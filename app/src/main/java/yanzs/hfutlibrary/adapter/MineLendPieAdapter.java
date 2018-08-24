package yanzs.hfutlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseChartList;
import yanzs.hfutlibrary.base.BaseViewHolder;
import yanzs.hfutlibrary.bean.responsemine.Rootclasssort;
import yanzs.hfutlibrary.util.ColorUtil;

public class MineLendPieAdapter extends BaseChartList<Rootclasssort>{

    private int[] color;
    private PieChartData pieChartData;

    public MineLendPieAdapter(List<Rootclasssort> data, Context context) {
        super(data, context);
    }

    @Override
    protected int getHeadResId() {
        return R.layout.module_viewholder_mine_lend_pie_header;
    }

    @Override
    protected int getNormalResId() {
        return R.layout.module_viewholder_mine_lend_item;
    }

    @Override
    protected void initHeadView(BaseViewHolder holder) {
        PieChartView pieChartView=holder.getView(R.id.viewholder_mine_lend_head_piechart);
        initData(pieChartView);
        pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                pieChartData.setCenterText1Color(color[arcIndex]);
                pieChartData.setCenterText1(getData().get(arcIndex).getLegendText());
                pieChartData.setCenterText2(value.getValue() + "（" + calPercent(arcIndex) + ")");
                pieChartData.setCenterText2Color(color[arcIndex]);

            }

            @Override
            public void onValueDeselected() {

            }
        });
    }

    private void initData(PieChartView pieChartView) {
        List<SliceValue> values;
        color=new int[getData().size()];
        values=new ArrayList<>();
        for (int i=0;i<getData().size();i++){
            color[i]= ColorUtil.getRandColorCode();
            SliceValue sliceValue=new SliceValue(getData().get(i).getY(),color[i]);
            values.add(sliceValue);
        }
        pieChartData=new PieChartData();
        pieChartData.setCenterText1FontSize(12);
        pieChartData.setCenterText2FontSize(12);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(false);
        pieChartData.setHasLabelsOutside(false);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setValues(values);
        pieChartData.setCenterCircleColor(Color.WHITE);
        pieChartData.setCenterCircleScale(0.5f);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(true);
        pieChartView.setCircleFillRatio(1f);
    }



    @SuppressLint("DefaultLocale")
    private String calPercent(int j) {
        String result = "";
        int sum = 0;
        for (int i = 0; i < getData().size(); i++) {
            sum += getData().get(i).getY();
        }
        result = String.format("%.2f", (float) getData().get(j).getY() * 100 / sum) + "%";
        return result;
    }

    @Override
    protected void initItemView(BaseViewHolder holder, int position) {
        TextView textView=holder.getView(R.id.viewholder_mine_lend_item_text);
        ImageView imageView=holder.getView(R.id.viewholder_mine_lend_item_img);
        textView.setText(getData().get(position-1).getLegendText());
        imageView.setBackgroundColor(color[position-1]);
    }
}
