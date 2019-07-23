package com.zhenghe.kindofdemo.permission;

import java.util.List;

/*
 * 权限申请回调
 */
public interface PermissionCallback {

    /**
     * 权限申请通过回调
     * @param granted 被允许的权限集合
     * @param isAll   是否所有申请的权限都被允许
     */
    void onPermissionGranted(List<String> granted, boolean isAll);


    /**
     * 权限被拒绝，但是没有勾选“不再提醒"
     * @param permisson 被拒绝的权限
     */
    void shouldShowRational(String permisson);

    /**
     * 权限被拒绝，并且勾选了"不再提醒"，即彻底被拒绝
     * @param permisson 被拒绝的权限
     */
    void onPermissonReject(String permisson);
}