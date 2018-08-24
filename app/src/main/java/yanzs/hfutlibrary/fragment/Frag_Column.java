package yanzs.hfutlibrary.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.adapter.MineLendColumnAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.bean.responsemine.Rootyearsort;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.JsoupUtil;

public class Frag_Column extends BaseFragment {

    @BindView(R.id.frag_lend_column_recycle)
    RecyclerView frag_lend_column_recycle;
    private MineLendColumnAdapter mineLendColumnAdapter;
    private List<Rootyearsort> data;

    @Override
    protected void initFragment() {
        data = JsoupUtil.getMineSortYear(getContext());
        mineLendColumnAdapter=new MineLendColumnAdapter(data,getContext());
        frag_lend_column_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        frag_lend_column_recycle.setAdapter(mineLendColumnAdapter);
    }



    @Override
    protected int getResId() {
        return R.layout.module_fragment_mine_lend_column;
    }
}
