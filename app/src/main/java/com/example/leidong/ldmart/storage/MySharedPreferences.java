package com.example.leidong.ldmart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 自定义MySharedPreferences用于存数各项配置信息
 */
public class MySharedPreferences {
    private Context context;
    private SharedPreferences sharedPreferences;
    private static MySharedPreferences mySharedPreferences;

    public MySharedPreferences(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("LDSP", Context.MODE_PRIVATE);
    }

    public static MySharedPreferences getMySharedPreferences(Context context){
        if(mySharedPreferences == null){
            mySharedPreferences = new MySharedPreferences(context);
        }
        return mySharedPreferences;
    }

    //布尔型数据
    public void save(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean load(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    //整型数据
    public void save(String key, int value){
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public int load(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }

    //长整型数据
    public void save(String key, long value){
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public long load(String key, long defaultValue){
        return sharedPreferences.getLong(key, defaultValue);
    }

    //浮点型数据
    public void save(String key, float value){
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public float load(String key, float defaultValue){
        return sharedPreferences.getFloat(key, defaultValue);
    }

    //字符串数据
    public void save(String key, String value){
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String load(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}
