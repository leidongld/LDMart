package com.example.leidong.ldmart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.adapters.ProductsSellerAdapter;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.utils.LoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家商品Fragment
 * @author Lei Dong
 */
public class ProductsSellerFragment extends Fragment {
    private static final String TAG = "ProductsSellerFragment";

    //搜索框
    @BindView(R.id.search_view)
    SearchView mSearchView;

    //商品列表
    @BindView(R.id.products_list)
    RecyclerView mProductsRecyclerView;

    //全部商品
    private Product[] mProducts;

    //用户身份码
    private int mUserMode;

    //卖家Id
    private Long mSellerId;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_products_seller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    mProducts = LoadUtils.loadSellerProducts();
                }
                else{
                    mProducts = LoadUtils.loadSellerProductsByName(s);
                }
                mProductsRecyclerView.setAdapter(new ProductsSellerAdapter(MyApplication.getsContext(), mProducts));
                return false;
            }
        });
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mSellerId = mMySharedPreferences.load(Constants.SELLER_ID, 0L);

        mProducts = LoadUtils.loadSellerProducts();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyApplication.getsContext(), 2);
        mProductsRecyclerView.setLayoutManager(gridLayoutManager);
        mProductsRecyclerView.setAdapter(new ProductsSellerAdapter(MyApplication.getsContext(), mProducts));
    }

    @Override
    public void onStart(){
        super.onStart();
        initWidgets();
    }
}
