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
 */
public class MainBuyerActivity extends Activity implements OnTabSelectListener {
    private static final String TAG = "MainBuyerActivity";

    @BindView(R.id.content_container)
    FrameLayout mContentContainer;

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private FragmentManager mFragmentManager;

    private int mUserMode;
    private Long mBuyerId;

    private Bundle mBundle;
    private Intent mIntent;

    private MySharedPreferences mMySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buyer_main);

        ButterKnife.bind(this);

        initWidgets();

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

        mFragmentManager = getFragmentManager();
        ProductsBuyerFragment productsBuyerFragment = new ProductsBuyerFragment();
//        productsBuyerFragment.setArguments(mBundle);
        mFragmentManager.beginTransaction().replace(R.id.content_container, productsBuyerFragment).commit();
    }

    /**
     * 底部导航栏的点击
     * @param tabId
     */
    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            case R.id.tab_products:
                mFragmentManager = getFragmentManager();
                ProductsBuyerFragment productsBuyerFragment = new ProductsBuyerFragment();
//                productsBuyerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, productsBuyerFragment).commit();
                break;
            case R.id.tab_orders:
                mFragmentManager = getFragmentManager();
                OrdersBuyerFragment ordersBuyerFragment = new OrdersBuyerFragment();
//                ordersBuyerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, ordersBuyerFragment).commit();
                break;
            case R.id.tab_my:
                mFragmentManager = getFragmentManager();
                MyBuyerFragment myBuyerFragment = new MyBuyerFragment();
//                myBuyerFragment.setArguments(mBundle);
                mFragmentManager.beginTransaction().replace(R.id.content_container, myBuyerFragment).commit();
                break;
            default:
                break;
        }
    }
}
