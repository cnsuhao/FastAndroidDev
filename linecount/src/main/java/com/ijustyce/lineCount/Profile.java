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
		
		Properties pro;// 属性集
		FileInputStream is = null;
		boolean b = true;
		if (!file.exists()) {// 配置文件不存在时
			b = create();// 创建一个
			if (b)// 创建成功后
				b = write(latestPath);// 初始化
			else
				// 创建失败即不存在配置文件时弹出对话框提示错误
				JOptionPane.showConfirmDialog(null, "对不起，不存在配置文件！", "错误",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				is = new FileInputStream(file);
				pro = new Properties();
				pro.load(is);// 读取属性
				latestPath = pro.getProperty("latestPath");// 读取配置参数latestPath的值
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

			pro.store(os, null); // 将属性写入
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
