package com.example.leidong.ldmart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.adapters.OrdersSellerAdapter;
import com.example.leidong.ldmart.beans.Order;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.storage.MySharedPreferences;
import com.example.leidong.ldmart.utils.LoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家订单Fragment
 *
 * @author Lei Dong
 */
public class OrdersSellerFragment extends Fragment {
    private static final String TAG = "OrdersSellerFragment";

    //搜索框
    @BindView(R.id.search_view)
    SearchView mSearchView;

    //订单列表
    @BindView(R.id.orders_container)
    RecyclerView mOrdersContainer;

    //全部订单
    private Order[] mOrders;

    //用户身份码
    private int mUserMode;

    //卖家Id
    private Long mSellerId;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_orders_seller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //初始化组件
        initWidgest();

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
                    mOrders = LoadUtils.loadSellerOrders();
                }
                else{
                    mOrders = LoadUtils.loadSellerOrdersByDate(s);
                }
                mOrdersContainer.setAdapter(new OrdersSellerAdapter(MyApplication.getsContext(), mOrders));
                return false;
            }
        });
    }

    /**
     * 初始化组件
     */
    private void initWidgest() {
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mSellerId = mMySharedPreferences.load(Constants.SELLER_ID, 0L);

        mOrders = LoadUtils.loadSellerOrders();
        mOrdersContainer.setLayoutManager(new LinearLayoutManager(MyApplication.getsContext()));
        mOrdersContainer.setAdapter(new OrdersSellerAdapter(MyApplication.getsContext(), mOrders));
    }

    @Override
    public void onStart(){
        super.onStart();
        initActions();
    }
}
