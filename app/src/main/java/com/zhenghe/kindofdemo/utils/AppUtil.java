package com.zhenghe.kindofdemo.utils;

import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by zh on 2019/8/15.
 */
public class AppUtil {

    private static final String TAG = "AppUtil";
    /**
     *
     * @return true:表示手机设置了密码  false:表示手机没有设置密码
     */
    public static boolean deviceLock(Context context){
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean lock = keyguardManager.isKeyguardSecure();
        Log.e(TAG,"lock::" + lock);
        return lock;
    }

    /**
     * 激活设备管理器页面
     * @param context
     * @param clazz
     */
    public  static void activeManage(Context context,Class<? extends DeviceAdminReceiver> clazz) {
        DevicePolicyManager policyManager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, clazz);
        // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活设备管理");
        if (!policyManager.isAdminActive(componentName)) {
            //设备管理器没有激活则激活
            context.startActivity(intent);
        }
    }

    /**
     * 判断应用是否已经开启了“查看其它应用使用情况”的权限
     */
    public static boolean enableCheck(Context context){
        if(hasOption(context)){
            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){   // 如果大于等于5.0 再做判断
                long ts = System.currentTimeMillis();
                UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(Service.USAGE_STATS_SERVICE);
                List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
                if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 跳转打开“查看其它应用使用情况”页面
     */
    public static void openUsageAccessPage(Context context){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            //修改判断是否有查看其它应用权限的逻辑，
            boolean allowUsageStats = enableCheck(context);
            if (!allowUsageStats) {//未拥有权限
                Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 判断当前设备中是否有"有权查看使用情况的应用程序"这个选项：
     */
    private static boolean hasOption(Context context) {

        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
