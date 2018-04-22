package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Root;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.RootDao;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.utils.FontUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 应用欢迎界面
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private MySharedPreferences mMySharedPreferences;

    private boolean isFirstLogin;

    @BindView(R.id.tv_app_name)
    TextView mAppName;

    @BindView(R.id.activity_splash)
    LinearLayout mSplashActivityLayout;

    private RootDao mRootDao;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        initWidgets();

        initActions();

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(Constants.SPLASH_ACTIVITY_ANIMATION_DURATION);
        mSplashActivityLayout.setAnimation(alphaAnimation);



        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, Constants.SPLASH_ACTIVITY_ANIMATION_DURATION);
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        if(isFirstLogin){
            //添加root用户
            mRootDao = MyApplication.getInstance().getDaoSession().getRootDao();
            Root root = new Root();
            root.setUsername(Constants.ROOT_USERNAME);
            root.setPassword(Constants.ROOT_PASSWORD);
            mRootDao.insert(root);

            mMySharedPreferences.save(Constants.IS_ROOT_EXIST, false);
        }
    }

    /**
     * 初始化控件
     */
    private void initWidgets() {
        FontUtils.setFontFromAssets(mAppName, "fonts/tongkati.ttf");

        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(SplashActivity.this);
        isFirstLogin = mMySharedPreferences.load(Constants.IS_FIRST_LOGIN, true);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
