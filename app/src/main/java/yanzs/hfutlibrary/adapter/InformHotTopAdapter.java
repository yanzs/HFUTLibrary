package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_HotTop;
import yanzs.hfutlibrary.R;

public class InformHotTopAdapter extends BaseListAdapter {
    private List<Bean_Inform_HotTop> dataList;
    private Context context;
    private int[] imgId={R.drawable.inform_hottop_first,R.drawable.inform_hottop_second,R.drawable.inform_hottop_third};
    public InformHotTopAdapter(List<Bean_Inform_HotTop> dataList, Context context){
        this.dataList=dataList;
        this.context=context;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_sign=holder.getView(R.id.viewholder_inform_hottop_text_sign);
        TextView text_title =holder.getView(R.id.viewholder_inform_hottop_text_title);
        TextView text_time =holder.getView(R.id.viewholder_inform_hottop_text_time);
        ImageView img_sign=holder.getView(R.id.viewholder_inform_hottop_img_sign);
        if (position<3){
            text_sign.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            text_sign.setTextColor(Color.RED);
            text_sign.setTextSize(16);
            img_sign.setImageDrawable(context.getResources().getDrawable(imgId[position]));
        }else {
            text_sign.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            text_sign.setTextColor(Color.YELLOW);
            text_sign.setTextSize(14);
            if (Integer.parseInt(dataList.get(position).getTime())>500){
                img_sign.setImageDrawable(context.getResources().getDrawable(R.drawable.inform_hottop_hot));
            }else {
                img_sign.setImageDrawable(null);
            }
        }
        text_title.setText(dataList.get(position).getValue());
        text_time.setText(dataList.get(position).getTime());
        int pos=position+1;
        text_sign.setText(""+pos);
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_hottop;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
