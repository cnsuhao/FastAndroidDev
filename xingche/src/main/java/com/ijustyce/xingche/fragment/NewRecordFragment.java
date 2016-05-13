package com.ijustyce.xingche.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.ijustyce.fastandroiddev.base.BaseFragment;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;
import com.ijustyce.fastandroiddev.ui.CircleImageView;
import com.ijustyce.xingche.R;
import com.ijustyce.xingche.data.RecordDB;
import com.ijustyce.xingche.model.Record;

import butterknife.Bind;
import butterknife.OnClick;

public class NewRecordFragment extends BaseFragment {

    private static final int REQUEST_CODE_TAKE_VIDEO = 1;// 摄像的requestCode
    private String strVideoPath;
    private Animation animation;

    @Bind(R.id.desc)
    EditText desc;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_record;
    }

    @OnClick({R.id.submit, R.id.select})
    public void onClick(View view){

        if (view == null){
            return;
        }
        switch (view.getId()){

            case R.id.submit:
                submit();
                break;

            case R.id.select:
                videoMethod();
                break;

            default:
                break;
        }
    }

    private void submit(){

        if (StringUtils.isEmpty(strVideoPath)){
            ToastUtil.show(mContext, "请先拍摄视频!");
            return;
        }

        String descString = CommonTool.getText(desc);
        if (StringUtils.isEmpty(descString) || descString.length() < 5){
            ToastUtil.show(mContext, "描述内容不能少于5个字！");
            return;
        }
        Record record = new Record();
        record.setTitle(descString.substring(0, descString.length() > 15 ? 15 : descString.length()));
        record.setDesc(descString);
        record.setDate(DateUtil.getDateString("yyyy-MM-dd HH:mm:ss"));
        record.setUrl("file://" + strVideoPath);

        RecordDB.saveOrUpdate(record);

        ToastUtil.show(mContext, "保存成功!");

        desc.setText("");
        strVideoPath = null;
    }

    @Override
    public void afterCreate() {

        animation = AnimationUtils.loadAnimation(mContext, R.anim.new_record);
        animation.setRepeatCount(-1);
        final CircleImageView view = (CircleImageView) mView.findViewById(R.id.select);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * 拍摄视频
     */
    private void videoMethod() {

        if (animation != null) animation.cancel();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CODE_TAKE_VIDEO://拍摄视频
                if (resultCode == Activity.RESULT_OK) {
                    Uri uriVideo = data.getData();
                    Cursor cursor = getActivity().getContentResolver().query(uriVideo, null, null, null, null);
                    if (cursor == null) return;
                    if (cursor.moveToNext()) {
                        strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                        ILog.i("===path===", strVideoPath);
                        cursor.close();
                    }
                }
                break;
            default:
                break;
        }
    }
}
