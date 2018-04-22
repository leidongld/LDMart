package com.example.leidong.ldmart.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 订单Bean
 */
@Entity
public class Order {
    @Id(autoincrement = true)
    private Long id;
    private Long buyerId;
    private String orderId;
    private String orderTime;
    private String productName;
    private String productImageUrl;
    private int productPrice;
    private int productNumber;
    private int productRemain;
    private int orderState;

    @Generated(hash = 1655734202)
    public Order(Long id, Long buyerId, String orderId, String orderTime,
            String productName, String productImageUrl, int productPrice,
            int productNumber, int productRemain, int orderState) {
        this.id = id;
        this.buyerId = buyerId;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productNumber = productNumber;
        this.productRemain = productRemain;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
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
}
