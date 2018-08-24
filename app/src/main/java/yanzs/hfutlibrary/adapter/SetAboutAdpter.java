package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.constant.Values;

public class SetAboutAdpter extends BaseListAdapter {
    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_left=holder.getView(R.id.viewholder_set_about_text_left);
        TextView text_right=holder.getView(R.id.viewholder_set_about_text_right);
        text_left.setText(Values.SET_ABOUT_TEXT[position]);
        text_right.setText(Values.SET_ABOUT_ITEM[position]);
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_set_about;
    }

    @Override
    public int getItemCount() {
        return Values.SET_ABOUT_TEXT.length;
    }
}
