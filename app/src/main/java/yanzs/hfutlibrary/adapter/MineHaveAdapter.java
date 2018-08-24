package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseRefreshListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Mine_Have;
import yanzs.hfutlibrary.R;

public class MineHaveAdapter extends BaseRefreshListAdapter<Bean_Mine_Have>{
    public MineHaveAdapter(List<Bean_Mine_Have> dataList, boolean isMore) {
        super(dataList, isMore);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_author=holder.getView(R.id.viewholder_mine_have_text_author);
        TextView text_lend=holder.getView(R.id.viewholder_mine_have_text_lend);
        TextView text_local=holder.getView(R.id.viewholder_mine_have_text_local);
        TextView text_name=holder.getView(R.id.viewholder_mine_have_text_name);
        TextView text_return=holder.getView(R.id.viewholder_mine_have_text_return);
        text_author.setText(getDataList().get(position).getAuthor());
        text_lend.setText(getDataList().get(position).getLendDate());
        text_local.setText(getDataList().get(position).getLocal());
        text_name.setText(getDataList().get(position).getTitle());
        text_return.setText(getDataList().get(position).getReturnData());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_mine_have;
    }

}
