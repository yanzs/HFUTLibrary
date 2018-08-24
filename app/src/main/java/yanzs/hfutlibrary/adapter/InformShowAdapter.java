package yanzs.hfutlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yanzs.hfutlibrary.base.BaseShowList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.R;

public class InformShowAdapter extends BaseShowList<String> {


    public InformShowAdapter(Context context, RelativeLayout rela_background, Activity activity,TextView textView) {
        super(context, rela_background, activity,textView);
        initDate();
    }

    private void initDate() {
        if (getResponseDou().getAuthor_intro()!=null&&getResponseDou().getAuthor_intro().length()>0){
            getData().add(getResponseDou().getAuthor_intro());
        }
        if (getResponseDou().getSummary()!=null&&getResponseDou().getSummary().length()>0){
            getData().add(getResponseDou().getSummary());
        }
        if (getResponseDou().getCatalog()!=null&&getResponseDou().getCatalog().length()>0){
            getData().add(getResponseDou().getCatalog());
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_show_main;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView main_text=holder.getView(R.id.viewholder_inform_main_text);
        main_text.setText(getData().get(position));
    }
}
