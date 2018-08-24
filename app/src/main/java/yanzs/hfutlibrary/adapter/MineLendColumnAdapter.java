package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseChartList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.responsemine.Rootyear;
import yanzs.hfutlibrary.bean.responsemine.Rootyearsort;
import yanzs.hfutlibrary.util.ColorUtil;

public class MineLendColumnAdapter extends BaseChartList<Rootyearsort>{
    private int[] color;

    public MineLendColumnAdapter(List<Rootyearsort> data, Context context) {
        super(data, context);
    }

    @Override
    protected int getHeadResId() {
        return R.layout.module_viewholder_mine_lend_column_header;
    }

    @Override
    protected int getNormalResId() {
        return R.layout.module_viewholder_mine_lend_item;
    }

    @Override
    protected void initHeadView(BaseViewHolder holder) {
        ColumnChartView columnChartView= holder.getView(R.id.viewholder_mine_lend_head_columnchart);
        initData(columnChartView);
    }


    private void initData(ColumnChartView columnChartView) {
        ColumnChartData chartData;
        int numColumns = getData().size();
        color=new int[numColumns];
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < numColumns; i++) {
            values = new ArrayList<>();
            color[i]=ColorUtil.getRandColorCode();
            values.add(new SubcolumnValue(getData().get(i).getY(),color[i]));
            axisValues.add(new AxisValue(i).setLabel(getData().get(i).getLabel()));
            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
        }
        chartData = new ColumnChartData(columns);
        chartData.setAxisXBottom(new Axis(axisValues));
        chartData.setAxisYLeft(new Axis());
        columnChartView.setColumnChartData(chartData);
    }


    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView textView=holder.getView(R.id.viewholder_mine_lend_item_text);
        ImageView imageView=holder.getView(R.id.viewholder_mine_lend_item_img);
        textView.setText(getData().get(position-1).getLabel());
        imageView.setBackgroundColor(color[position-1]);
    }
}
