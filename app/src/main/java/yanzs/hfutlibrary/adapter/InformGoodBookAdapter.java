package yanzs.hfutlibrary.adapter;

import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_GoodBook;
import yanzs.hfutlibrary.R;

public class InformGoodBookAdapter extends BaseListAdapter {
    private List<Bean_Inform_GoodBook> dataList;

    public InformGoodBookAdapter(List<Bean_Inform_GoodBook> dataList){
        this.dataList=dataList;
    }
    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_name=holder.getView(R.id.viewholder_inform_goodbook_text_name);
        TextView text_author=holder.getView(R.id.viewholder_inform_goodbook_text_author);
        TextView text_publish=holder.getView(R.id.viewholder_inform_goodbook_text_publish);
        text_name.setText(dataList.get(position).getName());
        text_author.setText(dataList.get(position).getAuthor());
        text_publish.setText(dataList.get(position).getPublish());

    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_goodbook;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
