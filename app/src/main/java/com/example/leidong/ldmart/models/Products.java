package com.example.leidong.ldmart.models;

import java.util.List;

/**
 * Product Json
 *
 * @author Lei Dong
 */
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
         * categoryId : 1
         * productName : 精品脐橙
         * productImage : https://img12.360buyimg.com/babel/s360x360_jfs/t3295/14/5775084582/492685/47d076e7/587e0252Nf62a419b.jpg!q80.webp
         * productPrice : 1
         * productNumber : 100
         * productDesc : 被春天吻过的鲜橙，生长期更长，耐心酝酿更优质的甜橙。
         */

        private int productId;
        private int categoryId;
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

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
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
