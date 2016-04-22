package com.ijustyce.lineCount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Profile {

	String latestPath = "C:/";
	File file = new File("set.ini");

	boolean create() {
		
		boolean b = true;
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	boolean read() {
		
		Properties pro;// ���Լ�
		FileInputStream is = null;
		boolean b = true;
		if (!file.exists()) {// �����ļ�������ʱ
			b = create();// ����һ��
			if (b)// �����ɹ���
				b = write(latestPath);// ��ʼ��
			else
				// ����ʧ�ܼ������������ļ�ʱ�����Ի�����ʾ����
				JOptionPane.showConfirmDialog(null, "�Բ��𣬲����������ļ���", "����",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				is = new FileInputStream(file);
				pro = new Properties();
				pro.load(is);// ��ȡ����
				latestPath = pro.getProperty("latestPath");// ��ȡ���ò���latestPath��ֵ
				is.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				b = false;
			}
		}
		return b;
	}

	boolean write(String latestPath) {
		this.latestPath = latestPath;
		Properties pro = null;
		FileOutputStream os = null;
		boolean b = true;
		try {
			os = new FileOutputStream(file);
			pro = new Properties();

			pro.setProperty("latestPath", latestPath);

			pro.store(os, null); // ������д��
			os.flush();
			os.close();

			System.out.println("latestPath=" + latestPath);

		} catch (IOException ioe) {
			b = false;
			ioe.printStackTrace();
		}
		return b;
	}

}
