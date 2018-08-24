package yanzs.hfutlibrary.callBack;

import android.content.Context;
import android.widget.Toast;

import okhttp3.Request;
import okhttp3.Response;
import yanzs.hfutlibrary.base.BaseCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnLoginCallBackListener;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class VerifyCallBack extends BaseCallBack{
    private Context context;
    private OnLoginCallBackListener onLoginCallBackListener;

    public void setOnLoginCallBackListener(OnLoginCallBackListener onLoginCallBackListener) {
        this.onLoginCallBackListener = onLoginCallBackListener;
    }

    public VerifyCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Request request, Exception e) {
        Toast.makeText(context, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onGetString(String string) {
        String s = JsoupUtil.getUserName(string);
        if (s.length()>0){
            ShareUtil.storeLocalData(context,ShareKey.SHARED_KEY, ShareKey.KEY_COOKIE,OkHttpUtil.getCookie(Urls.URL_ORIGIN));
            ShareUtil.storeLocalData(context,ShareKey.SHARED_KEY, ShareKey.KEY_PAGE_USER_INFO,string);
        }else {
            ShareUtil.storeLocalData(context,ShareKey.SHARED_KEY, ShareKey.KEY_COOKIE,"");
        }

        if (onLoginCallBackListener !=null){
            onLoginCallBackListener.loginState(s.length()>0);
        }

    }
}
