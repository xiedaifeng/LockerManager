package com.yidao.module_lib.base;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;

public class CustomWebview extends WebView {


    public CustomWebview(Context context) {
        super(context);
        init();
    }

    public CustomWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setDefaultFontSize(40);
        setDrawingCacheEnabled(true);


        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        addJavascriptInterface(new JavascriptInterface(), "android");
    }

    public static class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void nativeQuestionSubmit(String data) {
            LogUtils.e("nativeQuestionSubmit:" + data);
            ViewManager.getInstance().finishView();
        }
    }

    public void evaluateCustomJavascript(final String params, final IJavascriptCallBack callBack){
        evaluateJavascript("javascript:virtualConfigFromApp('" + params + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                LogUtils.e("onReceiveValue:" + value);
                if(callBack!=null){
                    callBack.onReceiveValue(value);
                }
            }
        });
    }

    public interface IJavascriptCallBack{
        void onReceiveValue(String value);
    }
}
