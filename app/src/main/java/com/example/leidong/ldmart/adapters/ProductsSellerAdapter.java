package com.example.leidong.ldmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.ui.ProductManageActivity;
import com.squareup.picasso.Picasso;

/**
 * 卖家产品列表适配器
 */
public class ProductsSellerAdapter extends RecyclerView.Adapter<ProductsSellerAdapter.ViewHolder> {
    private Context mContext;
    private Product[] mProducts;

    public ProductsSellerAdapter(Context context, Product[] products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @Override
    public ProductsSellerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.product_seller_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mProducts != null) {
            Picasso.get().load(mProducts[position].getProductImageUrl()).into(holder.productImage);
            holder.productName.setText(mProducts[position].getProductName());
            holder.productPrice.setText(mProducts[position].getProductPrice() + "  元");

            holder.productItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, position + "", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, ProductManageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.PRODUCT_ID, mProducts[position].getId());
                    bundle.putString(Constants.PRODUCT_MANAGE_NAME, mProducts[position].getProductName());
                    bundle.putString(Constants.PRODUCT_MANAGE_IMAGE_URL, mProducts[position].getProductImageUrl());
                    bundle.putInt(Constants.PRODUCT_MANAGE_PRICE, mProducts[position].getProductPrice());
                    bundle.putString(Constants.PRODUCT_MANAGE_DESC, mProducts[position].getDesc());
                    intent.putExtra(Constants.PRODUCT_MANAGE, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mProducts != null) {
            return mProducts.length;
        }
        return 0;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout productItemLayout;
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        ViewHolder(View itemView) {
            super(itemView);

            productItemLayout = itemView.findViewById(R.id.product_buyer_item_layout);
            productImage = itemView.findViewById(R.id.product_buyer_item_image);
            productName = itemView.findViewById(R.id.product_buyer_item_name);
            productPrice = itemView.findViewById(R.id.product_buyer_item_price);
        }
    }
}
