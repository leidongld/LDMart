package com.example.leidong.ldmart.models;

import java.util.List;

public class Orders {

    private List<OrdersBean> Orders;

    public List<OrdersBean> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrdersBean> Orders) {
        this.Orders = Orders;
    }

    public static class OrdersBean {
        /**
         * productId : 0
         * orderId : 201804040000
         * orderTime : 20180404
         * productName : 精品脐橙
         * productImage : https://img12.360buyimg.com/babel/s360x360_jfs/t3295/14/5775084582/492685/47d076e7/587e0252Nf62a419b.jpg!q80.webp
         * productPrice : 10
         * productNumber : 100
         * productRemain : 99
         */

        private int productId;
        private String orderId;
        private String orderTime;
        private String productName;
        private String productImage;
        private int productPrice;
        private int productNumber;
        private int productRemain;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(int productNumber) {
            this.productNumber = productNumber;
        }

        public int getProductRemain() {
            return productRemain;
        }

        public void setProductRemain(int productRemain) {
            this.productRemain = productRemain;
        }
    }
}
