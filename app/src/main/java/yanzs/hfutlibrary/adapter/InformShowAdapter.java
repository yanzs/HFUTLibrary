package yanzs.hfutlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yanzs.hfutlibrary.base.BaseShowList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.constant.Values;

public class InformShowAdapter extends BaseShowList<String> {


    public InformShowAdapter(Context context, RelativeLayout rela_background, Activity activity,TextView textView) {
        super(context, rela_background, activity,textView);
        initDate();
    }

    private void initDate() {
        if (getResponseDou()!=null&&getResponseDou().getStrings()!=null){
            setData(getResponseDou().getStrings());
        }else {
            List<String> data=new ArrayList<>();
            data.add(Values.HINT_SHOW_ERROR);
            setData(data);
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
