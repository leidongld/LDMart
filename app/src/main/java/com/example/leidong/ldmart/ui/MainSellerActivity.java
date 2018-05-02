package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.fragments.MySellerFragment;
import com.example.leidong.ldmart.fragments.OrdersSellerFragment;
import com.example.leidong.ldmart.fragments.ProductsSellerFragment;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家主界面
 *
 * @author Lei Dong
 */
public class MainSellerActivity extends Activity implements OnTabSelectListener {
    private static final String TAG = "MainSellerActivity";

    //界面的layout
    @BindView(R.id.content_container)
    FrameLayout mContentContainer;

    //底部导航栏
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    //FragmentManager
    private FragmentManager mFragmentManager;

    //Intent
    private Intent mIntent;
    //Bundle
    private Bundle mBundle;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    //用户身饭码
    private int mUserMode;

    //卖家Id
    private Long mSellerId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_main);

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
        mBundle = mIntent.getBundleExtra(Constants.SELLER_DATA);

        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(this);
        mUserMode = mBundle.getInt(Constants.USER_MODE);
        mSellerId = mBundle.getLong(Constants.SELLER_ID);
        mMySharedPreferences.save(Constants.USER_MODE, mUserMode);
        mMySharedPreferences.save(Constants.SELLER_ID, mSellerId);

        mFragmentManager = getFragmentManager();
        ProductsSellerFragment productsSellerFragment = new ProductsSellerFragment();
        productsSellerFragment.setArguments(mBundle);
        mFragmentManager.beginTransaction().replace(R.id.content_container, productsSellerFragment).commit();
    }

    /**
     * 底部导航栏的选择
     *
     * @param tabId tab编号
     */
    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            //商品tab
            case R.id.tab_products:
                mFragmentManager = getFragmentManager();
                ProductsSellerFragment productsSellerFragment = new ProductsSellerFragment();
                productsSellerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, productsSellerFragment).commit();
                break;
            //订单tab
            case R.id.tab_orders:
                mFragmentManager = getFragmentManager();
                OrdersSellerFragment ordersSellerFragment = new OrdersSellerFragment();
                ordersSellerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, ordersSellerFragment).commit();
                break;
            //我的tab
            case R.id.tab_my:
                mFragmentManager = getFragmentManager();
                MySellerFragment mySellerFragment = new MySellerFragment();
                mySellerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, mySellerFragment).commit();
                break;
        }
    }
}
