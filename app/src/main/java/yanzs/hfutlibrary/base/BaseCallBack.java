package yanzs.hfutlibrary.base;

import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallBack{
    public abstract void onError(Request request, Exception e);
    public abstract void onResponse(Response response);
    public abstract void onGetString(String string);
}
