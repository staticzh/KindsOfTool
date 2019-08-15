package com.zhenghe.kindofdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zh on 2019/8/15.
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 从应用本地存储data/data/包名中读取文件内容
     * @param context
     * @return
     */
    public static String getFromStream(Context context,String fileName){
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int b;
            while((b = fis.read()) != -1) {
                baos.write(b);													//将读取到的数据逐个写到内存中
            }
            Log.e(TAG,"str::" + baos.toString());
            fis.close();
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缓存目录的获取
     * @param context
     * @param uniqueName 传入的目录名称
     * @return           返回cache/uniqueName目录
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //如果SD卡存在或者sd卡不可移除的时候，使用 /sdcard/Android/data/<application package>/cache 目录
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //否则的话，使用/data/data/<application package>/cache目录
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 解析审计配置文件，返回配置列表
     */
    private static Map<String,String> getConfigList(String fileName, Context context) {
        HashMap<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            //格式化配置文件成对象
            inputStream = context.getAssets().open(fileName);
            String strategy = readInputSream(inputStream);

            JSONObject object = new JSONObject(strategy);
            Iterator<String> iterator = object.keys();
            while (iterator.hasNext()){

                String key = iterator.next();
                Object value = object.get(key);
                map.put(key,value.toString());
            }

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }

    /**
     * 将流转换成字符串
     */
    private static String readInputSream(InputStream in) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        in.close();


        return baos.toString();
    }
}
