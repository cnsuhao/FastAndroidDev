/**
 * date:2014-04-21
 * rewrite toast
 */
package com.ijustzxh.unit;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustzxh.androidlib.R;

public class toast {

	public static void show(int id, Context context) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(id);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}

	public static void show(Context context, String text) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(text);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.BOTTOM , 0, 90);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
	
	/**
	 * 
	 * @param id
	 * @param height dp , height of toast
	 * @param context
	 */
	public static void showTop(int id, Context context) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast_top, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(id);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.TOP , 0, 0);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}

	public static void showTop(Context context, String text) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast_top, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(text);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.TOP , 0, 0);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
	
	/**
	 * 
	 * @param id
	 * @param yOffset dp , height of toast
	 * @param context
	 */
	public static void showTop(int id, Context context , int yOffset) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast_top, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(id);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.TOP , 0, yOffset);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}

	public static void showTop(Context context, String text , int yOffset) {

		LayoutInflater mInflater = LayoutInflater.from(context);
		View toastRoot = mInflater.inflate(R.layout.toast_top, null);
		TextView message = (TextView) toastRoot.findViewById(R.id.message);
		message.setText(text);

		Toast toastStart = new Toast(context);
		toastStart.setGravity(Gravity.TOP , 0, yOffset);
		toastStart.setDuration(Toast.LENGTH_LONG);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
}
