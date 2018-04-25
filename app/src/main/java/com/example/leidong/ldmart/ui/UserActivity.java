package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Order;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.OrderDao;
import com.example.leidong.ldmart.greendao.ProductDao;
import com.example.leidong.ldmart.greendao.SellerDao;
import com.example.leidong.ldmart.storage.MySharedPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户信息界面
 * @author Lei Dong
 */
public class UserActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "UserActivity";

    //用户名TextView
    @BindView(R.id.tv_username)
    TextView mUsernameTv;

    //密码TextView
    @BindView(R.id.tv_password)
    TextView mPasswordTv;

    //电话TextView
    @BindView(R.id.tv_phone)
    TextView mPhoneTv;

    //地址TextView
    @BindView(R.id.tv_address)
    TextView mAddressTv;

    //删除用户按钮
    @BindView(R.id.btn_delete_user)
    Button mDeleteUserBtn;

    private MySharedPreferences mMySharedPreferences;

    //用户Id
    private Long mUserId;
    //用户身份码
    private int mUserMode;

    //用户名
    private String mUsername;
    //密码
    private String mPassword;
    //电话
    private String mPhone;
    //地址
    private String mAddress;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.USER_DATA);

        mUserId = bundle.getLong(Constants.USER_ID);
        mUserMode = bundle.getInt(Constants.USER_MODE);

        //买家模式
        if(mUserMode == Constants.BUYER_MODE){
            BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
            Buyer buyer = buyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mUserId)).unique();
            mUsername = buyer.getUsername();
            mPassword = buyer.getPassword();
            mPhone = buyer.getPhone();
            mAddress = buyer.getAddress();
        }
        //卖家模式
        else if(mUserMode == Constants.SELLER_MODE){
            SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
            Seller seller = sellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(mUserId)).unique();
            mUsername = seller.getUsername();
            mPassword = seller.getPassword();
            mPhone = seller.getPhone();
            mAddress = seller.getAddress();
        }
        else{

        }

        //填充信息
        mUsernameTv.setText(mUsername);
        mPasswordTv.setText(mPassword);
        mPhoneTv.setText(mPhone);
        mAddressTv.setText(mAddress);
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mDeleteUserBtn.setOnClickListener(this);
    }

    /**
     * 点击事件监听
     * @param view 点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_delete_user:
                clickDeleteUserBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击删除用户按钮
     */
    private void clickDeleteUserBtn() {
        //删除买家及对应订单
        if(mUserMode == Constants.BUYER_MODE){
            OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
            List<Order> orderList = orderDao.queryBuilder().where(OrderDao.Properties.BuyerId.eq(mUserId)).list();
            orderDao.deleteInTx(orderList);

            BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
            Buyer buyer = buyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mUserId)).unique();
            buyerDao.delete(buyer);

            finish();
        }
        //删除卖家及对应订单
        else{
            OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
            List<Order> orderList = orderDao.loadAll();
            orderDao.deleteInTx(orderList);

            SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
            Seller seller = sellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(mUserId)).unique();
            sellerDao.delete(seller);

            ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
            List<Product> productList = productDao.loadAll();
            productDao.deleteInTx(productList);

            mMySharedPreferences = MySharedPreferences.getMySharedPreferences(this);
            mMySharedPreferences.save(Constants.IS_SELLER_EXIST, false);

            finish();
        }
    }
}
