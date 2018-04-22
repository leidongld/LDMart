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
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.OrderDao;
import com.example.leidong.ldmart.greendao.SellerDao;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "UserActivity";

    @BindView(R.id.tv_username)
    TextView mUsernameTv;

    @BindView(R.id.tv_password)
    TextView mPasswordTv;

    @BindView(R.id.tv_phone)
    TextView mPhoneTv;

    @BindView(R.id.tv_address)
    TextView mAddressTv;

    @BindView(R.id.btn_delete_user)
    Button mDeleteUserBtn;

    private Long mUserId;
    private int mUserMode;

    private String mUsername;
    private String mPassword;
    private String mPhone;
    private String mAddress;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        initWidgets();

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

        if(mUserMode == Constants.BUYER_MODE){
            BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
            Buyer buyer = buyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mUserId)).unique();
            mUsername = buyer.getUsername();
            mPassword = buyer.getPassword();
            mPhone = buyer.getPhone();
            mAddress = buyer.getAddress();
        }
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
     * @param view
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
        //是买家
        if(mUserMode == Constants.BUYER_MODE){
            OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
            List<Order> orderList = orderDao.queryBuilder().where(OrderDao.Properties.BuyerId.eq(mUserId)).list();
            orderDao.deleteInTx(orderList);

            BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
            Buyer buyer = buyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mUserId)).unique();
            buyerDao.delete(buyer);

            finish();
        }
        //是卖家
        else{
            OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
            List<Order> orderList = orderDao.loadAll();
            orderDao.deleteInTx(orderList);

            SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
            Seller seller = sellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(mUserId)).unique();
            sellerDao.delete(seller);

            finish();
        }
    }
}
