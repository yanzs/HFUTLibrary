package yanzs.hfutlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtil {
    private  static ShareUtil instance;
    private static SharedPreferences sharedPreferences;
    private ShareUtil(){

    }

    public static ShareUtil getInstance(){
        if(instance==null){
            synchronized (ShareUtil.class){
                if (instance==null){
                    instance=new ShareUtil();
                }
            }
        }

        return instance;
    }


    /**
     * 用于清除相应键值对应数据
     * @param key 想要清除的数据的键值
     */
    public void clearLocalData(String key) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


    public static String loadStringData(Context context, String sharedName, String key){
        return getInstance()._loadStringData(context,sharedName,key);
    }

    public static int loadIntData(Context context, String sharedName, String key){
        return getInstance()._loadIntData(context,sharedName,key);
    }

    public static float loadFloatData(Context context, String sharedName, String key){
        return getInstance()._loadFloatData(context,sharedName,key);
    }

    public static boolean loadBoolData(Context context, String sharedName, String key){
        return getInstance()._loadBoolData(context,sharedName,key);
    }

    public static long loadLongData(Context context, String sharedName, String key){
        return getInstance()._loadLongData(context,sharedName,key);
    }


    public static void storeLocalData(Context context,String sharedName,String key,boolean content){
        getInstance()._storeLocalData(context,sharedName,key,content);
    }

    public static void storeLocalData(Context context,String sharedName,String key,int content){
        getInstance()._storeLocalData(context,sharedName,key,content);
    }

    public static void storeLocalData(Context context,String sharedName,String key,String content){
        getInstance()._storeLocalData(context,sharedName,key,content);
    }

    public static void storeLocalData(Context context,String sharedName,String key,float content){
        getInstance()._storeLocalData(context,sharedName,key,content);
    }

    public static void storeLocalData(Context context,String sharedName,String key,long content){
        getInstance()._storeLocalData(context,sharedName,key,content);
    }

    /*********************************************************************************************/

    /**
     * 用于取出相应数据
     * @param context 所需上下文
     * @param sharedName 相应sharedPreferences的名字
     * @param key     想要取出的键值
     */
    private String _loadStringData(Context context, String sharedName, String key){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    private int _loadIntData(Context context, String sharedName, String key){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }

    private float _loadFloatData(Context context, String sharedName, String key){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key,0);
    }

    private boolean _loadBoolData(Context context, String sharedName, String key){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,true);
    }

    private long _loadLongData(Context context, String sharedName, String key){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,0);
    }


    /**
     * 用于存储相应数据
     * @param context 所需上下文
     * @param sharedName 相应sharedPreferences的名字
     * @param key     想要更改的键值
     * @param content 想要更改的内容
     */
    private void _storeLocalData(Context context,String sharedName,String key,boolean content){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,content);
        editor.apply();
    }

    private void _storeLocalData(Context context,String sharedName,String key,int content){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,content);
        editor.apply();
    }

    private void _storeLocalData(Context context,String sharedName,String key,String content){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,content);
        editor.apply();
    }

    private void _storeLocalData(Context context,String sharedName,String key,float content){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat(key,content);
        editor.apply();
    }

    private void _storeLocalData(Context context,String sharedName,String key,long content){
        sharedPreferences=context.getSharedPreferences(sharedName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putLong(key,content);
        editor.apply();
    }
}
