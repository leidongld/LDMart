package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.fragments.MyBuyerFragment;
import com.example.leidong.ldmart.fragments.OrdersBuyerFragment;
import com.example.leidong.ldmart.fragments.ProductsBuyerFragment;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买家主界面
 *
 * @author Lei Dong
 */
public class MainBuyerActivity extends Activity implements OnTabSelectListener {
    private static final String TAG = "MainBuyerActivity";

    //界面的layout
    @BindView(R.id.content_container)
    FrameLayout mContentContainer;

    //底部导航栏
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    //FragmentManager
    private FragmentManager mFragmentManager;

    //用户模式码
    private int mUserMode;
    //买家Id
    private Long mBuyerId;

    //Bundle
    private Bundle mBundle;

    //Intent
    private Intent mIntent;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_main);

        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mBottomBar.setOnTabSelectListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mIntent = getIntent();
        mBundle = mIntent.getBundleExtra(Constants.BUYER_DATA);

        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(this);
        mUserMode = mBundle.getInt(Constants.USER_MODE);
        mBuyerId = mBundle.getLong(Constants.BUYER_ID);
        mMySharedPreferences.save(Constants.USER_MODE, mUserMode);
        mMySharedPreferences.save(Constants.BUYER_ID, mBuyerId);

        //加载Fragment
        mFragmentManager = getFragmentManager();
        ProductsBuyerFragment productsBuyerFragment = new ProductsBuyerFragment();
        mFragmentManager.beginTransaction().replace(R.id.content_container, productsBuyerFragment).commit();
    }

    /**
     * 底部导航栏的点击
     *
     * @param tabId tab编号
     */
    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            //商品tab
            case R.id.tab_products:
                mFragmentManager = getFragmentManager();
                ProductsBuyerFragment productsBuyerFragment = new ProductsBuyerFragment();
                mFragmentManager.beginTransaction().replace(R.id.content_container, productsBuyerFragment).commit();
                break;
            //订单tab
            case R.id.tab_orders:
                mFragmentManager = getFragmentManager();
                OrdersBuyerFragment ordersBuyerFragment = new OrdersBuyerFragment();
                mFragmentManager.beginTransaction().replace(R.id.content_container, ordersBuyerFragment).commit();
                break;
            //我的tab
            case R.id.tab_my:
                mFragmentManager = getFragmentManager();
                MyBuyerFragment myBuyerFragment = new MyBuyerFragment();
                mFragmentManager.beginTransaction().replace(R.id.content_container, myBuyerFragment).commit();
                break;
            default:
                break;
        }
    }
}
