package yanzs.hfutlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import yanzs.hfutlibrary.bean.responsedou.RootResponseDou;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.FileUtil;
import yanzs.hfutlibrary.util.GsonUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;

public abstract class BaseShowList<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private RootResponseDou responseDou;
    private Bitmap bitmap;
    private Handler handler = new Handler();
    private OnItemClickListener onItemClickListener;
    private Context context;
    private RelativeLayout rela_background;
    private List<T> data;
    private Activity activity;
    private TextView text_download;

    public BaseShowList(final Context context, RelativeLayout rela_background, Activity activity, TextView textView) {
        data=new ArrayList<>();
        text_download=textView;
        this.context = context;
        this.rela_background=rela_background;
        this.activity=activity;
        responseDou = GsonUtil.getResponseRootDou(context);
        text_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap!=null){
                    try {
                        String dirPath= Environment.getExternalStorageDirectory().getPath();
                        String fileName=System.currentTimeMillis() + ".jpg";
                        FileUtil.saveBitmapToFile(bitmap, dirPath, fileName);
                        Toast.makeText(context, "图片保存至"+dirPath+fileName, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "图片存储失败，默认图片无法下载", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public List<T> getData() {
        return data;
    }

    public RootResponseDou getResponseDou() {
        return responseDou;
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Values.VIEW_TYPE_NORMAL) {
            return new BaseViewHolder(parent, getResId());
        } else {
            return new BaseViewHolder(parent, R.layout.module_viewholder_inform_show_headimg);
        }
    }

    protected abstract int getResId();

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Values.VIEW_TYPE_HEADER;
        } else {
            return Values.VIEW_TYPE_NORMAL;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        if (getItemViewType(pos) == Values.VIEW_TYPE_NORMAL) {
            initItemView(holder, position-1);
        } else if (getItemViewType(pos) == Values.VIEW_TYPE_HEADER) {
            final ImageView img_cover = holder.getView(R.id.viewholder_inform_show_img_cover);
            TextView text_title = holder.getView(R.id.viewholder_inform_show_text_title);
            if (responseDou.getTitle()!=null&&responseDou.getTitle().length()>0){
                text_title.setText(responseDou.getTitle());
            }else {
                text_title.setText(Values.HINT_SHOW_ERROR);
            }
            Runnable imgRun = new Runnable() {
                @Override
                public void run() {
                    img_cover.setImageBitmap(bitmap);
                    Palette palette = Palette.generate(bitmap);
                    int vibrantDark = palette.getDarkVibrantColor(ColorUtil.getThemeColor(context));
                    int dark= ColorUtil.colorBurn(vibrantDark);
                    rela_background.setBackgroundColor(dark);//为LinearLayout添加背景色
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.getWindow().setStatusBarColor(dark);
                    }
                }
            };
            if (responseDou.getImage()!=null&&responseDou.getImage().length()>0){
                initCoverImg(imgRun);
            }else {
                rela_background.setBackgroundColor(ColorUtil.getThemeColor(context));
                img_cover.setImageDrawable(context.getResources().getDrawable(R.drawable.default_img));
            }


        }
    }

    private void initCoverImg(final Runnable runnable) {
        Call call = OkHttpUtil.getCallFromGET(responseDou.getImage());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(context, Values.HINT_NET_ERROR, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody body = response.body();
                assert body != null;
                InputStream in = body.byteStream();
                bitmap = BitmapFactory.decodeStream(in);
                handler.post(runnable);
            }
        });
    }

    protected abstract void initItemView(BaseViewHolder holder, int position);


}
