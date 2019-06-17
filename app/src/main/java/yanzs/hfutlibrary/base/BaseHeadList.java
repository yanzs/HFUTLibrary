package yanzs.hfutlibrary.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import yanzs.hfutlibrary.activity.inform.Inform_Show;
import yanzs.hfutlibrary.bean.Bean_Inform_Detail;
import yanzs.hfutlibrary.bean.newdouban.Items;
import yanzs.hfutlibrary.bean.newdouban.RootNewDou;
import yanzs.hfutlibrary.bean.responsedou.RootResponseDou;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.GsonUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.util.WebJsUtil;

public abstract class BaseHeadList<M> extends RecyclerView.Adapter<BaseViewHolder> implements WebJsUtil.JsCallBack{
    private Bean_Inform_Detail data;
    private List<M> mdata;
    private Bitmap bitmap;
    private Handler handler = new Handler();
    private OnItemClickListener onItemClickListener;
    private Context context;
    private ImageView backImg,img_cover;
    private RelativeLayout rela_background;
    private TextView text_title, text_author;
    private RequestCallBack callBack;
    private RootNewDou responseDou;
    private String imgUrl;
    private String bookUrl;
    private WebJsUtil webJsUtil;
    private Runnable imgRun;
    private ZLoadingDialog dialog;


    public BaseHeadList(Context context, ImageView backImg, Bean_Inform_Detail data, List<M> mdata) {
        this.context = context;
        this.backImg = backImg;
        this.data = data;
        this.mdata = mdata;
        webJsUtil=new WebJsUtil(context);
        webJsUtil.setJsCallBack(this);
    }


    public Bean_Inform_Detail getData() {
        return data;
    }

    @Override
    public void dealJsResult(String s) {
        responseDou= GsonUtil.getResponseRootDou(s);
        if (responseDou!=null&&responseDou.getPayload()!=null&&responseDou.getPayload().getItems().size()>0){
            Items items=responseDou.getPayload().getItems().get(0);
            imgUrl=items.getCover_url();
            bookUrl=items.getUrl();
            ShareUtil.storeLocalData(context,ShareKey.SHARED_KEY,ShareKey.KEY_INFORM_PAGE_BOOK_ID,""+items.getId());;
        }
        if (imgUrl!=null&&imgUrl.length()>0){
            initCoverImg(imgRun);
        }else {
            int color=ColorUtil.getThemeColor(context);
            rela_background.setBackgroundColor(color);//为LinearLayout添加背景色
            backImg.setBackgroundColor(color);
            img_cover.setImageDrawable(context.getResources().getDrawable(R.drawable.default_img));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Values.VIEW_TYPE_NORMAL) {
            return new BaseViewHolder(parent, getResId());
        } else {
            return new BaseViewHolder(parent, R.layout.module_viewholder_header);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        if (getItemViewType(pos) == Values.VIEW_TYPE_NORMAL) {
            initItemView(holder, position);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.itemClick(pos);
                    }
                });
            }
        } else if (getItemViewType(pos) == Values.VIEW_TYPE_HEADER) {
            rela_background = holder.getView(R.id.viewholder_header_rela_backgroud);
            img_cover = holder.getView(R.id.viewholder_header_img_cover);
            text_title = holder.getView(R.id.viewholder_header_text_title);
            text_author = holder.getView(R.id.viewholder_header_text_author);
            imgRun = new Runnable() {
                @Override
                public void run() {
                    img_cover.setImageBitmap(bitmap);
                    Palette palette = Palette.generate(bitmap);
                    int vibrantDark = palette.getDarkVibrantColor(ColorUtil.getThemeColor(context));
                    int dark= ColorUtil.colorBurn(vibrantDark);
                    rela_background.setBackgroundColor(dark);//为LinearLayout添加背景色
                    backImg.setBackgroundColor(dark);
                }
            };
            text_title.setTextColor(Color.WHITE);
            text_author.setTextColor(Color.WHITE);
            text_author.setText(getData().getWriter());
            text_title.setText(getData().getName());
            try {
                initView(imgRun);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bookUrl!=null&&bookUrl.length()>0){
                        dialog= DialogUtil.initLoadDialog(context,Values.HINT_DIALOG_LOAD);
                        RequestCallBack requestCallBack=new RequestCallBack(ShareKey.KEY_INFORM_PAGE_BOOK_INFO,context);
                        requestCallBack.setOnFinishRequestListener(new OnFinishRequestListener() {
                            @Override
                            public void afterRequest(String sign) {
                                if (sign.equals(ShareKey.KEY_INFORM_PAGE_BOOK_INFO)){
                                    Intent intent=new Intent();
                                    intent.setClass(context, Inform_Show.class);
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }
                            }
                        });
                        try {
                            OkHttpUtil.getResponseFromGET(bookUrl,requestCallBack);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(context,Values.HINT_SHOW_ERROR,Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Values.VIEW_TYPE_HEADER;
        } else {
            return Values.VIEW_TYPE_NORMAL;
        }

    }

    @Override
    public int getItemCount() {
        return mdata.size() + 1;
    }

    private void initView(final Runnable imgRun) throws IOException {
        if (getData().getImgUrl().length()>0){
            callBack=new RequestCallBack( ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL_IMG,context);
            callBack.setOnFinishRequestListener(new OnFinishRequestListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void afterRequest(String sign) {
                    if (sign.equals(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL_IMG)){
                        String s= JsoupUtil.getDoubanWinData(context);
                        webJsUtil.dealMusicJsMethod(s);
                    }
                }
            });
            OkHttpUtil.getResponseFromGET(getData().getImgUrl(),callBack);
        }else {
            int color=ColorUtil.getThemeColor(context);
            rela_background.setBackgroundColor(color);//为LinearLayout添加背景色
            backImg.setBackgroundColor(color);
            img_cover.setImageDrawable(context.getResources().getDrawable(R.drawable.default_img));
        }

    }

    private void initCoverImg(final Runnable runnable) {
        Call call = OkHttpUtil.getCallFromGET(imgUrl);
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

    protected abstract int getResId();
}
