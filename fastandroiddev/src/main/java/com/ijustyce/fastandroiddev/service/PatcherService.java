package com.ijustyce.fastandroiddev.service;

import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.base.BaseService;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.RegularUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.manager.PreferenceManager;
import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.fastandroiddev.net.ITransferListener;

import org.json.JSONObject;

/**
 * Created by yangchun on 16/5/30.  热修复
 */
public class PatcherService extends BaseService {

    public PatcherService() {
        super("name");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        HttpParams params = HttpParams.create(null, "http://ijustyce.com/app/patcher.php");
        params.add("pkgName", getPackageName())
                .add("versionCode", CommonTool.getVersionCode(mContext))
                .add("lastId", PreferenceManager.getInt("app_patcher_last_id"));
        INetWork.sendGet(params, new HttpListener() {
            @Override
            public void success(String object, String taskId) {

                if (!StringUtils.isEmpty(object) && !object.equals("null")){
                    try {
                        JSONObject jsonObject = new JSONObject(object);
                        int code = jsonObject.getInt("code");
                        if (code == 100){
                            PreferenceManager.putInt("app_patcher_last_id",
                                    StringUtils.getInt(jsonObject.getString("id")));
                            applyPatcher(jsonObject.getString("url"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void fail(int code, String msg, String taskId) {

            }
        });
    }

    private void applyPatcher(String url){

        if (!RegularUtils.isUrl(url)) return;

        HttpParams params = HttpParams.create(null, url);
        String path = FileUtils.getAvailablePath(mContext, "patcher");
        path = path + "/" + System.currentTimeMillis() + ".apatch";

        //  /storage/emulated/0/Android/data/com.lzhplus.lzh/files/1464601625713.apatch
        INetWork.downloadFile(params, path, new ITransferListener() {
            @Override
            public void onDownload(String filePath, String fileUri) {
                IApplication.addPatcher(filePath);
                FileUtils.deleteFile(filePath);
            }
        });
    }
}