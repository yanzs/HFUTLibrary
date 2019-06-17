package yanzs.hfutlibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class WebJsUtil {


    private WebView webView;
    private JsCallBack jsCallBack;

    public void setJsCallBack(JsCallBack jsCallBack) {
        this.jsCallBack = jsCallBack;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public WebJsUtil(Context context){
        webView=new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/douban.html");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void dealMusicJsMethod(String paras){
        webView.evaluateJavascript("javascript:decrypt('"+paras+"')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if (jsCallBack!=null){
                    jsCallBack.dealJsResult(s);
                }
            }
        });
    }

    public interface JsCallBack{
        void dealJsResult(String s);
    }

}
