package vip.pk.lib.progress_bar;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class ProgressBarDemo extends BaseActivity implements View.OnClickListener , Runnable{

    @Override
    public void initView() {
        setContentView(R.layout.progress_bar_demo);

        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);
        roundProgressbar = (FlikerProgressBar) findViewById(R.id.round_flikerbar);

        flikerProgressBar.setOnClickListener(this);
        roundProgressbar.setOnClickListener(this);

        downLoad();
    }

    FlikerProgressBar flikerProgressBar;
    FlikerProgressBar roundProgressbar;

    Thread downLoadThread;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            roundProgressbar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
                roundProgressbar.finishLoad();
            }
        }
    };



    public void reLoad(View view) {

        downLoadThread.interrupt();
        // 重新加载
        flikerProgressBar.reset();
        roundProgressbar.reset();

        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        if(!flikerProgressBar.isFinish()){
            flikerProgressBar.toggle();
            roundProgressbar.toggle();

            if(flikerProgressBar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }

        }
    }

    @Override
    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = flikerProgressBar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
