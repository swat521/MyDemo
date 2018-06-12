package vip.pk.lib.clipboard;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;

public class ClipboardMonitor extends BaseActivity {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private ClipboardService.ClipboardServiceBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (ClipboardService.ClipboardServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void initView() {
        setContentView(R.layout.clipboard_demo);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
    public void btnStartService(View v){
        Intent startIntent = new Intent(ClipboardMonitor.this, ClipboardService.class);
        Bundle b= new Bundle();
        b.putString("packageName",getPackageName());
        b.putString("activityName","vip.pk.lib.clipboard.ResultActivity");
        startIntent.putExtras(b);
        startService(startIntent);
    }

    public void btnEndService(View v){
        Intent stopIntent = new Intent(ClipboardMonitor.this, ClipboardService.class);
        stopService(stopIntent);
    }

    public void btnBindService(View v){
        Intent bindIntent = new Intent(this, ClipboardService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    public void btnUnBindService(View v){
        unbindService(connection);
    }


}
