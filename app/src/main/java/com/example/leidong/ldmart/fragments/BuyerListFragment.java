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
import com.example.leidong.ldmart.adapters.BuyerListAdapter;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.greendao.BuyerDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买家列表Fragment
 * @author Lei Dong
 */
public class BuyerListFragment extends Fragment{
    private static final String TAG = "BuyerListFragment";

    //买家列表
    @BindView(R.id.buyers_container)
    RecyclerView mBuyersContainer;

    //买家数组
    private Buyer[] mBuyers;

    private BuyerDao mBuyerDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_buyer_list, container, false);
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

    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mBuyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        List<Buyer> buyerList = mBuyerDao.loadAll();
        mBuyers = new Buyer[buyerList.size()];
        for(int i = 0; i < buyerList.size(); i++){
            mBuyers[i] = buyerList.get(i);
        }

        mBuyersContainer.setLayoutManager(new LinearLayoutManager(MyApplication.getsContext()));
        mBuyersContainer.setAdapter(new BuyerListAdapter(MyApplication.getsContext(), mBuyers));
    }

    @Override
    public void onStart(){
        super.onStart();
        initWidgets();
    }
}
