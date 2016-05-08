package com.ijustyce.diancan.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.base.BaseFragment;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.ImageUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;
import com.ijustyce.fastandroiddev.net.FormFile;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.fastandroiddev.net.ProcessListener;
import com.ijustyce.fastandroiddev.ui.CircleImageView;
import com.ijustyce.fastandroiddev.umenglib.UpdateTool;
import com.ijustyce.diancan.IApplication;
import com.ijustyce.diancan.R;
import com.ijustyce.diancan.activity.AboutActivity;
import com.ijustyce.diancan.activity.GuideActivity;
import com.ijustyce.diancan.activity.LoginActivity;
import com.ijustyce.diancan.constant.Constant;
import com.ijustyce.diancan.http.HttpConstant;
import com.ijustyce.diancan.http.HttpUser;
import com.ijustyce.diancan.model.UserInfo;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class MeFragment extends BaseFragment {

    @Bind(R.id.logout)
    Button logout;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.userMobile)
    TextView userMobile;
    @Bind(R.id.headimgIcon)
    CircleImageView userHeader;

    private final static int SELECT_PIC = 0;
    private final static int CROP_PIC = 1;
    private String tmpFile;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.aboutUs, R.id.recommendShare, R.id.versionUpdate, R.id.cache, R.id.logout, R.id.headimgIcon})
    public void onClick(View view){

        if (view == null){
            return;
        }
        switch (view.getId()){

            case R.id.aboutUs:
                newActivity(AboutActivity.class);
                break;

            case R.id.recommendShare:
                CommonTool.systemShare(mContext, getResString(R.string.share));
                break;

            case R.id.versionUpdate:
                UpdateTool.forceUpdate(mContext);
                break;

            case R.id.headimgIcon:
                if (!IApplication.isLogin()){
                    newActivity(LoginActivity.class);
                }else {
                    selectPic();
                }
                break;

            case R.id.cache:
                clearCache();
                break;

            case R.id.logout:
                IApplication.setUserInfo(null);
                SharedPreferences shared = mContext.
                        getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                shared.edit().clear().apply();
                newActivity(GuideActivity.class);
                getActivity().finish();
                break;

            default:
                break;
        }
    }

    private void cropPic(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 96);
        intent.putExtra("outputY", 96);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PIC);
    }

    private void selectPic() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            startActivityForResult(intent, SELECT_PIC);
        } else {
            ToastUtil.show(mContext, "系统没有图片选择软件！");
        }
    }

    ProcessListener processListener = new ProcessListener() {
        @Override
        public void onProcess(long total, int current) {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onSuccess(String response) {

            dismiss();
            ILog.i("===me===", "result is " + response);
            UserInfo userInfo = IJson.fromJson(response + "", UserInfo.class);
            if (userInfo == null || userInfo.getResult() == null) {
                return;
            }
            String head = userInfo.getResult().getMsg();
            HttpUser.upHead(getTAG(), head, mContext, null);
            IApplication.setHead(head);
            ((IApplication) mContext.getApplicationContext()).saveUserInfo(IApplication.getUserInfo());
            ImageUtils.load(mContext, userHeader,
                    HttpConstant.UPLOAD_DIR + IApplication.getHead());
        }

        @Override
        public void onDownload(String file, String fileUri) {

        }
    };

    private void editHead() {

        if (tmpFile == null) {
            return;
        }
        showProcess("正在上传头像...");
        FormFile[] files = new FormFile[1];
        File f = new File(tmpFile);
        files[0] = new FormFile(f.getName(), f, "uploadfile", null);
        INetWork.uploadFile(files, mContext, HttpParams.create(null, HttpConstant.UPLOAD), processListener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case CROP_PIC:

                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    if (data.hasExtra("data")) {

                        Bitmap bmap = data.getParcelableExtra("data");
                        if (bmap == null) {
                            ToastUtil.showTop(mContext, "文件打开失败！");
                            return;
                        }
                        tmpFile = FileUtils.getAvailablePath(mContext, Constant.FILE_PATH)
                                + "/.tmp/";
                        File tmp = new File(tmpFile);
                        if (!tmp.exists()) {
                            tmp.mkdirs();
                        }
                        tmpFile = tmpFile + System.currentTimeMillis() + ".png";
                        bmap = CommonTool.bitmapToRound(bmap);
                        FileUtils.savBitmapToPng(bmap, tmpFile);
                        bmap.recycle();
                        editHead();
                    }
                }
                break;

            case SELECT_PIC:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    cropPic(uri);
                }
                break;
            default:
                break;
        }
    }

    private synchronized void clearCache(){

        showProcess("正在为您清理缓存");

        String file = FileUtils.getAvailablePath(mContext, Constant.FILE_PATH);
        if (file != null) {
            FileUtils.deleteFile(file);
        }
        dismiss(2000);
    }

    @Override
    public void doResume() {
        super.doResume();

        boolean isLogin = IApplication.isLogin();

        logout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        userName.setText(isLogin ? IApplication.getUserInfo().getData().getName() : "");
        userMobile.setText(isLogin ? IApplication.getUserInfo().getData().getPhone() : "");

        String file = FileUtils.getAvailablePath(mContext, Constant.FILE_PATH);
        cacheSize.setText(file == null ? "0M" : "" + FileUtils.getDirSize(new File(file)));
    }
}
