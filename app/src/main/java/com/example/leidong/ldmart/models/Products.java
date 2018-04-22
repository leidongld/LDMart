package com.example.leidong.ldmart.models;

import java.util.List;

public class Products {

    private List<ProductsBean> products;

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * productId : 0
         * productName : 西瓜1
         * productImage : http://58pic.ooopic.com/58pic/12/92/84/36c58PICefh.jpg
         * productPrice : 20
         * productNumber : 100
         * productDesc : 西瓜1的介绍
         */

        private int productId;
        private String productName;
        private String productImage;
        private int productPrice;
        private int productNumber;
        private String productDesc;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
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

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }
    }
}
