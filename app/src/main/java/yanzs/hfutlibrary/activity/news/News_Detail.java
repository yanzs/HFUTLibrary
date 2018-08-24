package yanzs.hfutlibrary.activity.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.base.BaseCallBack;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;

public class News_Detail extends BaseActivity{
    @BindView(R.id.news_detail_webview)
    WebView news_detail_webview;
    @BindView(R.id.news_detail_img_back)
    ImageView news_detail_img_back;
    @BindView(R.id.news_detail_pro)
    ProgressBar news_detail_pro;

    @Override
    protected void initActivity() {
        String url=getIntent().getStringExtra(Values.NEWS_INTENT_PAGER_SIGN);
        initHtml(url);
    }

    private void initHtml(final String url) {
        try {
            OkHttpUtil.getResponseFromGET(url, new BaseCallBack() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(News_Detail.this, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onResponse(Response response) {

                }

                @Override
                public void onGetString(String string) {
                    String content=JsoupUtil.getNewsHtml(string);
                    initWebView(content,url);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView(String content, String url){
        if (!TextUtils.isEmpty(content)){
            news_detail_webview.loadDataWithBaseURL(url,content,"text/html","utf-8",null);
        }else {
            news_detail_webview.loadUrl(url);
        }
        news_detail_webview.setWebViewClient(new WebClient());
        news_detail_webview.setWebChromeClient(new WebChrome());
        news_detail_webview.setDownloadListener(new WebViewDownLoadListener());
        news_detail_webview.addJavascriptInterface(new JavascriptInterface(),"imgActivity");
        WebSettings settings=news_detail_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
    }

    @OnClick(R.id.news_detail_img_back)
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.news_detail_img_back:
                finish();
                break;
        }
    }

    private class WebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private String readJS() {
        try {
            InputStream inputStream=getAssets().open("img.js");
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            byte[] bytes=new byte[1024];
            int len;
            while ((len=inputStream.read(bytes))>0){
                outputStream.write(bytes,0,len);
            }
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void startImgActivity(String imageUrl) {
            if (!imageUrl.contains("icon")) {//图片url包含"icon"表明是小图标，不需要放大查看
                Intent intent = new Intent(News_Detail.this, News_Img.class);
                intent.putExtra(Values.NEWS_INTENT_IMG_SIGN, imageUrl);
                startActivity(intent);
            }
        }
    }

    private class WebClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            news_detail_webview.loadUrl("javascript:("+readJS()+")()");
        }
    }

    private class WebChrome extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (news_detail_pro==null){
                news_detail_pro=new ProgressBar(News_Detail.this);
            }else {
                news_detail_pro.setProgress(newProgress);
            }
            if (newProgress==100){
                news_detail_pro.setVisibility(View.GONE);
            }else {
                news_detail_pro.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_news_detial;
    }
}
