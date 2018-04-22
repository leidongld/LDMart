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
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.secure.SecureUtils;
import com.example.leidong.ldmart.storage.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买家个人信息Fragment
 */
public class MyBuyerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MyBuyerFragment";

    @BindView(R.id.buyer_name)
    EditText mBuyerNameEt;

    @BindView(R.id.password1_layout)
    LinearLayout mPassword1Layout;

    @BindView(R.id.buyer_password1)
    EditText mBuyerPassword1Et;

    @BindView(R.id.password2_layout)
    LinearLayout mPassword2Layout;

    @BindView(R.id.buyer_password2)
    EditText mBuyerPassword2Et;

    @BindView(R.id.buyer_phone)
    EditText mBuyerPhoneEt;

    @BindView(R.id.buyer_address)
    EditText mBuyerAddressEt;

    @BindView(R.id.btn_change)
    Button mBtnChange;

    @BindView(R.id.btn_save)
    Button mBtnSave;

    private int mUserMode;
    private Long mBuyerId;

    private MySharedPreferences mMySharedPreferences;

    private BuyerDao mBuyerDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_buyer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        initWidgets();

        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mBtnChange.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
//        Bundle bundle = getArguments();
//        mUserMode = bundle.getInt(Constants.USER_MODE);
//        mBuyerId = bundle.getLong(Constants.BUYER_ID);
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mBuyerId = mMySharedPreferences.load(Constants.BUYER_ID, 0L);

        mBuyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();

        mBuyerNameEt.setFocusableInTouchMode(false);
        mBuyerNameEt.setEnabled(false);
        mBuyerAddressEt.setFocusableInTouchMode(false);
        mBuyerPhoneEt.setEnabled(false);
        mBuyerPhoneEt.setFocusableInTouchMode(false);
        mBuyerAddressEt.setEnabled(false);

        Buyer buyer = mBuyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mBuyerId)).unique();
        if(buyer != null){
            String buyerName = buyer.getUsername();
            String buyerPassword = buyer.getPassword();
            String buyerAddress = buyer.getAddress();
            String buyerPhone = buyer.getPhone();

            mBuyerNameEt.setText(buyerName);
            mBuyerPhoneEt.setText(buyerPhone);
            mBuyerAddressEt.setText(buyerAddress);
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
        String buyerName = mBuyerNameEt.getText().toString().trim();
        String buyerPassword1 = mBuyerPassword1Et.getText().toString().trim();
        String buyerPassword2 = mBuyerPassword2Et.getText().toString().trim();
        String buyerPhone = mBuyerPhoneEt.getText().toString().trim();
        String buyerAddress = mBuyerAddressEt.getText().toString().trim();

        if(buyerName.length() > 0
                && buyerPassword1.length() > 0
                && buyerPassword2.length() > 0
                && buyerPhone.length() > 0
                && buyerAddress.length() > 0
                && SecureUtils.isPasswordLegal(buyerPassword1, buyerPassword2)){
            //修改买家信息
            Buyer buyer = mBuyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mBuyerId)).unique();
            buyer.setUsername(buyerName);
            buyer.setPassword(buyerPassword1);
            buyer.setPhone(buyerPhone);
            buyer.setAddress(buyerAddress);
            mBuyerDao.update(buyer);

            Toast.makeText(MyApplication.getsContext(), "您的信息更改完毕！", Toast.LENGTH_LONG).cancel();

            mPassword1Layout.setVisibility(View.GONE);
            mPassword2Layout.setVisibility(View.GONE);

            mBuyerNameEt.setFocusableInTouchMode(false);
            mBuyerNameEt.setEnabled(false);
            mBuyerPhoneEt.setFocusableInTouchMode(false);
            mBuyerPhoneEt.setEnabled(false);
            mBuyerAddressEt.setFocusableInTouchMode(false);
            mBuyerAddressEt.setEnabled(false);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.warning);
            builder.setMessage(R.string.warning_format_error);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mBuyerNameEt.setText(null);
                    mBuyerPassword1Et.setText(null);
                    mBuyerPassword2Et.setText(null);
                    mBuyerPhoneEt.setText(null);
                    mBuyerAddressEt.setText(null);
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
        Toast.makeText(MyApplication.getsContext(), "请更改您的信息", Toast.LENGTH_LONG).cancel();

        mBuyerNameEt.setFocusableInTouchMode(true);
        mBuyerNameEt.setEnabled(true);
        mBuyerAddressEt.setFocusableInTouchMode(true);
        mBuyerAddressEt.setEnabled(true);
        mBuyerPhoneEt.setFocusableInTouchMode(true);
        mBuyerPhoneEt.setEnabled(true);

        mPassword1Layout.setVisibility(View.VISIBLE);
        mPassword2Layout.setVisibility(View.VISIBLE);

        mBuyerNameEt.setText(null);
        mBuyerPhoneEt.setText(null);
        mBuyerAddressEt.setText(null);
    }
}
