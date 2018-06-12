package vip.pk.lib.clipboard;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ClipboardService extends Service {

    private static final String TAG = "ClipboardService";

    private static int count = 0;

    public static final String clipboardText = "clipboardText";

    private ClipboardServiceBinder binder = new ClipboardServiceBinder();
    private String data_json;

    class ClipboardServiceBinder extends Binder {
    }


    public ClipboardService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String packageName = (String) intent.getExtras().get("packageName");
        final String activityName = (String) intent.getExtras().get("activityName");

        final ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {

            @Override
            public void onPrimaryClipChanged() {

                if (cb.hasPrimaryClip()) {
                    for (int i = 0; i < cb.getPrimaryClip().getItemCount(); i++) {

                        Log.d(TAG, "onPrimaryClipChanged() " + cb.getPrimaryClip().getItemAt(i).getText());
                        if (cb.getPrimaryClip().getItemAt(i).getText() != null && cb.getPrimaryClip().getItemAt(i).getText().length() > 0) {
                            if (!cb.getPrimaryClip().getItemAt(i).getText().toString().trim().isEmpty()) {
                                String clipboardItem = cb.getPrimaryClip().getItemAt(i).getText().toString().trim();


                                Intent intent = new Intent();
                                intent.setClassName(packageName, activityName);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(clipboardText, clipboardItem);
                                startActivity(intent);

                                break;
                            }
                        }
                    }
                }

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder{
        public void setData(int money){
            //调用办证的方法
        }
    }


}