package com.ijustyce.ichat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.io.File;

public class MainActivity extends Activity {

	private JilvChat chat;
	private MultiUserChat  mulChat;
	private String username, password, resource, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}
	
	private void init(){
		
		Log.i("===ichat===", "init ...");
		username = "test" + System.currentTimeMillis();
		password = "123456";
		email = username + "@qq.com";
		resource = "Android";
		chat = JilvChat.getInstance("112.124.61.5", 5222);
	}

	public void viewClick(View view) {

		int id = view.getId();

		if (id == R.id.create) {
			create();
		}else if(id == R.id.chat){
			chat();
		}else if(id == R.id.logout){
			logout();
		}else if(id == R.id.login){
			login();
		}else if(id == R.id.mul){
			mulChat();
		}else if(id == R.id.file){
			sendFile();
		}
	}
	
	private void sendFile(){
		
		if(chat!=null){
			File f = new File(Environment.getExternalStorageDirectory().getPath() + "/123.amr");
			Log.i("===ichat===", f.getAbsolutePath());
			if(f.exists()){
				chat.sendFile("10051328", "yun-android", f);
			}
		}
	}
	
	private void mulChat(){
		
		if(chat != null){
			mulChat.sendMessage("hahaha");
		}
	}

	private void create() {
		
		if(chat !=null){
			chat.createAccount(username, password, email);		
		}
	}

	private void chat() {

		if (chat != null) {
			chat.sendMsg("10051328", username, null);
		}
	}

	private void logout() {

		if (chat != null) {
			mulChat.removeMessageListener(mulListener);  //  移除消息监听
			chat.removeConnectionListener(connectionListener);  //  移除连接状态监听
			chat.removeChatListener(chatListener);  //  移除私聊消息监听
			chat.logout();
		}
	}

	private void login() {

		if (chat != null) {
			chat.login(username, password, resource);
			chat.addConnectionListener(connectionListener);  //  连接监听
			mulChat = chat.multiUserChat(username, "test01", password);  //  创建群：test01，其他用户加入本群需要的密码就是password
			mulChat.addMessageListener(mulListener);  //  群消息监听
			chat.addChatListener(chatListener);  //  私聊消息监听
		}
	}
	
	private ChatManagerListener chatListener = new ChatManagerListener(){

		@Override
		public void chatCreated(Chat chat, boolean createdLocally) {
			
			chat.addMessageListener(listener);
		}
	};
	
	private MessageListener listener = new MessageListener(){

		@Override
		public void processMessage(Chat chat, Message message) {
			
			Log.i("===from===", message.getFrom());
			Log.i("===body===", message.getBody());
		}		
	};
	
	private PacketListener mulListener = new PacketListener(){

		@Override
		public void processPacket(Packet packet) {
			
			Message message = (Message)packet;
			Log.i("===from===", message.getFrom());
			Log.i("===body===", message.getBody());
		}	
		
	};
	
	private ConnectionListener connectionListener = new ConnectionListener() {	
		@Override
		public void reconnectionSuccessful() {
			
		}
		
		@Override
		public void reconnectionFailed(Exception e) {
			
		}
		
		@Override
		public void reconnectingIn(int seconds) {
			
		}
		
		@Override
		public void connectionClosedOnError(Exception e) {
			
			Log.e("MainActivity", "connectionClosedOnError");
		}
		
		@Override
		public void connectionClosed() {
			
			Log.e("MainActivity", "connectionClosed");
		//	login();
		}
	};
}
