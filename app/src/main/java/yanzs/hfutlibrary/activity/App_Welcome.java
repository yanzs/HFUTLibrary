package yanzs.hfutlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.yanzhenjie.permission.Permission;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.HttpUrl;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.callBack.CheckCallBack;
import yanzs.hfutlibrary.callBack.PermissionCallBack;
import yanzs.hfutlibrary.callBack.VerifyCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnLoginCallBackListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.PermissionUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class App_Welcome extends BaseActivity implements OnLoginCallBackListener {
    @BindView(R.id.welcome_rela_background)
    RelativeLayout background;
    private VerifyCallBack verifyCallBack=new VerifyCallBack(this);
    private Context context;
    @Override
    protected void initActivity() {
        context=this;
        verifyCallBack.setOnLoginCallBackListener(this);
        if (!ShareUtil.loadStringData(this, ShareKey.SHARED_KEY, ShareKey.KEY_COOKIE).equals("")){
            OkHttpUtil.setCookies(HttpUrl.parse(Urls.URL_ORIGIN),ShareUtil.loadStringData(this, ShareKey.SHARED_KEY, ShareKey.KEY_COOKIE));
        }
        try {
            OkHttpUtil.getResponseFromGET(Urls.URL_LOGIN_INFO, verifyCallBack);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void resetCookie(){
        try {
            OkHttpUtil.getResponseFromGET(Urls.URL_LOGIN_VERIFY,new CheckCallBack(this));
            ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_COOKIE,OkHttpUtil.getCookie(Urls.URL_ORIGIN));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initPermission(final Intent intent){
        PermissionUtil.requestPermission(this, new PermissionCallBack() {
            @Override
            public void onSuccess() {
                startActivity(intent);
                finish();
            }

            @Override
            public void onDefined() {
                Toast.makeText(context, Values.HINT_PERMISSION_ERROR, Toast.LENGTH_SHORT).show();
            }
        }, Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_welcome;
    }

    @Override
    public void loginState(boolean b) {
        Intent intent=new Intent();
        if (b){
            intent.setClass(App_Welcome.this,App_Main.class);
        }else {
            intent.setClass(App_Welcome.this,App_Login.class);
            resetCookie();
        }
        initPermission(intent);
    }
}
