package com.example.leidong.ldmart.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 商品Bean
 */
@Entity
public class Product {
    @Id(autoincrement = true)
    private Long id;
    private String productName;
    private String productImageUrl;
    private int productPrice;
    private int productStock;
    private String desc;

    @Generated(hash = 762026572)
    public Product(Long id, String productName, String productImageUrl,
            int productPrice, int productStock, String desc) {
        this.id = id;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.desc = desc;
    }

    @Generated(hash = 1890278724)
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
