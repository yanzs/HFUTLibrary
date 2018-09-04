package yanzs.hfutlibrary.activity.mine;

import android.content.Context;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class Mine_Renew extends BaseActivity implements OnFinishRequestListener{

    @BindView(R.id.mine_renew_bt_renew)
    Button bt_renew;
    @BindView(R.id.mine_renew_textinputedittext_captcha)
    TextInputEditText editText;
    @BindView(R.id.mine_renew_img_captcha)
    ImageView img_captcha;
    private String string;
    private Bitmap bitmap;
    private Context context;
    private Handler handler = new Handler();
    private Runnable imgRun = new Runnable() {
        @Override
        public void run() {
            img_captcha.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void initActivity() {
        context=this;
        string=getIntent().getStringExtra(Values.MINE_INTENT_RENEW_SIGN);
        GradientDrawable gradientDrawable = (GradientDrawable) bt_renew.getBackground();
        gradientDrawable.setStroke(80, ColorUtil.getThemeColor(this));
        initCaptchaImg();
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_mine_renew;
    }

    private void initCaptchaImg() {
        Call call = OkHttpUtil.getCallFromGET(Urls.URL_IMG_CAPTCHA, Urls.URL_HOST, Urls.URL_MINE_RENEW_CAPTCHA_REFERER);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(Mine_Renew.this, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.mine_renew_bt_renew, R.id.mine_renew_img_back, R.id.mine_renew_img_captcha})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mine_renew_img_captcha:
                initCaptchaImg();
                break;
            case R.id.mine_renew_img_back:
                finish();
                break;
            case R.id.mine_renew_bt_renew:
                renewClick();
                break;
        }
    }

    private void renewClick() {
        String s=editText.getText().toString();
        if (s.length() ==0){
            Toast.makeText(this, Values.HINT_INPUT_ERROR, Toast.LENGTH_SHORT).show();
            return;
        }
        String url=Urls.URL_MINE_RENEW+string+"&captcha="+s+"&time="+getData();
        try {
            RequestCallBack callBack=new RequestCallBack(ShareKey.KEY_USER_RENEW,context);
            callBack.setOnFinishRequestListener(this);
            OkHttpUtil.getResponseFromGET(url, Urls.URL_HOST, Urls.URL_MINE_RENEW_CAPTCHA_REFERER,callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getData(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.SECOND);
    }

    @Override
    public void afterRequest(String sign) {
        String result=ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,sign);
        result=JsoupUtil.getTxtFromHtml(result);
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
