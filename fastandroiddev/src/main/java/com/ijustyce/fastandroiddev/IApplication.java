package com.ijustyce.fastandroiddev;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;

/**
 * Created by yangchun on 16/4/28.  全局的Application
 */
public class IApplication extends Application {

    private static Application app;
    private static PatchManager patchManager;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static void enAbleAndFix(){

        if (patchManager != null) return;
        patchManager = new PatchManager(app);
        patchManager.init(CommonTool.getVersion(app));//current version
        patchManager.loadPatch();
    }

    public static void addPatcher(String path){
        try {
            patchManager.addPatch(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void init(Application application){

        app = application;
    }

    public static Application getInstance(){

        return app;
    }
}