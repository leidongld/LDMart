package com.example.leidong.ldmart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 */
public class OrdersSellerFragment extends Fragment {
    private static final String TAG = "OrdersSellerFragment";

    @BindView(R.id.orders_container)
    RecyclerView mOrdersContainer;

    private Order[] mOrders;

    private int mUserMode;
    private Long mSellerId;

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

        initWidgest();

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
    private void initWidgest() {
//        Bundle bundle = getArguments();
//        mUserMode = bundle.getInt(Constants.USER_MODE);
//        mSellerId = bundle.getLong(Constants.SELLER_ID);
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mSellerId = mMySharedPreferences.load(Constants.SELLER_ID, 0L);

        mOrders = LoadUtils.loadOrdersSellerData();
        mOrdersContainer.setLayoutManager(new LinearLayoutManager(MyApplication.getsContext()));
        mOrdersContainer.setAdapter(new OrdersSellerAdapter(MyApplication.getsContext(), mOrders));
    }

    @Override
    public void onStart(){
        super.onStart();
        initActions();
    }
}
