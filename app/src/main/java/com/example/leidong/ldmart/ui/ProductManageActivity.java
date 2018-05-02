package com.example.leidong.ldmart.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
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
import com.example.leidong.ldmart.secure.SecureUtils;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.utils.FontUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品管理界面
 *
 * @author Lei Dong
 */
public class ProductManageActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ProductManageActivity";

    @BindView(R.id.product_manage_image)
    ImageView mProductManageImage;

    @BindView(R.id.product_manage_name)
    EditText mProductManageName;

    @BindView(R.id.product_manage_price)
    EditText mProductManagePrice;

    @BindView(R.id.product_manage_stock)
    EditText mProductManageStock;

    @BindView(R.id.product_manage_desc)
    EditText mProductManageDesc;

    @BindView(R.id.btn_cancel)
    Button mBtnCanael;

    @BindView(R.id.btn_ok)
    Button mBtnOk;

    /*特定商品的信息*/
    private Long mProductId;
    private String mProductImageUrl;
    private String mProductName;
    private int mProductPrice;
    private int mProductStock;
    private String mProductDesc;

    private MySharedPreferences mMySharedPreferences;

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
    @SuppressLint("SetTextI18n")
    private void initWidgets() {
        //获取本界面需要的全部信息
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.PRODUCT_MANAGE);
        mProductId = bundle.getLong(Constants.PRODUCT_ID);
        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        Product product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(mProductId)).unique();
        mProductImageUrl = product.getProductImageUrl();
        mProductName = product.getProductName();
        mProductPrice = product.getProductPrice();
        mProductStock = product.getProductStock();
        mProductDesc = product.getDesc();

        //填充本界面需要的全部信息
        Picasso.get().load(mProductImageUrl).into(mProductManageImage);
        mProductManageName.setText(mProductName);
        mProductManagePrice.setText(mProductPrice + "");
        mProductManageStock.setText(mProductStock + "");
        mProductManageDesc.setText(mProductDesc);
        FontUtils.setFontFromAssets(mProductManageDesc, "fonts/doudouti.ttf");

        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(this);
    }

    /**
     * 按钮点击事件的监听
     *
     * @param view 点击的View
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
        final EditText editText = new EditText(this);
        editText.setHint(R.string.input_password);
        editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setGravity(Gravity.CENTER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setIcon(R.drawable.app_icon);
        builder.setView(editText);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String passwordTemp = editText.getText().toString().trim();
                Long sellerId = mMySharedPreferences.load(Constants.SELLER_ID, 0L);
                if(SecureUtils.isSellererPasswordRight(passwordTemp, sellerId)){
                    updateProductInDb();
                }
                else{
                    Toast.makeText(ProductManageActivity.this, R.string.warning_password_error, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    /**
     * 更新数据库中的特性商品信息
     */
    private void updateProductInDb() {
        String productName = mProductManageName.getText().toString().trim();
        int productPrice = Integer.parseInt(mProductManagePrice.getText().toString().trim());
        int productStock = Integer.parseInt(mProductManageStock.getText().toString().trim());
        String productDesc = mProductManageDesc.getText().toString().trim();

        final ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        final Product product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(mProductId)).unique();
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
