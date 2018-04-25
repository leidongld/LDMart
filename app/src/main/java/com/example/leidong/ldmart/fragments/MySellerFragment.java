package com.example.leidong.ldmart.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.SellerDao;
import com.example.leidong.ldmart.secure.SecureUtils;
import com.example.leidong.ldmart.storage.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家个人信息Fragment
 * @author Lei Dong
 */
public class MySellerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MySellerFragment";

    //卖家用户名输入框
    @BindView(R.id.seller_name)
    EditText mSellerNameEt;

    //卖家密码输入框1的layout
    @BindView(R.id.password1_layout)
    LinearLayout mPassword1Layout;

    //卖家密码输入框1
    @BindView(R.id.seller_password1)
    EditText mSellerPassword1Et;

    //卖家密码输入框2的layout
    @BindView(R.id.password2_layout)
    LinearLayout mPassword2Layout;

    //卖家密码输入框2
    @BindView(R.id.seller_password2)
    EditText mSellerPassword2Et;

    //卖家电话输入框
    @BindView(R.id.seller_phone)
    EditText mSellerPhoneEt;

    //卖家地址输入框
    @BindView(R.id.seller_address)
    EditText mSellerAddressEt;

    //更改信息按钮
    @BindView(R.id.btn_change)
    Button mBtnChange;

    //保存信息按钮
    @BindView(R.id.btn_save)
    Button mBtnSave;

    //用户身份码
    private int mUserMode;

    //卖家Id
    private Long mSellerId;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    private SellerDao mSellerDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_seller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mBtnSave.setOnClickListener(this);
        mBtnChange.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mSellerId = mMySharedPreferences.load(Constants.SELLER_ID, 0L);

        mSellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();

        //禁止输入
        mSellerNameEt.setFocusableInTouchMode(false);
        mSellerNameEt.setEnabled(false);
        mSellerPhoneEt.setFocusableInTouchMode(false);
        mSellerPhoneEt.setEnabled(false);
        mSellerAddressEt.setFocusableInTouchMode(false);
        mSellerAddressEt.setEnabled(false);

        //填充已有信息
        Seller seller = mSellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(mSellerId)).unique();
        if(seller != null){
            String sellerName = seller.getUsername();
            String sellerPassword = seller.getPassword();
            String sellerPhone = seller.getPhone();
            String sellerAddress = seller.getAddress();

            mSellerNameEt.setText(sellerName);
            mSellerPhoneEt.setText(sellerPhone);
            mSellerAddressEt.setText(sellerAddress);
        }
    }

    /**
     * 按钮点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change:
                clickChangeBtn();
                break;
            case R.id.btn_save:
                clickSaveBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击保存按钮
     */
    @SuppressLint("ShowToast")
    private void clickSaveBtn() {
        String sellerName = mSellerNameEt.getText().toString().trim();
        String sellerPassword1 = mSellerPassword1Et.getText().toString().trim();
        String sellerPassword2 = mSellerPassword2Et.getText().toString().trim();
        String sellerPhone = mSellerPhoneEt.getText().toString().trim();
        String sellerAddress = mSellerAddressEt.getText().toString().trim();

        //输入符合要求
        if(sellerName.length() > 0
                && sellerPassword1.length() > 0
                && sellerPassword2.length() > 0
                && sellerPhone.length() > 0
                && sellerAddress.length() > 0
                && SecureUtils.isPasswordLegal(sellerPassword1, sellerPassword2)){
            //修改卖家信息
            Seller seller = mSellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(mSellerId)).unique();
            seller.setUsername(sellerName);
            seller.setPassword(sellerPassword1);
            seller.setPhone(sellerPhone);
            seller.setAddress(sellerAddress);
            mSellerDao.update(seller);

            Toast.makeText(MyApplication.getsContext(), R.string.user_info_update_finish, Toast.LENGTH_LONG).cancel();

            mPassword1Layout.setVisibility(View.GONE);
            mPassword2Layout.setVisibility(View.GONE);
            mSellerNameEt.setFocusableInTouchMode(false);
            mSellerNameEt.setEnabled(false);
            mSellerPhoneEt.setFocusableInTouchMode(false);
            mSellerPhoneEt.setEnabled(false);
            mSellerAddressEt.setFocusableInTouchMode(false);
            mSellerAddressEt.setEnabled(false);
        }
        //输入不符合要求
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.warning);
            builder.setIcon(R.drawable.app_icon);
            builder.setMessage(R.string.warning_format_error);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mSellerNameEt.setText(null);
                    mSellerPassword1Et.setText(null);
                    mSellerPassword2Et.setText(null);
                    mSellerPhoneEt.setText(null);
                    mSellerAddressEt.setText(null);
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.create().show();
        }
    }

    /**
     * 点击更改信息按钮
     */
    @SuppressLint("ShowToast")
    private void clickChangeBtn() {
        Toast.makeText(MyApplication.getsContext(), R.string.chaneg_info, Toast.LENGTH_LONG).cancel();

        //允许输入
        mSellerNameEt.setFocusableInTouchMode(true);
        mSellerNameEt.setEnabled(true);
        mSellerAddressEt.setFocusableInTouchMode(true);
        mSellerPhoneEt.setEnabled(true);
        mSellerPhoneEt.setFocusableInTouchMode(true);
        mSellerAddressEt.setEnabled(true);

        //显示密码输入框
        mPassword1Layout.setVisibility(View.VISIBLE);
        mPassword2Layout.setVisibility(View.VISIBLE);

        //清除输入框中的内容
        mSellerNameEt.setText(null);
        mSellerPhoneEt.setText(null);
        mSellerAddressEt.setText(null);
    }
}
