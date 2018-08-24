package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseRefreshListAdapter;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_News;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;

public class NewsListAdapter extends BaseRefreshListAdapter<Bean_News> {
    private int color;
    public NewsListAdapter(List<Bean_News> dataList, boolean isMore, Context context) {
        super(dataList, isMore);
        color= ColorUtil.getThemeColor(context);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        RelativeLayout rela_calendar=holder.getView(R.id.viewholder_news_list_rela_calendar);
        rela_calendar.setBackgroundColor(color);
        TextView text_month=holder.getView(R.id.viewholder_news_list_text_month);
        TextView text_day=holder.getView(R.id.viewholder_news_list_text_day);
        TextView text_title=holder.getView(R.id.viewholder_news_list_text_title);
        text_title.setText(getDataList().get(position).getTitle());
        text_day.setText(getDataList().get(position).getDay());
        text_month.setText(getDataList().get(position).getMonth());
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_news_list;
    }
}
