package com.ijustyce.ichat;

import android.util.Log;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.search.UserSearchManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JilvChat {

	private static String host;
	private static int port = 5222;
	public static XMPPConnection connection;
	private static ConnectionConfiguration connectionConfig;
	private static Roster roster;
	private static Map<String, Chat> chatManage = new HashMap<>();
	private static JilvChat chat;

	private JilvChat(String host, int port){
		JilvChat.host = host;
		JilvChat.port = port;
	}

	/**
	 * @param host	ip
	 * @param port	5222
     */
	public synchronized static JilvChat getInstance(final String host, int port){

		if(chat == null){
			chat = new JilvChat(host, port);
			new Thread(){
				public void run(){
					initConnection();
				}
			}.start();
		}
		return chat;
	}

	public static void destroy(){
		if(connection!=null){
			connection = null;
		}
		if (connectionConfig!=null){
			connectionConfig = null;
		}if(roster!=null){
			roster = null;
		}
		if(chatManage!=null){
			chatManage.clear();
			chatManage = null;
		}
		if(chat!=null){
			chat = null;
		}
	}

	/**
	 * 初始化登陆选项
	 */
	public static synchronized void initConnection() {

		if(connection != null){
			return ;
		}
		chatManage = new HashMap<>();
		if(connectionConfig == null){
			connectionConfig = new ConnectionConfiguration(host, port, host);
		}

		connectionConfig.setReconnectionAllowed(false); // 是否允许自动登陆
		connectionConfig.setSendPresence(false); // 不告诉服务器自己的登陆状态，以便获取离线消息
		connectionConfig.setSASLAuthenticationEnabled(false);
//		connectionConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
		connectionConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		connectionConfig.setVerifyRootCAEnabled(false);
		connection = new XMPPConnection(connectionConfig);
	}

	/**
	 * 获取离线消息并登录。
	 *
	 * @return 返回离线消息map，key是发送离线消息的好友的信息，value是这个好友发送的消息数组。
	 */
	public Map<String, ArrayList<Message>> getOfflineMessage() {
		Map<String, ArrayList<Message>> offlineMsgs = null;
		if(connection == null){
			Log.e("getOfflineMessage", "connection is null");
			return null;
		}
		OfflineMessageManager offlineManager = new OfflineMessageManager(
				connection);
		Iterator<Message> it = null;
		try{
			it = offlineManager.getMessages();
		}catch (Exception e){
			e.printStackTrace();
		}
		if (it == null) return null;
		offlineMsgs = new HashMap<String, ArrayList<Message>>();
		while (it.hasNext()) {
			Message message = it.next();
			String fromUser = message.getFrom().split("/")[0];
			if (offlineMsgs.containsKey(fromUser)) {
				offlineMsgs.get(fromUser).add(message);
			} else {
				ArrayList<Message> temp = new ArrayList<Message>();
				temp.add(message);
				offlineMsgs.put(fromUser, temp);
			}
		}
		if (connection != null && connection.isConnected() && connection.isAuthenticated()) {
			try {
				offlineManager.deleteMessages();
			}catch (Exception e){
				e.printStackTrace();
			}
			setRoster(connection.getRoster());
		}
		return offlineMsgs;
	}

	public synchronized void login(final String username, final String password, final String resource){
		new Thread(){
			public void run(){
				if(connection == null){
					Log.e("login", "connection is null");
					return ;
				}
				try {
					if(!connection.isConnected()){
						connection.connect();
					}
					connection.login(username, password, resource);
				}catch (Exception e){
					e.printStackTrace();
				}
				setPresence(0);
			}
		}.start();
	}

	public synchronized void addConnectionListener(ConnectionListener connectionListener){

		if(connection == null){

			Log.e("addConnectionListener", "connection is null");
			return ;
		}if(!connection.isConnected()){

			Log.e("addConnectionListener", "not connected to server");
			return ;
		}
		connection.addConnectionListener(connectionListener);
	}

	public synchronized void removeConnectionListener(ConnectionListener connectionListener){

		if(connection == null){

			Log.e("addConnectionListener", "connection is null");
			return ;
		}if(!connection.isConnected()){

			Log.e("addConnectionListener", "not connected to server");
			return ;
		}
		connection.removeConnectionListener(connectionListener);
	}

	private static void setRoster(Roster roster){

		JilvChat.roster = roster;
	}

	public synchronized void createAccount(final String username, final String password, final String email){

		Log.d("===createAccount===", "username: " + username + " password: " + password + " email: " + email);
		new Thread(){
			public void run(){
				if(connection == null){
					Log.e("createAccount", "connection is null");
					return ;
				}
				try {
					connection.getAccountManager().createAccount(username, password, email);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 设置在线状态
	 *
	 * @param code
	 *            0->在线 1->Q我吧 2->忙碌 3->离开 4->离线
	 */
	public void setPresence(int code) {
		if (connection == null)
			return;
		Presence presence;
		switch (code) {
			case 0:
				presence = new Presence(Presence.Type.available);
				presence.setPriority(1);  //  设置优先值
				connection.sendPacket(presence);
				break;
			case 1:
				presence = new Presence(Presence.Type.available);
				presence.setMode(Presence.Mode.chat);
				connection.sendPacket(presence);
				break;
			case 2:
				presence = new Presence(Presence.Type.available);
				presence.setMode(Presence.Mode.dnd);
				connection.sendPacket(presence);
				break;
			case 3:
				presence = new Presence(Presence.Type.available);
				presence.setMode(Presence.Mode.away);
				connection.sendPacket(presence);
				break;
			case 4:
				presence = new Presence(Presence.Type.unavailable);
				connection.sendPacket(presence);
				break;
			default:
				break;
		}
	}

	/**
	 * 注销登陆
	 */
	public void logout() {
		new Thread(){
			public void run(){
				if(connection == null){
					Log.e("logout", "connection is null");
					return ;
				}try {
					connection.getAccountManager().deleteAccount();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		destroy();
	}

	/**
	 * 注销登陆
	 * @param connection XMPPConnection
	 * @return 注销成功返回true，否则返回false
	 */
	public boolean deleteAccount(XMPPConnection connection) {
		try {
			connection.getAccountManager().deleteAccount();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 查询所有分组
	 *
	 * @return list数组，包含分组信息
	 */
	public static List<RosterGroup> getGroups() {
		List<RosterGroup> groupList = new ArrayList<>();
		Collection<RosterGroup> rosterGroup = roster.getGroups();
		for (RosterGroup tmp : rosterGroup){
			groupList.add(tmp);
		}
		return groupList;
	}

	/**
	 * 添加分组
	 *
	 * @param groupName
	 *            组名
	 * @return 如果添加成功则返回true，否则返回false
	 */
	public static boolean addGroup(String groupName) {
		try {
			roster.createGroup(groupName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询某个分组的好友信息
	 * @param groupName groupName
	 * @return list数组，包含好友信息
	 */
	public static List<RosterEntry> getEntriesByGroup(String groupName) {
		List<RosterEntry> EntriesList = new ArrayList<>();
		RosterGroup rosterGroup = roster.getGroup(groupName);
		Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
		for (RosterEntry tmp : rosterEntry){
			EntriesList.add(tmp);
		}
		return EntriesList;
	}

	/**
	 * 列出所有好友
	 *
	 * @return list数组，包含好友信息
	 */
	public static List<RosterEntry> getAllEntries() {
		List<RosterEntry> EntriesList = new ArrayList<>();
		Collection<RosterEntry> rosterEntry = roster.getEntries();
		for (RosterEntry tmp : rosterEntry){
			EntriesList.add(tmp);
		}
		return EntriesList;
	}

	/**
	 * 添加好友，不指定具体分组
	 *
	 * @param userName
	 *            用户名
	 * @param name
	 *            备注
	 * @return 如果成功返回true，否则返回false
	 */
	public static boolean addUser(String userName, String name) {
		try {
			roster.createEntry(userName, name, null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加好友到指定分组
	 *
	 * @param userName
	 *            用户名
	 * @param name
	 *            备注名
	 * @param groupName
	 *            分组名
	 * @return true if success or return false
	 */
	public static boolean addUser(String userName, String name, String groupName) {
		try {
			roster.createEntry(userName, name, new String[]{groupName});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除好友
	 *
	 * @param userName
	 *            用户名
	 * @return true if success or return false
	 */
	public static boolean removeUser(String userName) {
		try {
			if (userName.contains("@")) {
				userName = userName.split("@")[0];
			}
			RosterEntry entry = roster.getEntry(userName);
			System.out.println("删除好友：" + userName);
			roster.removeEntry(entry);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 搜索好友
	 *
	 * @param userName userName
	 * @return list数组，包含Row，通过row.getValues("userAccount");读取用户账号
	 *         row.getValues("userName")读取用户名
	 * @throws XMPPException
	 */
	public static List<Row> searchUsers(String userName) throws XMPPException {
		List<Row> results = new ArrayList<>();
		UserSearchManager usm = new UserSearchManager(connection);
		Form searchForm = usm.getSearchForm(host);
		Form answerForm = searchForm.createAnswerForm();
		answerForm.setAnswer("userAccount", true);
		answerForm.setAnswer("userName", userName);
		ReportedData data = usm.getSearchResults(answerForm, host);
		Iterator<Row> it = data.getRows();
		Row row;
		while (it.hasNext()) {
			row = it.next();
			results.add(row);
		}
		return results;
	}

	private Chat getFriendChat(String friend, MessageListener listenter) {
		if (!connection.isConnected()) {
			Log.e("===error===", "error while create chat ");
			return null;
		}

		Chat chat = chatManage.get(friend);
		if(chat == null){
			chat = connection.getChatManager().createChat(friend + "@" + host,
					listenter);
			chatManage.put(friend, chat);
		}
		return chat;
	}

	/**
	 * 设置消息监听，如果有消息到达会激发这个事件
	 *
	 * @param listener
	 *            ChatManagerListener
	 * @return true if success or return false
	 */
	public boolean addChatListener(ChatManagerListener listener) {
		if (connection != null && connection.isConnected()) {
			connection.getChatManager().addChatListener(listener);
			return true;
		}
		return false;
	}

	public boolean removeChatListener(ChatManagerListener listener) {
		if (connection != null && connection.isConnected()) {
			connection.getChatManager().removeChatListener(listener);
			return true;
		}
		return false;
	}

	/**
	 * 创建群聊
	 *
	 * @param user user
	 * @param roomsName roomsName
	 * @param password password
	 * @return MultiUserChat
	 */
	public MultiUserChat multiUserChat(String user, String roomsName, String password) {
		if (connection == null){
			return null;
		}
		try {
			MultiUserChat muc = new MultiUserChat(connection,
					roomsName + "@conference." + connection.getServiceName());
			DiscussionHistory history = new DiscussionHistory();
			history.setMaxChars(0);
			muc.join(user, password, history,
					SmackConfiguration.getPacketReplyTimeout());
			return muc;
		} catch (XMPPException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean sendFile(String user, String device, File file){

		if(connection == null){
			return false;
		}
		FileTransferManager transfer = new FileTransferManager(connection);
		OutgoingFileTransfer out = transfer.createOutgoingFileTransfer(user + "@" + host + "/" + device);
		OutgoingFileTransfer.setResponseTimeout(5000);
		try {
			String name = file.getName();
			Log.i("===sendFile===", "name: " + name);
			out.sendFile(file, name);
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 发送消息给好友
	 *
	 * @param user
	 *            用户名，比如test2
	 * @param msg
	 *            消息内容
	 * @param listener
	 *            消息监听器
	 * @return true if success or return false
	 */
	public boolean sendMsg(String user, String msg, MessageListener listener) {
		Chat send = getFriendChat(user, listener);
		if (send == null) {
			return false;
		}
		try {
			send.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}