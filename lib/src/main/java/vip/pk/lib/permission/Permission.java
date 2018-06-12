package vip.pk.lib.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import javax.security.auth.callback.Callback;

import vip.pk.pklib.utils.PermissionUtils2;

public class Permission {

    public static void RequestPermissions(final Context c,String[] permissionList,final PermissionCallBack permissionCallBack){
        PermissionUtils2.requestPermissionsResult((Activity)c, 1,permissionList
                , new PermissionUtils2.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //Toast.makeText(c, "授权成功,执行拨打电话操作!", Toast.LENGTH_SHORT).show();
                        permissionCallBack.PermisionResult(true);
                    }

                    @Override
                    public void onPermissionDenied() {
                        permissionCallBack.PermisionResult(false);
                        //PermissionUtils2.showTipsDialog(c);
                    }
                });
    }

    public static void PermissionDialog(Context c){
        PermissionUtils2.showTipsDialog(c);
    }


}
