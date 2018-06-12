package vip.pk.lib.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class ChartDemo extends BaseActivity implements View.OnClickListener{

    private BarChart mBarChart;

    //显示顶点值
    private Button btn_show_values;
    //x轴动画
    private Button btn_anim_x;
    //y轴动画
    private Button btn_anim_y;
    //xy轴动画
    private Button btn_anim_xy;
    //保存到sd卡
    private Button btn_save_pic;
    //切换自动最大最小值
    private Button btn_auto_mix_max;
    //高亮显示
    private Button btn_actionToggleHighlight;
    //显示边框
    private Button btn_show_border;

    protected String[] values = new String[]{
            "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
    };


    //初始化
    @Override
    public void initView() {
        setContentView(R.layout.chart_demo);
        //基本控件
        btn_show_values = (Button) findViewById(R.id.btn_show_values);
        btn_show_values.setOnClickListener(this);
        btn_anim_x = (Button) findViewById(R.id.btn_anim_x);
        btn_anim_x.setOnClickListener(this);
        btn_anim_y = (Button) findViewById(R.id.btn_anim_y);
        btn_anim_y.setOnClickListener(this);
        btn_anim_xy = (Button) findViewById(R.id.btn_anim_xy);
        btn_anim_xy.setOnClickListener(this);
        btn_save_pic = (Button) findViewById(R.id.btn_save_pic);
        btn_save_pic.setOnClickListener(this);
        btn_auto_mix_max = (Button) findViewById(R.id.btn_auto_mix_max);
        btn_auto_mix_max.setOnClickListener(this);
        btn_actionToggleHighlight = (Button) findViewById(R.id.btn_actionToggleHighlight);
        btn_actionToggleHighlight.setOnClickListener(this);
        btn_show_border = (Button) findViewById(R.id.btn_show_border);
        btn_show_border.setOnClickListener(this);


        mBarChart = (BarChart) findViewById(R.id.mBarChart);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setTouchEnabled(false);
        mBarChart.setDragEnabled(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //自定义x轴显示
        MyXFormatter formatter = new MyXFormatter(values);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(formatter);

        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.animateY(2500);
        mBarChart.getLegend().setEnabled(false);

        setData();
    }

    //设置数据
    private void setData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 10; i++) {
            float mult = 50;
            float val = (float) (Math.random() * mult) + mult / 3;
            yVals1.add(new BarEntry(i, val));
        }
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "日期设置");
            //设置多彩 也可以单一颜色
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mBarChart.setData(data);
            mBarChart.setFitBars(true);
            for (IDataSet set : mBarChart.getData().getDataSets()) {
                set.setDrawValues(!set.isDrawValuesEnabled());
            }
        }
        mBarChart.invalidate();
    }




    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_show_values){ //显示顶点值
            for (IDataSet set : mBarChart.getData().getDataSets())
                set.setDrawValues(!set.isDrawValuesEnabled());

            mBarChart.invalidate();
        }else if(v.getId()==R.id.btn_anim_x){//x轴动画
            mBarChart.animateX(3000);
        }else if(v.getId()==R.id.btn_anim_y){//y轴动画
            mBarChart.animateY(3000);
        }else if(v.getId()==R.id.btn_anim_xy){ //xy轴动画
            mBarChart.animateXY(3000, 3000);
        }else if(v.getId()==R.id.btn_save_pic){//保存到sd卡
            if (mBarChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                Toast.makeText(getApplicationContext(), "保存成功",
                        Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "保存失败",
                        Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.btn_auto_mix_max){//切换自动最大最小值
            mBarChart.setAutoScaleMinMaxEnabled(!mBarChart.isAutoScaleMinMaxEnabled());
            mBarChart.notifyDataSetChanged();
        }else if(v.getId()==R.id.btn_actionToggleHighlight){//高亮显示
            if (mBarChart.getData() != null) {
                mBarChart.getData().setHighlightEnabled(
                        !mBarChart.getData().isHighlightEnabled());
                mBarChart.invalidate();
            }
        }else if(v.getId()==R.id.btn_show_border){//显示边框
            for (IBarDataSet set : mBarChart.getData().getDataSets())
                ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);
            mBarChart.invalidate();
        }

    }
    class MyXFormatter  implements IAxisValueFormatter {

        private String[] mValues;

        public MyXFormatter(String[] values) {
            this.mValues = values;
        }
        private static final String TAG = "MyXFormatter";

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            Log.d(TAG, "----->getFormattedValue: "+value);
            return mValues[(int) value % mValues.length];
        }
    }
}
