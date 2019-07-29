package com.zhenghe.kindofdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.zhenghe.kindofdemo.R;
import com.zhenghe.kindofdemo.permission.HPermisson;
import com.zhenghe.kindofdemo.permission.PermissionCallback;

import java.util.List;

/**
 * Created by zh on 2019/7/29.
 */
public class PermissionActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Button btn_permission = (Button) findViewById(R.id.my_permission);
        Button btn_xxpermission = (Button) findViewById(R.id.xx_permission);
        Button btn_haspermission = (Button) findViewById(R.id.has_permission);
        Button btn_setpermission = (Button) findViewById(R.id.set_permission);

        btn_permission.setOnClickListener(this);
        btn_xxpermission.setOnClickListener(this);
        btn_haspermission.setOnClickListener(this);
        btn_setpermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_permission: //使用自定义的动态申请权限框架去申请
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};
                HPermisson.with(this)
                        .permisson(strings)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onPermissionGranted(List<String> granted, boolean isAll) {
                                if(isAll){
                                    Toast.makeText(PermissionActivity.this, "所有的权限都申请通过了", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(PermissionActivity.this, "部分权限没有通过", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void shouldShowRational(String permisson) {
                                Toast.makeText(PermissionActivity.this, "权限：" + permisson + " 被拒绝，但没有勾选不再显示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissonReject(String permisson) {
                                Toast.makeText(PermissionActivity.this, "权限：" + permisson + " 被拒绝，但勾选了不再显示", Toast.LENGTH_SHORT).show();
                                //这里就需要再次申请的时候，调用这自己做处理，因为系统不会在弹出申请权限的提示框
                            }
                        })
                        .request();
                break;
            case R.id.xx_permission: //使用第三方框架XXPermissions去动态申请权限,GitHub地址https://github.com/getActivity/XXPermissions
                String[] strings_xx = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};
                XXPermissions.with(this)
                        .permission(strings_xx)
                        .request(new OnPermission() {
                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                //granted表示已经获取到的权限列表,isAll表示申请的权限有没有全部允许
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                //denied表示没有获取到的权限列表，quick表示denied集合中是否有某个权限被永久拒绝了，即勾选了「不再提示」的框框

                                Toast.makeText(PermissionActivity.this, "勾选不再显示--" + quick, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.has_permission: //检查某些权限是否全部授予了
                String[] strings_has = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};
                if (XXPermissions.isHasPermission(this, strings_has)) {
                    Toast.makeText(PermissionActivity.this, "location 和 camera 权限全部授予了", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PermissionActivity.this, "location 和 camera 权限没有全部授予", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.set_permission: //跳转到设置页面
                XXPermissions.gotoPermissionSettings(this);
                break;
        }
    }
}
