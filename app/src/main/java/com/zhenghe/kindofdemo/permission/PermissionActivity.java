package com.zhenghe.kindofdemo.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.List;

/**
 * Created by zh on 2019/7/23.
 * 用来动态申请权限的透明Activity
 */
public class PermissionActivity extends Activity{

    /*
     * 添加一个静态方法方便使用
     */
    public static void request(Context context, String[] permissions, PermissionCallback callback) {
        CALLBACK = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(KEY_PERMISSIONS, permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    public static final String KEY_PERMISSIONS = "permissions";
    private static final int RC_REQUEST_PERMISSION = 100;
    private static PermissionCallback CALLBACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (!intent.hasExtra(KEY_PERMISSIONS)) {
            return;
        }
        // 当api大于23时，才进行权限申请
        String[] permissions = getIntent().getStringArrayExtra(KEY_PERMISSIONS);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, RC_REQUEST_PERMISSION);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != RC_REQUEST_PERMISSION) {
            return;
        }
        // 处理申请结果
        boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];
        for (int i = 0; i < permissions.length; ++i) {
            shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }

        this.onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale);

    }

    @TargetApi(23)
    void onRequestPermissionsResult(String[] permissions, int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        int length = permissions.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale[i] == true){
                    CALLBACK.shouldShowRational(permissions[i]);
                } else {
                    CALLBACK.onPermissonReject(permissions[i]);
                }
            }
        }

        List<String> succeedPermissions = PermissionUtil.getSucceedPermissions(permissions, grantResults);
        if (succeedPermissions.size() == length) {
            CALLBACK.onPermissionGranted(succeedPermissions,true);
        }else {
            // 证明还有一部分权限被成功授予，回调成功接口
            if (!succeedPermissions.isEmpty()) {
                CALLBACK.onPermissionGranted(succeedPermissions,false);
            }
        }
        finish();
    }
}


