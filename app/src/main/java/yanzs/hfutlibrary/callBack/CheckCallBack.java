package yanzs.hfutlibrary.callBack;

import android.content.Context;
import android.widget.Toast;

import okhttp3.Request;
import okhttp3.Response;
import yanzs.hfutlibrary.base.BaseCallBack;
import yanzs.hfutlibrary.constant.Values;

public class CheckCallBack extends BaseCallBack {
    private Context context;
    public CheckCallBack(Context context){
        this.context=context;
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

    }
}
