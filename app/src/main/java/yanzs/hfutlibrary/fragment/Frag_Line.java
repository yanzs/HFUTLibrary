package yanzs.hfutlibrary.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.adapter.MineLendLineAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.bean.responsemine.Rootmonthsort;
import yanzs.hfutlibrary.util.JsoupUtil;

public class Frag_Line extends BaseFragment{

    @BindView(R.id.frag_lend_line_recycle)
    RecyclerView frag_lend_line_recycle;
    private List<Rootmonthsort> data;
    private MineLendLineAdapter mineLendLineAdapter;

    @Override
    protected void initFragment() {
        data= JsoupUtil.getMineSortMonth(getContext());
        mineLendLineAdapter=new MineLendLineAdapter(data,getContext());
        frag_lend_line_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_lend_line_recycle.setAdapter(mineLendLineAdapter);
    }

    @Override
    protected int getResId() {
        return R.layout.module_fragment_mine_lend_line;
    }
}
