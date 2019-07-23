package com.zhenghe.kindofdemo.permission;

import android.content.Context;

public class HPermisson {
    // 权限申请回调
    private PermissionCallback callback;
    // 需要申请的权限
    private String[] permissions;
    private Context context;

    public HPermisson(Context context) {
        this.context = context;
    }

    public static HPermisson with(Context context) {
        HPermisson permisson = new HPermisson(context);
        return permisson;
    }

    public HPermisson permisson(String[] permissons) {
        this.permissions = permissons;
        return this;
    }

    public HPermisson callback(PermissionCallback callback) {
        this.callback = callback;
        return this;
    }

    public void request() {
        if (permissions == null || permissions.length <= 0) {
            return;
        }
        PermissionActivity.request(context, permissions, callback);
    }
}