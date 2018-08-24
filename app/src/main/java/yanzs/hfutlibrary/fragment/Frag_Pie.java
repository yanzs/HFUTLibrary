package yanzs.hfutlibrary.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.adapter.MineLendColumnAdapter;
import yanzs.hfutlibrary.adapter.MineLendPieAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.bean.responsemine.Rootclass;
import yanzs.hfutlibrary.bean.responsemine.Rootclasssort;
import yanzs.hfutlibrary.util.JsoupUtil;

public class Frag_Pie extends BaseFragment{

    @BindView(R.id.frag_lend_pie_recycle)
    RecyclerView frag_lend_pie_recycle;
    private MineLendPieAdapter mineLendPieAdapter;
    private List<Rootclasssort> data;
    @Override
    protected void initFragment() {
        data = JsoupUtil.getMineSortClass(getContext());
        mineLendPieAdapter=new MineLendPieAdapter(data,getContext());
        frag_lend_pie_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_lend_pie_recycle.setAdapter(mineLendPieAdapter);
    }

    @Override
    protected int getResId() {
        return R.layout.module_fragment_mine_lend_pie;
    }
}
