package com.example.leidong.ldmart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.R;
import com.example.leidong.ldmart.beans.Order;
import com.example.leidong.ldmart.constants.Constants;
import com.example.leidong.ldmart.greendao.OrderDao;
import com.squareup.picasso.Picasso;

/**
 * 卖家订单适配器
 */
public class OrdersSellerAdapter extends RecyclerView.Adapter<OrdersSellerAdapter.ViewHolder> {
    private Context mContext;
    private Order[] mOrders;

    public OrdersSellerAdapter(Context context, Order[] mOrders) {
        this.mContext = context;
        this.mOrders = mOrders;
    }

    @Override
    public OrdersSellerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_seller_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrdersSellerAdapter.ViewHolder holder, final int position) {
        if(mOrders != null) {
            holder.orderId.setText(mOrders[position].getOrderId());
            holder.orderTime.setText(mOrders[position].getOrderTime());
            Picasso.get().load(mOrders[position].getProductImageUrl()).into(holder.productImage);
            holder.productName.setText(mOrders[position].getProductName());
            holder.productPrice.setText(mOrders[position].getProductPrice() + "元");
            holder.productNumber.setText(mOrders[position].getProductNumber() + "件");
            int totalPrice = mOrders[position].getProductPrice() * mOrders[position].getProductNumber();
            holder.productsTotalPrice.setText("总计：" + totalPrice + " 元");

            holder.btnDeliver.setText(mOrders[position].getOrderState() == Constants.ORDER_BOUGHT ? R.string.order_deliver_str_seller : R.string.order_delivered_str_seller);
            if(mOrders[position].getOrderState() == Constants.ORDER_BOUGHT) {
                holder.btnDeliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
                        Order order = mOrders[position];
                        order.setOrderState(Constants.ORDER_DELIVERED);
                        orderDao.update(order);
                    }
                });
            }
            else{
                holder.btnDeliver.setClickable(false);
            }
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
