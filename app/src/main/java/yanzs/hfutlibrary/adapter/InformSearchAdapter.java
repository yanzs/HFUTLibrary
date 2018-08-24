package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseRefreshListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_Search;
import yanzs.hfutlibrary.R;

public class InformSearchAdapter extends BaseRefreshListAdapter<Bean_Inform_Search> {

    public InformSearchAdapter(List<Bean_Inform_Search> dataList, boolean isMore) {
        super(dataList, isMore);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_name=holder.getView(R.id.viewholder_inform_search_text_name);
        TextView text_author=holder.getView(R.id.viewholder_inform_search_text_author);
        TextView text_index=holder.getView(R.id.viewholder_inform_search_text_index);
        TextView text_publisher=holder.getView(R.id.viewholder_inform_search_text_publisher);
        text_name.setText(getDataList().get(position).getTitle());
        text_author.setText(getDataList().get(position).getAuthor());
        text_index.setText(getDataList().get(position).getCallNo());
        text_publisher.setText(getDataList().get(position).getPublisher());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_search;
    }

}
