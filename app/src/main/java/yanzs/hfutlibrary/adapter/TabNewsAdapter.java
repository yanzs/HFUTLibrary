package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.widget.TextView;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;

public class TabNewsAdapter extends BaseListAdapter {


    private Context context;

    public TabNewsAdapter(Context context){
        this.context=context;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_sign=holder.getView(R.id.viewholder_tab_text_sign);
        TextView text_title=holder.getView(R.id.viewholder_tab_text_title);
        text_sign.setTextColor(ColorUtil.getThemeColor(context));
        text_sign.setText(Values.TAB_NEWS_ITEM_SIGN[position]);
        text_title.setText(Values.TAB_NEWS_ITEM_TITLE[position]);
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_tab;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
