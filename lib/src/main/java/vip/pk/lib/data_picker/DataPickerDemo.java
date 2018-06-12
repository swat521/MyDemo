package vip.pk.lib.data_picker;

import android.view.View;
import android.widget.TextView;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class DataPickerDemo extends BaseActivity {

    private String strDate;
    private String strtime;
    private String strDateTime;


    @Override
    public void initView() {

        setContentView(R.layout.data_picker_demo);
        // 日期 格式 yyyy-MM-dd
        ((TextView) findViewById(R.id.tv_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(DataPickerDemo.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                strDate = time;
                                ((TextView) findViewById(R.id.tv_date)).setText(strDate);
                            }
                        });
                endDateChooseDialog.setTimePickerGone(true);
                endDateChooseDialog.setDateDialogTitle("自定义标题部分");
                endDateChooseDialog.showDateChooseDialog();
            }
        });

        //时间
        ((TextView) findViewById(R.id.tv_time)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(DataPickerDemo.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                strtime = time;
                                ((TextView) findViewById(R.id.tv_time)).setText(strtime);
                            }
                        });
                endDateChooseDialog.setTimePickerGone(false);
                endDateChooseDialog.setDateDialogTitle("自定义标题部分");
                endDateChooseDialog.showDateChooseDialog();
            }
        });
    }
}
