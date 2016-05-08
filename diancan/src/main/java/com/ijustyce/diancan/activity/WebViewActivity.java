package com.ijustyce.diancan.activity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ijustyce.fastandroiddev.base.BaseActivity;
import com.ijustyce.fastandroiddev.ui.ProgressWebView;
import com.ijustyce.diancan.R;

import butterknife.Bind;

/**
 * Created by yc on 2016/5/1 0001.  webview
 */
public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webView)
    ProgressWebView webView;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void afterCreate() {
        super.afterCreate();

        webView.addJavascriptInterface(new ShowContent(), "handler");
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //      view.loadUrl("javascript:window.handler.showSource(document.body.innerHTML);");
                String main = "javascript:function getClass(parent, sClass) { var " +
                        "aEle=parent.getElementsByTagName('div'); var aResult=[]; " +
                        "var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) " +
                        "{ aResult.push(aEle[i]); } }; return aResult; }";
                view.loadUrl(main);
                view.loadUrl("javascript:window.handler.showSource('<head>'+"
                        + "getClass(document,'main')[0].innerHTML+'</head>');");
            }
        });

        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
        webView.getSettings().setDisplayZoomControls(true);
        webView.loadUrl(url);
    }

    final class ShowContent {
        @JavascriptInterface
        public void showSource(final String html) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                }
            });
            System.out.println("====>html=" + html);
        }
    }

    @Override
    public boolean beforeCreate() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey("url")) return false;
        url = bundle.getString("url");
        if (url == null) return false;
        url = url.replace("/sinanews.shtml", "/sharenews.shtml");
        return super.beforeCreate();
    }
}
