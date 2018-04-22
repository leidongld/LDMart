package com.example.leidong.ldmart.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.ProductDao;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品管理界面
 */
public class ProductManageActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ProductManageActivity";

    @BindView(R.id.product_manage_image)
    ImageView mProductManageImage;

    @BindView(R.id.product_manage_name)
    EditText mProductManageName;

    @BindView(R.id.product_manage_price)
    EditText mProductManagePrice;

    @BindView(R.id.product_manage_desc)
    EditText mProductManageDesc;

    @BindView(R.id.btn_cancel)
    Button mBtnCanael;

    @BindView(R.id.btn_ok)
    Button mBtnOk;

    /*特定商品的信息*/
    private int mProductId;
    private String mProductImageUrl;
    private String mProductName;
    private int mProductPrice;
    private String mProductDesc;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);

        ButterKnife.bind(this);

        initWidgets();

        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mBtnCanael.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        //获取本界面需要的全部信息
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.PRODUCT_MANAGE);
        mProductId = bundle.getInt(Constants.PRODUCT_ID);
        mProductImageUrl = bundle.getString(Constants.PRODUCT_MANAGE_IMAGE_URL);
        mProductName = bundle.getString(Constants.PRODUCT_MANAGE_NAME);
        mProductPrice = bundle.getInt(Constants.PRODUCT_MANAGE_PRICE);
        mProductDesc = bundle.getString(Constants.PRODUCT_MANAGE_DESC);

        //填充本界面需要的全部信息
        Picasso.get().load(mProductImageUrl).into(mProductManageImage);
        mProductManageName.setText(mProductName);
        mProductManagePrice.setText(mProductPrice + "");
        mProductManageDesc.setText(mProductDesc);
    }

    /**
     * 按钮点击事件的监听
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_cancel:
                clickCancelBtn();
                break;
            case R.id.btn_ok:
                clickOkBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击确认按钮
     */
    private void clickOkBtn() {
        Toast.makeText(ProductManageActivity.this, "商品信息已更新", Toast.LENGTH_LONG).show();
        String productName = mProductManageName.getText().toString().trim();
        int productPrice = Integer.parseInt(mProductManagePrice.getText().toString().trim());
        String productDesc = mProductManageDesc.getText().toString().trim();

        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        Product product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(mProductId+1)).unique();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setDesc(productDesc);
        productDao.update(product);
    }

    /**
     * 点击取消按钮
     */
    private void clickCancelBtn() {
        finish();
    }
}
