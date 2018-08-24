package yanzs.hfutlibrary.callBack;

import android.content.Context;
import android.widget.Toast;

import okhttp3.Request;
import okhttp3.Response;
import yanzs.hfutlibrary.base.BaseCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.util.ShareUtil;

public class RequestCallBack extends BaseCallBack {
    private String key;
    private Context context;
    private OnFinishRequestListener onFinishRequestListener;

    public void setOnFinishRequestListener(OnFinishRequestListener onFinishRequestListener) {
        this.onFinishRequestListener = onFinishRequestListener;
    }

    public RequestCallBack(String key, Context context) {
        this.key = key;
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
        if (key.equals(ShareKey.KEY_GSON_CLASS_SORT)){
            string=Values.GSON_HEAD_CLASS_SORT+string+"}";
        }else if (key.equals(ShareKey.KEY_GSON_YEAR_SORT)){
            string=Values.GSON_HEAD_YEAR_SORT+string+"}";
        }else if (key.equals(ShareKey.KEY_GSON_MONTH_SORT)){
            string=Values.GSON_HEAD_MONTH_SORT+string+"}";
        }
        ShareUtil.storeLocalData(context, ShareKey.SHARED_KEY, key, string);
        if (onFinishRequestListener != null) {
            onFinishRequestListener.afterRequest(key);
        }

    }
}
