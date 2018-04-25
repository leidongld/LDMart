package com.example.leidong.ldmart.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 订单Bean
 * @author Lei Dong
 */

@Entity
public class Order {
    @Id(autoincrement = true)
    private Long id;
    private Long buyerId;
    private long productId;
    private String orderId;
    private String orderTime;
//    private String productName;
//    private String productImageUrl;
//    private int productPrice;
    private int productNumber;
//    private int productRemain;
    private int orderState;

    @Generated(hash = 1497095576)
    public Order(Long id, Long buyerId, long productId, String orderId, String orderTime,
            int productNumber, int orderState) {
        this.id = id;
        this.buyerId = buyerId;
        this.productId = productId;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.productNumber = productNumber;
        this.orderState = orderState;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getProductImageUrl() {
//        return productImageUrl;
//    }
//
//    public void setProductImageUrl(String productImageUrl) {
//        this.productImageUrl = productImageUrl;
//    }
//
//    public int getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(int productPrice) {
//        this.productPrice = productPrice;
//    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

//    public int getProductRemain() {
//        return productRemain;
//    }
//
//    public void setProductRemain(int productRemain) {
//        this.productRemain = productRemain;
//    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
