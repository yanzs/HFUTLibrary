package yanzs.hfutlibrary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.ResponseBody;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.callBack.VerifyCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnLoginCallBackListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class App_Login extends BaseActivity implements OnLoginCallBackListener {

    @BindView(R.id.login_img_captcha)
    ImageView login_img_captcha;
    @BindView(R.id.login_textinputedittext_captcha)
    TextInputEditText login_textinputedittext_captcha;
    @BindView(R.id.login_textinputedittext_number)
    TextInputEditText login_textinputedittext_number;
    @BindView(R.id.login_textinputedittext_passwd)
    TextInputEditText login_textinputedittext_passwd;
    @BindView(R.id.login_bt_click)
    Button login_bt_click;
    private boolean isCookieSet =true;

    private VerifyCallBack verifyCallBack=new VerifyCallBack(this);
    private ZLoadingDialog dialog;
    private String number,passwd,captcha;
    private Bitmap bitmap;
    private Handler handler=new Handler();
    private Runnable imgRun=new Runnable() {
        @Override
        public void run() {
            login_img_captcha.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void initActivity() {
        initView();
        initCaptchaImg();
    }

    private void initView() {
        GradientDrawable gradientDrawable= (GradientDrawable) login_bt_click.getBackground();
        gradientDrawable.setStroke(80, ColorUtil.getThemeColor(this));
        number= ShareUtil.loadStringData(this, ShareKey.SHARED_KEY,ShareKey.KEY_USER_NUMBER);
        passwd= ShareUtil.loadStringData(this, ShareKey.SHARED_KEY,ShareKey.KEY_USER_PASSWD);
        login_textinputedittext_number.setText(number);
        login_textinputedittext_passwd.setText(passwd);
    }

    private void initCaptchaImg() {
        Call call= OkHttpUtil.getCallFromGET(Urls.URL_LOGIN_CAPTCHA);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(App_Login.this, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody body = response.body();
                InputStream in = body.byteStream();
                bitmap = BitmapFactory.decodeStream(in);
                handler.post(imgRun);
            }
        });
    }

    private void initClickLogin(){
        if (isCookieSet){//避免因验证码错误产生的cookie清空
            OkHttpUtil.setCookies(HttpUrl.parse(Urls.URL_ORIGIN),ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,ShareKey.KEY_COOKIE));
        }
        number=login_textinputedittext_number.getText().toString();
        passwd=login_textinputedittext_passwd.getText().toString();
        captcha=login_textinputedittext_captcha.getText().toString();
        if (passwd.length()==0||number.length()==0||captcha.length()==0){
            Toast.makeText(this, Values.HINT_INPUT_ERROR, Toast.LENGTH_SHORT).show();
        }else {
            dialog=DialogUtil.initLoadDialog(this,Values.HINT_DIALOG_LOGIN);
            verifyCallBack.setOnLoginCallBackListener(this);
            OkHttpUtil.getResponseFromPOST(number, passwd, captcha, Urls.URL_LOGIN_VERIFY, verifyCallBack);
        }

    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_login;
    }

    @OnClick({R.id.login_img_captcha,R.id.login_bt_click})
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.login_img_captcha:
                initCaptchaImg();
                break;
            case R.id.login_bt_click:
                initClickLogin();
                break;
        }
    }

    @Override
    public void loginState(boolean b) {
        Intent intent=new Intent();
        intent.setClass(App_Login.this,App_Main.class);
        if (b){
            startActivity(intent);
            dialog.dismiss();
            ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_USER_NUMBER,number);
            ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_USER_PASSWD,passwd);
            finish();
        }else {
            Toast.makeText(this, Values.HINT_LOGIN_ERROR, Toast.LENGTH_SHORT).show();
            initCaptchaImg();
            isCookieSet =false;
            dialog.dismiss();
        }
    }
}
