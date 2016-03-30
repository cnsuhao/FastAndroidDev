
package com.ijustzxh.ui;

import com.ijustzxh.androidlib.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProcessDialog {

	private TextView tipTextView;
	private Dialog loadingDialog;
	private Context context;

	public ProcessDialog(Context context) {

		this.context = context;
		loadingDialog = new Dialog(context, R.style.dialog);
	}

	public Dialog createDialog(String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.share_part_loading_dialog, null);
		RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_view);

		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		tipTextView = (TextView) v.findViewById(R.id.tipTextView);

		Animation jump = AnimationUtils.loadAnimation(
				context, R.anim.share_part_loading);

		spaceshipImage.startAnimation(jump);
		tipTextView.setText(msg);

		loadingDialog.setCancelable(true);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return loadingDialog;
	}
}