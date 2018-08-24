package yanzs.hfutlibrary.util;

import android.content.Context;

import com.google.gson.Gson;

import yanzs.hfutlibrary.bean.post.RootPost;
import yanzs.hfutlibrary.bean.responsedou.RootResponseDou;
import yanzs.hfutlibrary.bean.responselib.RootResponseLib;
import yanzs.hfutlibrary.bean.responsemine.Rootclass;
import yanzs.hfutlibrary.bean.responsemine.Rootmonth;
import yanzs.hfutlibrary.bean.responsemine.Rootyear;
import yanzs.hfutlibrary.constant.ShareKey;

public class GsonUtil {
    public static String getPostJson(RootPost root){
        Gson gson=new Gson();
        return gson.toJson(root);
    }

    public static RootResponseLib getResponseRoot(String response){
        Gson gson=new Gson();
        if (response.length()==0){
            return null;
        }
        return gson.fromJson(response,RootResponseLib.class);
    }


    public static Rootclass getResponseRootclass(String response){
        Gson gson=new Gson();
        if (response.length()==0){
            return null;
        }
        return gson.fromJson(response,Rootclass.class);
    }
    public static Rootyear getResponseRootyear(String response){
        Gson gson=new Gson();
        if (response.length()==0){
            return null;
        }
        return gson.fromJson(response,Rootyear.class);
    }
    public static Rootmonth getResponseRootmonth(String response){
        Gson gson=new Gson();
        if (response.length()==0){
            return null;
        }
        return gson.fromJson(response,Rootmonth.class);
    }



    public static RootResponseDou getResponseRootDou(Context context){
        String response=ShareUtil.loadStringData(context, ShareKey.SHARED_KEY,ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL_IMG);
        Gson gson=new Gson();
        if (response.length()==0){
            return null;
        }
        return gson.fromJson(response,RootResponseDou.class);
    }
}
