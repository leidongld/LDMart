package com.example.leidong.ldmart.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Order;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.OrderDao;
import com.example.leidong.ldmart.greendao.ProductDao;
import com.example.leidong.ldmart.ui.OrderDetailActivity;
import com.squareup.picasso.Picasso;

/**
 * 卖家订单适配器
 *
 * @author Lei Dong
 */
public class OrdersSellerAdapter extends RecyclerView.Adapter<OrdersSellerAdapter.ViewHolder> {
    //Context
    private Context mContext;

    //订单数组
    private Order[] mOrders;

    /**
     * 构造器
     * @param context Context
     * @param mOrders 订单数组
     */
    public OrdersSellerAdapter(Context context, Order[] mOrders) {
        this.mContext = context;
        this.mOrders = mOrders;
    }

    @Override
    public OrdersSellerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_seller_item, parent, false));
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(final OrdersSellerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(mOrders != null) {
            Long productId = mOrders[position].getProductId();
            ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
            Product product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(productId)).unique();
            String productImageUrl = product.getProductImageUrl();
            String productName = product.getProductName();
            int productPrice = product.getProductPrice();

            holder.orderId.setText(mOrders[position].getOrderId());
            holder.orderTime.setText(mOrders[position].getOrderTime());
            Picasso.get().load(productImageUrl).into(holder.productImage);
            holder.productName.setText(productName);
            holder.productPrice.setText(productPrice + "元");
            holder.productNumber.setText(mOrders[position].getProductNumber() + "件");
            int totalPrice = productPrice * mOrders[position].getProductNumber();
            holder.productsTotalPrice.setText("总计：" + totalPrice + " 元");

            holder.btnDeliver.setText(mOrders[position].getOrderState() == Constants.ORDER_BOUGHT ? R.string.order_deliver_str_seller : R.string.order_delivered_str_seller);

            if(mOrders[position].getOrderState() == Constants.ORDER_BOUGHT) {
                holder.btnDeliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //更新订单状态为已发货
                        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
                        Order order = mOrders[position];
                        order.setOrderState(Constants.ORDER_DELIVERED);
                        orderDao.update(order);
                        holder.btnDeliver.setText(R.string.order_delivered_str_seller);
                    }
                });
            }
            else{
                holder.btnDeliver.setClickable(false);
            }

            holder.orderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    //传递订单编号与买家Id
                    bundle.putLong(Constants.ORDER_DB_ID, mOrders[position].getId());
                    bundle.putLong(Constants.BUYER_ID, mOrders[position].getBuyerId());
                    intent.putExtra(Constants.ORDER_DATA, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mOrders != null) {
            return mOrders.length;
        }
        return 0;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout orderLayout;
        TextView orderId;
        TextView orderTime;
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productNumber;
        TextView productsTotalPrice;
        Button btnDeliver;

        ViewHolder(View itemView) {
            super(itemView);

            orderLayout = itemView.findViewById(R.id.order_layout);
            orderId = itemView.findViewById(R.id.order_id);
            orderTime = itemView.findViewById(R.id.order_time);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productNumber = itemView.findViewById(R.id.product_number);
            productsTotalPrice = itemView.findViewById(R.id.products_total_price);
            btnDeliver = itemView.findViewById(R.id.btn_deliver);
        }
    }
}
