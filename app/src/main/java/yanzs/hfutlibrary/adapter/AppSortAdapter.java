package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.lang.ref.ReferenceQueue;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.R;

public class AppSortAdapter extends BaseListAdapter {

    private String[] dataList;



    public AppSortAdapter(String[] dataList){
        this.dataList=dataList;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView sort_text =holder.getView(R.id.viewholder_sort_text);
        sort_text.setText(dataList[position]);
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_sort;
    }

    @Override
    public int getItemCount() {
        return dataList.length;
    }
}
