package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yanzs.hfutlibrary.base.BaseHeadList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.Bean_Inform_Detail;
import yanzs.hfutlibrary.bean.Bean_Inform_Local;
import yanzs.hfutlibrary.R;

public class InformDetailAdapter extends BaseHeadList<Bean_Inform_Local> {


    public InformDetailAdapter(Context context, ImageView backImg, Bean_Inform_Detail data, List<Bean_Inform_Local> mdata) {
        super(context, backImg, data, mdata);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView text_index=holder.getView(R.id.viewholder_inform_detail_text_index);
        TextView text_local=holder.getView(R.id.viewholder_inform_detail_text_local);
        TextView text_state=holder.getView(R.id.viewholder_inform_detail_text_state);
        String index="检索号:"+getData().getBeanInformLocals().get(position-1).getIndex();
        text_index.setText(index);
        String state="书籍状态:"+getData().getBeanInformLocals().get(position-1).getDate();
        text_local.setText(getData().getBeanInformLocals().get(position-1).getLocal());
        text_state.setText(state);
    }

    @Override
    protected int getResId() {
        return R.layout.module_viewholder_inform_detail;
    }
}
