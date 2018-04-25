package com.example.leidong.ldmart.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends Activity{
    private static final String TAG = "OrderDetailActivity";

    @BindView(R.id.order_id)
    TextView mOrderIdTv;

    @BindView(R.id.order_time)
    TextView mOrderTimeTv;

    @BindView(R.id.product_image)
    ImageView mProductImageIv;

    @BindView(R.id.product_name)
    TextView mProductNameTv;

    @BindView(R.id.product_price)
    TextView mProductPriceTv;

    @BindView(R.id.product_number)
    TextView mProductNumberTv;

    @BindView(R.id.products_total_price)
    TextView mProductsTotalPriceTv;

    @BindView(R.id.order_state)
    TextView mOrderStateTv;

    @BindView(R.id.buyer_name)
    TextView mBuyerNameTv;

    @BindView(R.id.buyer_phone)
    TextView mBuyerPhoneTv;

    @BindView(R.id.buyer_address)
    TextView mBuyerAddressTv;

    @BindView(R.id.seller_name)
    TextView mSellerNameTv;

    @BindView(R.id.seller_phone)
    TextView mSellerPhoneTv;

    @BindView(R.id.seller_address)
    TextView mSellerAddressTv;

    private OrderDao mOrderDao;
    private BuyerDao mBuyerDao;
    private SellerDao mSellerDao;

    private Long mOrderDbId;
    private Long mBuyerId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_detail);
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

    }

    /**
     * 初始化组件
     */
    @SuppressLint("SetTextI18n")
    private void initWidgets() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.ORDER_DATA);
        mOrderDbId = bundle.getLong(Constants.ORDER_DB_ID);
        mBuyerId = bundle.getLong(Constants.BUYER_ID);

        mOrderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        mBuyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        mSellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();

        Order order = mOrderDao.queryBuilder().where(OrderDao.Properties.Id.eq(mOrderDbId)).unique();
        Buyer buyer = mBuyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(mBuyerId)).unique();
        List<Seller> sellers = mSellerDao.loadAll();
        Seller seller = sellers.get(0);

        if(order != null){
            String orderId = order.getOrderId();
            String orderTime = order.getOrderTime();

            Long productId = order.getProductId();
            ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
            Product product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(productId)).unique();

            String productImageUrl = product.getProductImageUrl();
            String productName = product.getProductName();
            int productPrice = product.getProductPrice();
            int productNumber = order.getProductNumber();
            int productTotalPrice = productPrice * productNumber;
            int orderState = order.getOrderState();

            mOrderIdTv.setText(orderId);
            mOrderTimeTv.setText(orderTime);
            Picasso.get().load(productImageUrl).into(mProductImageIv);
            mProductNameTv.setText(productName);
            mProductPriceTv.setText(productPrice + " 元");
            mProductNumberTv.setText(productNumber + " 件");
            mProductsTotalPriceTv.setText("总计：" + productTotalPrice + " 元");
            if(orderState == Constants.ORDER_BOUGHT){
                mOrderStateTv.setText(R.string.buyer_bought);
            }
            else if(orderState == Constants.ORDER_DELIVERED){
                mOrderStateTv.setText(R.string.seller_delivered);
            }
        }

        if(buyer != null){
            String buyerName = buyer.getUsername();
            String buyerPhone = buyer.getPhone();
            String buyerAddress = buyer.getAddress();

            mBuyerNameTv.setText("买家：" + buyerName);
            mBuyerPhoneTv.setText("电话：" + buyerPhone);
            mBuyerAddressTv.setText("地址：" + buyerAddress);
        }

        if(seller != null){
            String sellerName = seller.getUsername();
            String sellerPhone = seller.getPhone();
            String sellerAddress = seller.getAddress();

            mSellerNameTv.setText("卖家：" + sellerName);
            mSellerPhoneTv.setText("电话：" + sellerPhone);
            mSellerAddressTv.setText("地址：" + sellerAddress);
        }
    }
}
