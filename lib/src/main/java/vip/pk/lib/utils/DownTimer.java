package vip.pk.lib.utils;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

 /**
 * DownTimer
 * @author cjj
 * @category 倒计时工具类
 * @version 1.0
 * @content
 *  <b>1.实例化后必须设置倒计时的总时间（totalTime）和每隔多久时间（intervalTime）回调</b><br>
 *  <b>2.有start()、 cancel()、 pause()、resume() 四个方法看方法就知道它的意思了 </b>
 
 
 final TextView tv_scend = (TextView) findViewById(R.id.text1);
        tv_scend.setText("总共一分钟");
        timer = new DownTimer();//实例化
        timer.setTotalTime(60*1000);//设置毫秒数
        timer.setIntervalTime(1000);//设置间隔数
        timer.setTimerLiener(new DownTimer.TimeListener() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "完成倒计时", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterval(long remainTime) {
                tv_scend.setText("还剩" + remainTime / 1000+"秒就完成了");//剩余多少秒
            }
        });
 
 
 @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:
                timer.start();
                break;
            case R.id.pause:
                timer.pause();
                break;
            case R.id.cancel:
                timer.cancel();
                break;
            case R.id.resume:
                timer.resume();
                break;
        }
    }
 
 
 
 
 
 
 
 
 */
public class DownTimer {
    private long totalTime = -1;
    private long intervalTime = 0;
    private long remainTime;
    private long systemAddTotalTime;
    private static final int TIME = 1;
    private TimeListener listener;
    private long curReminTime;
    private boolean isPause = false;

    public DownTimer() {
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void start() {
        if (totalTime <= 0 && intervalTime <= 0) {
            throw new RuntimeException("you must set the totalTime > 0 or intervalTime >0");
        }

        systemAddTotalTime = SystemClock.elapsedRealtime() + totalTime;

        if (null != mHandler)
            mHandler.sendEmptyMessage(TIME);
    }

    public void cancel() {
        if(mHandler !=null)
        {
            mHandler.removeMessages(TIME);
            mHandler = null;
        }

    }

    public void pause() {
        if(mHandler !=null)
        {
            mHandler.removeMessages(TIME);
            isPause = true;
            curReminTime = remainTime;
        }

    }

    public void resume() {
        if (isPause == true) {
            isPause = false;
            totalTime = curReminTime;
            start();
        }

    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    if (!isPause)
                        soloveTime();
                    break;
                case 2:
                    isPause = true;
                    break;
                default:
                    break;
            }
        }
    };


    private void soloveTime() {
        remainTime = systemAddTotalTime - SystemClock.elapsedRealtime();
        if (remainTime <= 0) {
            if (listener != null) {
                listener.onFinish();
                cancel();
            }
        } else if (remainTime < intervalTime) {
            if (null != mHandler)
                mHandler.sendEmptyMessageDelayed(TIME, remainTime);
        } else {
            long curSystemTime = SystemClock.elapsedRealtime();
            if (listener != null) {
                listener.onInterval(remainTime);
            }

            long delay = curSystemTime + intervalTime - SystemClock.elapsedRealtime();

            while (delay < 0) delay += intervalTime;

            if (null != mHandler) {
                mHandler.sendEmptyMessageDelayed(TIME, delay);
            }
        }
    }

    public interface TimeListener {
        public void onFinish();
        public void onInterval(long remainTime);
    }

    public void setTimerLiener(TimeListener listener) {
        this.listener = listener;
    }

}
