package yanzs.hfutlibrary.adapter;

import android.graphics.Color;
import android.widget.TextView;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;

public class TabIndexAdapter extends BaseListAdapter {
    @Override
    protected void initItemView(BaseViewHolder holder, int position) {

        TextView text_area=holder.getView(R.id.viewholder_index_text_area);
        TextView text_book=holder.getView(R.id.viewholder_index_text_book);
        TextView text_floor=holder.getView(R.id.viewholder_index_text_floor);
        TextView text_room=holder.getView(R.id.viewholder_index_text_room);
        text_area.setBackgroundColor(ColorUtil.getRandColorCode());
        text_floor.setBackgroundColor(ColorUtil.getRandColorCode());
        text_area.setText(Values.INDEX_ITEM[position][1]);
        text_book.setText(Values.INDEX_ITEM[position][3]);
        text_floor.setText(Values.INDEX_ITEM[position][0]);
        text_room.setText(Values.INDEX_ITEM[position][2]);
        if (Values.INDEX_ITEM[position][2].length()>0){
            text_room.setBackgroundColor(ColorUtil.getRandColorCode());
        }else {
            text_room.setBackgroundColor(Color.WHITE);
        }
        if (Values.INDEX_ITEM[position][3].length()>0){
            text_book.setBackgroundColor(ColorUtil.getRandColorCode());
        }else {
            text_book.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_index;
    }

    @Override
    public int getItemCount() {
        return Values.INDEX_ITEM.length;
    }
}
