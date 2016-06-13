/**
 * date:2014-04-21
 * rewrite ToastUtil
 */
package com.ijustyce.fastandroiddev.baseLib.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.R;

import java.util.ArrayList;
import java.util.List;

public class ToastUtil {

	private static List<String> notShowList;
	private static View toastView, toastTopView;
	private static TextView toastText, toastTopText;
	static {

		LayoutInflater mInflater = LayoutInflater.from(IApplication.getInstance());
		toastView = mInflater.inflate(R.layout.fastandroiddev_toast, null);
		toastTopView = mInflater.inflate(R.layout.fastandroiddev_toast_top, null);
		toastText = (TextView) toastView.findViewById(R.id.message);
		toastTopText = (TextView) toastTopView.findViewById(R.id.message);
		notShowList = new ArrayList<>();
	}

	public static void addNotShowWord(String word){

		if (!notShowList.contains(word)){
			notShowList.add(word);
		}
	}

	private static boolean shouldShow(String text){

		if (StringUtils.isEmpty(text) || notShowList.contains(text)){
			return false;
		}

		return true;
	}

	public static void show(int id) {

		String text = IApplication.getInstance().getResources().getString(id);
		if (!shouldShow(text)){
			return;
		}

		toastText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastView);
		toastStart.show();
	}

	public static void show(String text) {

		if (!shouldShow(text)){
			return;
		}

		toastText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastView);
		toastStart.show();
	}

	/**
	 *
	 * @param id
	 */
	public static void showTop(int id) {

		String text = IApplication.getInstance().getResources().getString(id);
		if (!shouldShow(text)){
			return;
		}

		toastTopText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastTopView);
		toastStart.show();
	}

	public static void showTop(Context context, String text) {

		if (!shouldShow(text)){
			return;
		}

		toastTopText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastTopView);
		toastStart.show();
	}

	/**
	 *
	 * @param id
	 * @param yOffset dp , height of ToastUnit
	 */
	public static void showTop(int id, int yOffset) {

		String text = IApplication.getInstance().getResources().getString(id);
		if (!shouldShow(text)){
			return;
		}

		toastTopText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.TOP , 0, yOffset);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastTopView);
		toastStart.show();
	}

	public static void showTop(String text , int yOffset) {


		if (!shouldShow(text)){
			return;
		}

		toastTopText.setText(text);
		Toast toastStart = new Toast(IApplication.getInstance());
		toastStart.setGravity(Gravity.TOP , 0, yOffset);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastTopView);
		toastStart.show();
	}
}
