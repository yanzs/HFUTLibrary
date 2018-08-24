package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Mine_Now;
import yanzs.hfutlibrary.R;

public class MineNowAdapter extends BaseListAdapter {
    private List<Bean_Mine_Now> data;

    public MineNowAdapter(List<Bean_Mine_Now> data){
        this.data=data;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_name=holder.getView(R.id.viewholder_mine_now_text_name);
        TextView text_lenddata=holder.getView(R.id.viewholder_mine_now_text_lenddata);
        TextView text_enddata=holder.getView(R.id.viewholder_mine_now_text_enddata);
        TextView text_lendnum=holder.getView(R.id.viewholder_mine_now_text_lendnum);
        TextView text_item=holder.getView(R.id.viewholder_mine_now_text_item);
        text_name.setText(data.get(position).getName());
        text_lenddata.setText(data.get(position).getName());
        text_enddata.setText(data.get(position).getName());
        text_lendnum.setText(data.get(position).getName());
        text_item.setText(data.get(position).getItem());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_mine_now;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
