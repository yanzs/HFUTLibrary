package yanzs.hfutlibrary.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yanzhenjie.permission.Permission;

import java.util.List;

import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.callBack.PermissionCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.PermissionUtil;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.util.ThemeUtil;
import yanzs.hfutlibrary.view.CircleImageView;

public class MineInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private List<String> data;
    private Context context;
    private Activity activity;
    private final int RESULT_LOAD_BACKGROUND = 1000;
    private final int RESULT_LOAD_HEAD = 1100;

    private RelativeLayout rela_background;
    private CircleImageView cirimg_header;

    public MineInfoAdapter(Context context, List<String> data, Activity activity) {
        this.context = context;
        this.data = data;
        this.activity=activity;
    }

    public RelativeLayout getRela_background() {
        return rela_background;
    }

    public CircleImageView getCirimg_header() {
        return cirimg_header;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Values.VIEW_TYPE_NORMAL) {
            return new BaseViewHolder(parent, R.layout.module_viewholder_mine_info_item);
        } else {
            return new BaseViewHolder(parent, R.layout.module_viewholder_mine_info_header);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        if (getItemViewType(pos) == Values.VIEW_TYPE_NORMAL) {
            TextView text_left = holder.getView(R.id.viewholder_mine_info_item_text_left);
            TextView text_right = holder.getView(R.id.viewholder_mine_info_item_text_right);
            ImageView img_icon = holder.getView(R.id.viewholder_mine_info_item_img_icon);
            String left = data.get(position).substring(0, data.get(position).indexOf("："));
            String right = data.get(position).substring(data.get(position).indexOf("：") + 1, data.get(position).length());
            text_left.setText(left);
            text_right.setText(right);
            img_icon.setColorFilter(ColorUtil.getRandColorCode());
        } else if (getItemViewType(pos) == Values.VIEW_TYPE_HEADER) {
            rela_background = holder.getView(R.id.viewholder_mine_info_header_rela_background);
            cirimg_header = holder.getView(R.id.viewholder_mine_info_header_cirimg_header);
            TextView text_name = holder.getView(R.id.viewholder_mine_info_header_text_name);
            String name = data.get(0).substring(data.get(0).indexOf("：") + 1, data.get(0).length());
            text_name.setText(name);
            ThemeUtil.setUserHead(context, cirimg_header);
            ThemeUtil.setUserTheme(context, rela_background);
            rela_background.setOnClickListener(this);
            cirimg_header.setOnClickListener(this);
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
        return data.size();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.viewholder_mine_info_header_rela_background:
                chooseBackground();
                break;
            case R.id.viewholder_mine_info_header_cirimg_header:
                chooseHead();
                break;
        }
    }
    
    private void chooseBackground(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("选择背景");
        String[] items={"相册选择","默认背景"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        PermissionUtil.requestPermission(activity, new PermissionCallBack() {
                            @Override
                            public void onSuccess() {
                                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(i, RESULT_LOAD_BACKGROUND);
                            }

                            @Override
                            public void onDefined() {
                                Toast.makeText(context, Values.HINT_PERMISSION_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        }, Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE);
                        break;
                    case 1:
                        ShareUtil.storeLocalData(context, ShareKey.SHARED_KEY,ShareKey.KEY_THEME_BACKGROUND_DEFAULT,true);
                        ThemeUtil.setUserTheme(context, rela_background);
                        context.deleteFile(Values.UESR_DEFAULT_BACKGROUND);
                        break;
                }
            }
        });
        builder.show();
    }


    private void chooseHead(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("选择头像");
        String[] items={"相册选择","默认头像"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        PermissionUtil.requestPermission(activity, new PermissionCallBack() {
                            @Override
                            public void onSuccess() {
                                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(i, RESULT_LOAD_HEAD);
                            }

                            @Override
                            public void onDefined() {
                                Toast.makeText(context, Values.HINT_PERMISSION_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        }, Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE);
                        break;
                    case 1:
                        ShareUtil.storeLocalData(context, ShareKey.SHARED_KEY,ShareKey.KEY_THEME_HEAD_DEFAULT,true);
                        ThemeUtil.setUserHead(context, cirimg_header);
                        context.deleteFile(Values.UESR_DEFAULT_HEAD);
                        break;
                }
            }
        });
        builder.show();
    }

}
