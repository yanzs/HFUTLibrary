package yanzs.hfutlibrary.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import yanzs.hfutlibrary.base.BaseCallBack;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;

public class OkHttpUtil {
    private volatile static OkHttpUtil instance;
    private OkHttpClient httpClient;
    private Handler deliveryHandler;

    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    private OkHttpUtil() {
        httpClient = new OkHttpClient.Builder().connectTimeout(Values.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                        cookieStore.put(new HttpUrl.Builder().scheme("http").host(url.host()).build(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(new HttpUrl.Builder().scheme("http").host(url.host()).build());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();

        deliveryHandler = new Handler(Looper.getMainLooper());
    }

    public static void clearCookie(String s) {
        getInstance().httpClient.cookieJar().saveFromResponse(HttpUrl.parse(s), null);
    }

    public static void getResponseFromGET(String url, BaseCallBack callBack) throws IOException {
        getInstance()._getResponseFromGET(url, callBack);
    }

    public static void getResponseFromGET(String url,String host,String referer,BaseCallBack callBack) throws IOException {
        getInstance()._getResponseFromGET( url, host, referer, callBack);
    }

    public static void getResponseFromPOST(String number, String password, String captcha, String url,BaseCallBack callBack){
        getInstance()._getResponseFromPOST(callBack,number,password,captcha,url);
    }

    public static void getResponseFromPOST(BaseCallBack callBack,String json){
        getInstance()._getResponseFromPOST(callBack,json);
    }

    public static String getCookie(String url) {
        return getInstance().httpClient.cookieJar().loadForRequest(HttpUrl.parse(url)).get(0).toString();
    }
    public static void setCookies(HttpUrl url,String cookie) {
        List<Cookie> cookieList=new ArrayList<>();
        Cookie cookies=Cookie.parse(url,cookie);
        cookieList.add(cookies);
        getInstance().httpClient.cookieJar().saveFromResponse(url,cookieList);
    }

    public static Call getCallFromGET(String url){
        return getInstance()._getCallFromGET(url);
    }

    public static Call getCallFromGET(String url,String host,String referer){
        return getInstance()._getCallFromGET(url,host,referer);
    }

    private void _getResponseFromPOST(BaseCallBack callBack, String number, String password, String captcha, String url) {

        FormBody formBody = new FormBody.Builder().
                add(Values.LOGIN_NUMBER, number).
                add(Values.LOGIN_PASSWD, password).
                add(Values.LOGIN_CAPTCHA, captcha).
                add(Values.LOGIN_SELECT, Values.LOGIN_SELECT_VALUES).build();
        Request request = new Request.Builder().
                url(url).post(formBody).
                addHeader(Values.HTTP_HOST, Urls.URL_HOST).
                addHeader(Values.HTTP_ORIGIN, Urls.URL_ORIGIN).
                addHeader(Values.HTTP_REFERER, Urls.URL_LOGIN_REFERER).
                build();
        deliveryRequestDeal(request, callBack);

    }


    private void _getResponseFromPOST(BaseCallBack callBack, String json) {

        RequestBody body=FormBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        Request request=new Request.Builder().url(Urls.URL_SEARCH_LIB).post(body).build();
        deliveryRequestDeal(request, callBack);

    }

    /**
     * 异步Get请求
     *
     * @param url
     * @param callBack
     */
    private void _getResponseFromGET(String url, BaseCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        deliveryRequestDeal(request, callBack);
    }

    private void _getResponseFromGET(String url,String host,String referer,BaseCallBack callBack){
        Request request = new Request.Builder().addHeader(Values.HTTP_HOST,host).
                addHeader(Values.HTTP_REFERER, referer).url(url).build();
        deliveryRequestDeal(request, callBack);
    }

    private void deliveryRequestDeal(final Request request, final BaseCallBack callBack) {
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailedInform(request, e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    sendSuccessInform(response, callBack);
                    String s=response.body().string();
                    sendHtmlString(s,callBack);
                }
            });
    }

    private void sendSuccessInform(final Response body, final BaseCallBack callBack) {
        deliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(body);
                }
            }
        });
    }

    private void sendHtmlString(final String string, final BaseCallBack callBack) {
        deliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onGetString(string);
                }
            }
        });
    }

    private void sendFailedInform(final Request request, final IOException e, final BaseCallBack callBack) {
        deliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onError(request, e);
                }
            }
        });
    }

    private Call _getCallFromGET(String url){
        Request request = new Request.Builder().url(url).build();
        return httpClient.newCall(request);
    }

    private Call _getCallFromGET(String url,String host,String referer){
        Request request=new Request.Builder().addHeader(Values.HTTP_HOST,host).addHeader(Values.HTTP_REFERER,referer).url(url).build();
        return httpClient.newCall(request);
    }

}
