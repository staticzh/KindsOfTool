package com.zhenghe.kindofdemo.permission;

import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 2019/7/23.
 */
public class PermissionUtil {
    static List<String> getSucceedPermissions(String[] permissions, int[] grantResults) {

        List<String> succeedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {

            // 把授予过的权限加入到集合中，-1表示没有授予，0表示已经授予
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                succeedPermissions.add(permissions[i]);
            }
        }
        return succeedPermissions;
    }
}
