package com.zhenghe.kindofdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhenghe.kindofdemo.permission.HPermisson;
import com.zhenghe.kindofdemo.permission.PermissionCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_permission = (Button) findViewById(R.id.permission);

        btn_permission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.permission:
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};
                HPermisson.with(this)
                        .permisson(strings)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onPermissionGranted(List<String> granted, boolean isAll) {
                                if(isAll){
                                    Toast.makeText(MainActivity.this, "所有的权限都申请通过了", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "部分权限没有通过", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void shouldShowRational(String permisson) {
                                Toast.makeText(MainActivity.this, "权限：" + permisson + " 被拒绝，但没有勾选不再显示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissonReject(String permisson) {
                                Toast.makeText(MainActivity.this, "权限：" + permisson + " 被拒绝，但勾选了不再显示", Toast.LENGTH_SHORT).show();
                                //这里就需要再次申请的时候，调用这自己做处理，因为系统不会在弹出申请权限的提示框
                            }
                        })
                        .request();
                break;
        }
    }
}
