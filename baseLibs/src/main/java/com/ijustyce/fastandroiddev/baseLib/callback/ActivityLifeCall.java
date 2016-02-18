package com.ijustyce.fastandroiddev.baseLib.callback;

/**
 * Created by yc on 16-2-7.
 */
public interface ActivityLifeCall {

    void onResume();
    void onPause();
    void onCreate();
    void onStop();
    void onDestroy();

    class DefaultActivityLifeCall implements ActivityLifeCall{

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
