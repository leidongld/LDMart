package com.example.leidong.ldmart.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.ProductDao;
import com.example.leidong.ldmart.greendao.SellerDao;
import com.example.leidong.ldmart.models.Products;
import com.example.leidong.ldmart.secure.SecureUtils;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.ui.LoginActivity;
import com.example.leidong.ldmart.utils.AuthenticateUtils;
import com.example.leidong.ldmart.utils.LoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加用户的Fragment
 * @author Lei Dong
 */
public class AddUserFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private static final String TAG = "AddUserFragment";

    //用户名输入框
    @BindView(R.id.username)
    EditText mUsernameEt;

    //密码输入框1
    @BindView(R.id.password1)
    EditText mPassword1Et;

    //密码输入框2
    @BindView(R.id.password2)
    EditText mPassword2Et;

    //RadioGroup
    @BindView(R.id.rbtn_group)
    RadioGroup mRbtnGroup;

    //RadioButton 买家
    @BindView(R.id.rbtn_buyer)
    RadioButton mRbtnBuyer;

    //RadioButton 卖家
    @BindView(R.id.rbtn_seller)
    RadioButton mRbtnSeller;

    //添加用户按钮
    @BindView(R.id.btn_add_user)
    Button mBtnAddUser;

    //用户身份码
    private int modeNumber;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    private SellerDao mSellerDao;
    private BuyerDao mBuyerDao;
    private ProductDao mProductDao;

    //存放Json中的Product信息的数组
    private Products.ProductsBean[] mProductsBeans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
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
     * 初始化控件
     */
    private void initActions() {
        mRbtnGroup.setOnCheckedChangeListener(this);
        mBtnAddUser.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mBtnAddUser.setVisibility(View.GONE);

        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
    }

    /**
     *
     * @param radioGroup RadioGroup
     * @param i 编号
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        i = radioGroup.getCheckedRadioButtonId();

        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();

        //买家
        if(i == R.id.rbtn_buyer){
            modeNumber = 1;
            if(username.length() > 0
                    && password1.length() > 0
                    && password2.length() > 0
                    && SecureUtils.isPasswordLegal(password1, password2)){
                mBtnAddUser.setText(R.string.add_buyer);
                mBtnAddUser.setVisibility(View.VISIBLE);
            }
        }
        //卖家
        else if(i == R.id.rbtn_seller){
            modeNumber = 2;
            mBtnAddUser.setVisibility(View.GONE);
            if(username.length() > 0
                    && password1.length() > 0
                    && password2.length() > 0
                    && SecureUtils.isPasswordLegal(password1, password2)
                    && !mMySharedPreferences.load(Constants.IS_SELLER_EXIST, false)){
                mBtnAddUser.setText(R.string.add_seller);
                mBtnAddUser.setVisibility(View.VISIBLE);
            }
        }
        else{

        }
    }

    /**
     * 按钮的点击事件
     * @param view 点击的View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_user:
                clickAddUserBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击添加用户按钮
     */
    private void clickAddUserBtn() {
        String username = mUsernameEt.getText().toString().trim();
        String password = mPassword1Et.getText().toString().trim();

        //添加买家
        if(modeNumber == 1 && AuthenticateUtils.buyerNotExsit(username)){
            mBuyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
            Buyer buyer = new Buyer();
            buyer.setUsername(username);
            buyer.setPassword(password);
            buyer.setAddress(null);
            buyer.setPhone(null);
            mBuyerDao.insert(buyer);

            clearData();
            Toast.makeText(MyApplication.getsContext(), "买家添加完毕！", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MyApplication.getsContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        //添加卖家以及商品
        else if(modeNumber == 2){
            //添加卖家
            mSellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
            Seller seller = new Seller();
            seller.setUsername(username);
            seller.setPassword(password);
            seller.setAddress(null);
            seller.setPhone(null);
            mSellerDao.insert(seller);

            mMySharedPreferences.save(Constants.IS_SELLER_EXIST, true);
            mBtnAddUser.setVisibility(View.GONE);

            //添加商品
            mProductsBeans = LoadUtils.loadProductsSellerDataFromJson();
            mProductDao = MyApplication.getInstance().getDaoSession().getProductDao();
            for (Products.ProductsBean mProductsBean : mProductsBeans) {
                Product product = new Product();
                product.setProductName(mProductsBean.getProductName());
                product.setProductImageUrl(mProductsBean.getProductImage());
                product.setProductPrice(mProductsBean.getProductPrice());
                product.setProductStock(mProductsBean.getProductNumber());
                product.setDesc(mProductsBean.getProductDesc());
                mProductDao.insert(product);
            }

            clearData();
            Toast.makeText(MyApplication.getsContext(), R.string.add_buyer_and_products_finish, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MyApplication.getsContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    /**
     * 清空输入框中的数据
     */
    private void clearData() {
        mUsernameEt.setText(null);
        mPassword1Et.setText(null);
        mPassword2Et.setText(null);
    }

    @Override
    public void onStart(){
        super.onStart();
        initWidgets();
    }
}
