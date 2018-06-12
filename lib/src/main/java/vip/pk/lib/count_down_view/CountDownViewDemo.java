package vip.pk.lib.count_down_view;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class CountDownViewDemo  extends BaseActivity {

    private CountDownView countDownView;

    @Override
    public void initView() {

        setContentView(R.layout.count_down_timer);
        countDownView=(CountDownView)findViewById(R.id.countDownView);
        countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
            @Override
            public void start() {
                Toast.makeText(CountDownViewDemo.this, "倒计时开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void finish() {
                Toast.makeText(CountDownViewDemo.this, "倒计时结束", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 开始
     * @param view
     */
    public void onStart(View view) {
        countDownView.startCountdown();
    }

    /**
     * 停止
     * @param view
     */
    public void onStop(View view) {
        countDownView.stopCountdown();
    }

}
