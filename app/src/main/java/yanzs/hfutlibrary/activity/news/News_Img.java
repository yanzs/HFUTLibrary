package yanzs.hfutlibrary.activity.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.FileUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.view.ScaleImageView;

public class News_Img extends BaseActivity  {
    @BindView(R.id.news_img)
    ScaleImageView news_img;
    @BindView(R.id.news_img_rela_download)
    RelativeLayout news_rela_download;

    private Bitmap bitmap;
    private Handler handler = new Handler();
    private Runnable imgRun = new Runnable() {
        @Override
        public void run() {
            news_img.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void initActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        String url = getIntent().getStringExtra(Values.NEWS_INTENT_IMG_SIGN);
        Call call = OkHttpUtil.getCallFromGET(url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(News_Img.this, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.news_img_rela_download)
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.news_img_rela_download:
                if (bitmap!=null){
                    try {
                        String dirPath=Environment.getExternalStorageDirectory().getPath();
                        String fileName=System.currentTimeMillis() + ".jpg";
                        FileUtil.saveBitmapToFile(bitmap, dirPath, fileName);
                        Toast.makeText(this, "图片保存至"+dirPath+fileName, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_news_img;
    }

}
