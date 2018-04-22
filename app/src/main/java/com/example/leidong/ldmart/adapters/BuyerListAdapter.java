package com.example.leidong.ldmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.ui.UserActivity;

/**
 * 买家列表适配器
 */
public class BuyerListAdapter extends RecyclerView.Adapter<BuyerListAdapter.ViewHolder> {
    private Context mContext;
    private Buyer[] mBuyers;

    public BuyerListAdapter(Context context, Buyer[] mBuyers) {
        this.mContext = context;
        this.mBuyers = mBuyers;
    }

    @Override
    public BuyerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.buyer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mBuyers.length > 0) {
            holder.buyerId.setText(mBuyers[position].getId() + "");
            holder.buyerName.setText(mBuyers[position].getUsername());

            holder.buyerItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.USER_ID, mBuyers[position].getId());
                    bundle.putInt(Constants.USER_MODE, Constants.BUYER_MODE);
                    intent.putExtra(Constants.USER_DATA, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mBuyers != null) {
            return mBuyers.length;
        }
        return 0;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout buyerItemLayout;
        TextView buyerId;
        TextView buyerName;

        ViewHolder(View itemView) {
            super(itemView);

            buyerItemLayout = itemView.findViewById(R.id.buyer_item_layout);
            buyerId = itemView.findViewById(R.id.buyer_id);
            buyerName = itemView.findViewById(R.id.buyer_name);
        }
    }
}
