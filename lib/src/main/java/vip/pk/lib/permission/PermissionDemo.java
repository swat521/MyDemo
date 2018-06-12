package vip.pk.lib.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import vip.pk.lib.R;
import vip.pk.lib.base.BaseActivity;
import vip.pk.pklib.utils.PermissionUtils2;

public class PermissionDemo extends BaseActivity {



    @Override
    public void initView() {
        setContentView(R.layout.permission_demo);
    }


    public void btn(View v) {
        PermissionUtils2.requestPermissionsResult(this, 1, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}
                , new PermissionUtils2.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(PermissionDemo.this, "授权成功,执行拨打电话操作!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied() {
                        PermissionUtils2.showTipsDialog(PermissionDemo.this);
                    }
                });
    }



}
