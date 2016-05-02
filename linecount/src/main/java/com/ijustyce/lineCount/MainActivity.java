/**
 * 
 */
package com.ijustyce.lineCount;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author yc
 * 
 */
public class MainActivity extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainActivity main = new MainActivity();
	private Font font = new IFont().getDefaultFont();
	private int lineCount = 0;

	private static String parentPath = "docs/";
	private String result = "";
	private static StringBuilder stringBuilder;

	private static List<String> file;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		main.setTitle("Lines of code");
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setSize(320, 320);
		main.setVisible(true);

		file = new ArrayList<>();
		stringBuilder = new StringBuilder();
		File file = new File(parentPath);
		if (!file.exists()) file.mkdirs();

	}

	private static boolean isExists(String path){

		if (file.contains(path)){
			return true;
		}
		file.add(path);
		return false;
	}

	public MainActivity() {

		setLayout(new GridLayout(4, 1));

		JButton addButton = new JButton("Browser directory");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);

		addButton = new JButton("Browser file");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);
		
		addButton = new JButton("clear count");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);

		addButton = new JButton("clear text");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);

		addButton = new JButton("show lineCount");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);

		addButton = new JButton("write to docs");
		addButton.addActionListener(this);
		addButton.setFont(font);
		add(addButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String s = ((JButton) e.getSource()).getText();

		if (s.equals("Browser directory")) {

			fromDir();
		}

		else if (s.equals("Browser file")) {

			fromFile();
		}

		else if (s.equals("clear text")){

			file.clear();
			stringBuilder = new StringBuilder();
		}

		else if (s.equals("write to docs")){

			file.clear();
			result = stringBuilder.toString();
			boolean success = DocWriter.write(new File(parentPath + "code.docs"), result);
			System.out.println("success " + success);
		}

		else if (s.equals("show lineCount")) {

			JOptionPane.showMessageDialog(null,"total ： " + lineCount ,"Lines of code",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(s.equals("clear count")){
			
			lineCount = 0;
		}

	}

	private void fromDir() {

		Profile profile = new Profile();
		String latestPath = (profile.read()?profile.latestPath:"C:");
		JFileChooser chooser = new JFileChooser(latestPath);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			String path = chooser.getSelectedFile().toString();
			profile.write(path);
			lineCount(path);
		}

	}

	private void fromFile() {

		Profile profile = new Profile();
		String latestPath = (profile.read()?profile.latestPath:"C:/");
		JFileChooser chooser = new JFileChooser(latestPath);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			String path = chooser.getSelectedFile().toString();
			profile.write(chooser.getSelectedFile().getParent());
			lineCount(path);
		}
	}

	private void lineCount(String path) {

		File f = new File(path);
		if(f.isDirectory()){
			System.out.println("===dic===" + path);
			File list[] = f.listFiles();
			if (list != null) {
				for (File tmp : list) {

					lineCount(tmp.getAbsolutePath());
				}
			}
		}
		
		else{
			
			Count(f);
		}
	}

	private boolean isEmpty(String value){

		return value == null || value.replaceAll(" ", "").length() < 1;
	}

	private void Count(File f) {

		if (f == null || !f.exists()) return;
		String name = f.getName();
		//if (!name.endsWith(".h") && !name.endsWith(".m") && !name.endsWith(".java") && !name.endsWith(".xml")) return;
		if (isExists(f.getAbsolutePath())){
			System.out.println("file exists, return ...");
			return;
		}
		if (name.equals(".DS_Store")){
			System.out.println(".DS_Store file, return ...");
			return;
		}
		try {
			InputStream input = new FileInputStream(f);
			BufferedReader b = new BufferedReader(new InputStreamReader(input));
			String value = b.readLine();

			while (value != null) {
				if (!isEmpty(value)) lineCount++;
				stringBuilder.append(value).append("\n");
				value = b.readLine();
			}

			b.close();
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("count " + lineCount);
	}

}
