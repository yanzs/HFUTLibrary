package yanzs.hfutlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseChartList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.responsemine.Rootmonthsort;
import yanzs.hfutlibrary.util.ColorUtil;

public class MineLendLineAdapter extends BaseChartList<Rootmonthsort> {

    private int[] color;

    public MineLendLineAdapter(List<Rootmonthsort> data, Context context) {
        super(data, context);
    }

    @Override
    protected int getHeadResId() {
        return R.layout.module_viewholder_mine_lend_line_header;
    }

    @Override
    protected int getNormalResId() {
        return R.layout.module_viewholder_mine_lend_item;
    }

    @Override
    protected void initHeadView(BaseViewHolder holder) {
        LineChartView lineChartView = holder.getView(R.id.viewholder_mine_lend_head_linechart);
        initData(lineChartView);
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView textView=holder.getView(R.id.viewholder_mine_lend_item_text);
        TextView num=holder.getView(R.id.viewholder_mine_lend_item_text_num);
        String s=getData().get(position-1).getYear()+"."+position;
        textView.setText(s);
        num.setBackgroundColor(color[position-1]);
        String snum=""+getData().get(position-1).getY();
        num.setText(snum);
    }

    private void initData(LineChartView lineChartView){
        List<PointValue> values = new ArrayList<>();
        color=new int[getData().size()];
        for (int i=0;i<getData().size();i++){
            color[i]=ColorUtil.getRandColorCode();
            values.add(new PointValue(Integer.parseInt(getData().get(i).getMonth())+1,getData().get(i).getY()));
        }
        List<Line> lines=new ArrayList<>();
        Line line = new Line(values).setColor(ColorUtil.getThemeColor(getContext()));
        line.setCubic(false);
        line.setHasLabels(true);
        lines.add(line);
        lineChartView.setInteractive(true);
        lineChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        LineChartData chartData=new LineChartData();
        Axis axisX=new Axis();
        Axis axisY=new Axis();
        chartData.setAxisXBottom(axisX);
        chartData.setAxisYLeft(axisY);
        chartData.setLines(lines);
        lineChartView.setLineChartData(chartData);
    }
}
