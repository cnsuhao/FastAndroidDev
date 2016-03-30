package com.ijustzxh.ui;

import com.ijustzxh.androidlib.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogUi extends Dialog {

	private Context context;
	private Dialog dialog;

	public DialogUi(Context context) {
		super(context);
		this.context = context;
		dialog = new Dialog(context, R.style.dialog_exit);
	}

	public DialogUi(Context context, int theme) {
		super(context);
		this.context = context;
		dialog = new Dialog(context, theme);
	}

	public Dialog createDialog(String msg, int layoutId) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(layoutId, null);
		RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_exit);

		TextView tipTextView = (TextView) v.findViewById(R.id.dialog_msg);
		tipTextView.setText(msg);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return dialog;
	}

	public Dialog createDialog(int layoutId) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(layoutId, null);
		RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_exit);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return dialog;
	}

	public Dialog editDialog(String title, String hint) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.share_part_edit, null);
		RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_exit);

		TextView tipTextView = (TextView) v.findViewById(R.id.edit_title);
		tipTextView.setText(title);

		EditText edit = (EditText) v.findViewById(R.id.dialog_msg);
		edit.setHint(hint);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return dialog;
	}

	public Dialog createDialog(String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.share_part_dialog, null);
		RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_exit);

		TextView tipTextView = (TextView) v.findViewById(R.id.dialog_msg);
		tipTextView.setText(msg);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return dialog;
	}
}
