package com.zhenghe.kindofdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhenghe.kindofdemo.activity.PermissionActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button activity_per = (Button) findViewById(R.id.activity_permission);

        activity_per.setOnClickListener(this);
    }

    public void startAct(Class cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_permission:
                startAct(PermissionActivity.class);
                break;
        }
    }
}
