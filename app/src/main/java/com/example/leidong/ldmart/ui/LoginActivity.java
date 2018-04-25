package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.SellerDao;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.utils.AuthenticateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登陆界面
 */
public class LoginActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private static final String TAG = "LoginActivity";

    //界面整体Layout
    @BindView(R.id.ll_login_activity)
    LinearLayout mLoginLayout;

    //用户名输入框
    @BindView(R.id.et_username)
    EditText mUsernameEt;

    //密码输入框
    @BindView(R.id.et_password)
    EditText mPasswordEt;

    //用户身份选择的RadioGroup
    @BindView(R.id.rg_selector)
    RadioGroup mRadioSelector;

    //RadioButton 买家
    @BindView(R.id.rbtn_buyer)
    RadioButton mBuyerRbtn;

    //RadioButton 卖家
    @BindView(R.id.rbtn_seller)
    RadioButton mSellerRbtn;

    //RadioButton 管理员
    @BindView(R.id.rbtn_root)
    RadioButton mRootRbtn;

    //登录按钮
    @BindView(R.id.btn_login)
    Button mLoginBtn;

    /**
     * 用户身份码
     * buyer  1
     * seller 2
     * root   3
     */
    private int modeNumber = 0;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    //BuyerDao
    private BuyerDao mBuyerDao;

    //SellerDao
    private SellerDao mSellerDao;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

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
        mRadioSelector.setOnCheckedChangeListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mLoginBtn.setVisibility(View.GONE);

        //更改标志，并非第一次登陆
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(LoginActivity.this);
        mMySharedPreferences.save(Constants.IS_FIRST_LOGIN, true);

        mBuyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        mSellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
    }

    /**
     * RadioGroup的点选择操作
     * @param radioGroup RadioGroup
     * @param i 编号
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        i = radioGroup.getCheckedRadioButtonId();

        String usernameTemp = mUsernameEt.getText().toString().trim();
        String passwordTemp = mPasswordEt.getText().toString().trim();

        //选择买家模式
        if(i == R.id.rbtn_buyer){
            mLoginLayout.setBackgroundResource(R.drawable.bg_buyer);
            modeNumber = Constants.BUYER_MODE;
            if(usernameTemp.length() > 0 && passwordTemp.length() > 0){
                mLoginBtn.setVisibility(View.VISIBLE);
            }
        }
        //选择卖家模式
        else if(i == R.id.rbtn_seller){
            mLoginLayout.setBackgroundResource(R.drawable.bg_seller);
            modeNumber = Constants.SELLER_MODE;
            if(usernameTemp.length() > 0 && passwordTemp.length() > 0){
                mLoginBtn.setVisibility(View.VISIBLE);
            }
        }
        //选择管理员模式
        else if(i == R.id.rbtn_root){
            mLoginLayout.setBackgroundResource(R.drawable.bg_root);
            modeNumber = Constants.ROOT_MODE;
            if(usernameTemp.length() > 0 && passwordTemp.length() > 0){
                mLoginBtn.setVisibility(View.VISIBLE);
            }
        }
        else{

        }
    }

    /**
     * 组件点击操作
     * @param view 点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                clickLoginBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击登录按钮
     */
    private void clickLoginBtn() {
        String usernameTemp = mUsernameEt.getText().toString().trim();
        String passwordTemp = mPasswordEt.getText().toString().trim();

        //买家模式
        if(modeNumber == Constants.BUYER_MODE){
            if(AuthenticateUtils.authenticateBuyer(usernameTemp, passwordTemp)) {
                Buyer buyer = mBuyerDao.queryBuilder().where(BuyerDao.Properties.Username.eq(usernameTemp)).unique();
                Long buyerId = buyer.getId();

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.USER_MODE, Constants.BUYER_MODE);
                bundle.putLong(Constants.BUYER_ID, buyerId);

                Intent intent = new Intent(LoginActivity.this, MainBuyerActivity.class);
                intent.putExtra(Constants.BUYER_DATA, bundle);
                startActivity(intent);
                finish();
            }
            else{
                //提醒用户用户名或者密码输入不正确
                showAlertDialog();
            }
        }
        //卖家模式
        else if(modeNumber == Constants.SELLER_MODE){
            //认证成功
            if(AuthenticateUtils.authenticateSeller(usernameTemp, passwordTemp)) {
                Seller seller = mSellerDao.queryBuilder().where(SellerDao.Properties.Username.eq(usernameTemp)).unique();
                Long sellerId = seller.getId();

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.USER_MODE, Constants.SELLER_MODE);
                bundle.putLong(Constants.SELLER_ID, sellerId);

                Intent intent = new Intent(LoginActivity.this, MainSellerActivity.class);
                intent.putExtra(Constants.SELLER_DATA, bundle);
                startActivity(intent);
                finish();
            }
            //认证失败
            else{
                showAlertDialog();
            }
        }
        //管理员模式
        else if(modeNumber == Constants.ROOT_MODE){
            if(AuthenticateUtils.authenticateRoot(usernameTemp, passwordTemp)) {
                Intent intent = new Intent(LoginActivity.this, MainRootActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                //提醒用户用户名或者密码输入不正确
                showAlertDialog();
            }
        }
    }

    /**
     * 提醒用户用户名或者密码输入错误
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setIcon(R.drawable.app_icon);
        builder.setMessage(R.string.warning_authenticate_failed);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUsernameEt.setText(null);
                mPasswordEt.setText(null);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create();
        builder.show();
    }
}
