package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseRefreshListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_WillBuy;
import yanzs.hfutlibrary.R;

public class InformWillBuyAdapter extends BaseRefreshListAdapter<Bean_Inform_WillBuy> {
    public InformWillBuyAdapter(List<Bean_Inform_WillBuy> dataList, boolean isMore) {
        super(dataList, isMore);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_name=holder.getView(R.id.viewholder_inform_willbuy_text_name);
        TextView text_author=holder.getView(R.id.viewholder_inform_willbuy_text_author);
        TextView text_data=holder.getView(R.id.viewholder_inform_willbuy_text_data);
        TextView text_state=holder.getView(R.id.viewholder_inform_willbuy_text_state);
        TextView text_publisher=holder.getView(R.id.viewholder_inform_willbuy_text_publisher);
        text_name.setText(getDataList().get(position).getTitle());
        text_author.setText(getDataList().get(position).getAuthor());
        text_data.setText(getDataList().get(position).getData());
        text_state.setText(getDataList().get(position).getState());
        text_publisher.setText(getDataList().get(position).getPublisher());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_willbuy;
    }
}
