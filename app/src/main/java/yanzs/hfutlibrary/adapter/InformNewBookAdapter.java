package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseRefreshListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_NewBook;
import yanzs.hfutlibrary.R;

public class InformNewBookAdapter extends BaseRefreshListAdapter<Bean_Inform_NewBook>{

    public InformNewBookAdapter(List<Bean_Inform_NewBook> dataList, boolean isMore) {
        super(dataList, isMore);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_name=holder.getView(R.id.viewholder_inform_newbook_text_name);
        TextView text_author=holder.getView(R.id.viewholder_inform_newbook_text_author);
        TextView text_index=holder.getView(R.id.viewholder_inform_newbook_text_index);
        text_name.setText(getDataList().get(position).getName());
        text_author.setText(getDataList().get(position).getPublish());
        text_index.setText(getDataList().get(position).getIndex());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_newbook;
    }

}
