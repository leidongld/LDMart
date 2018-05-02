package com.example.leidong.ldmart;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.leidong.ldmart.greendao.DaoMaster;
import com.example.leidong.ldmart.greendao.DaoSession;

/**
 * App初始化类
 *
 * @author Lei Dong
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private static AssetManager sAssetManager;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static MyApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();

        sContext = getApplicationContext();

        sAssetManager = getAssets();

        instance = this;

        setDatabase();
    }

    /**
     * 得到MyApplication对象
     *
     * @return
     */
    public static MyApplication getInstance(){
        return instance;
    }

    /**
     * 得到当前Context
     *
     * @return
     */
    public static Context getsContext(){
        return sContext;
    }

    /**
     * 得到AssetManager
     *
     * @return
     */
    public static AssetManager getsAssetManager(){
        return sAssetManager;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "leidong.db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 得到DaoSession对象
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 得到SQLite数据库对象
     *
     * @return
     */
    public SQLiteDatabase getDb() {
        return db;
    }

}
