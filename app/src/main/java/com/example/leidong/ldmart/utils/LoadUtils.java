package com.example.leidong.ldmart.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Order;
import com.example.leidong.ldmart.beans.Product;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.greendao.OrderDao;
import com.example.leidong.ldmart.greendao.ProductDao;
import com.example.leidong.ldmart.models.Products;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 数据加载相关工具类
 */
public class LoadUtils {
    /**
     * 获取assets文件夹下的对应Json文件并将其转换为String类型返回
     * @param fileName
     * @param context
     * @return
     */
    private static String getJsonFile(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String readLine;
        try {
            while((readLine = bufferedReader.readLine()) != null){
                stringBuilder.append(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }

    /**
     * 加载客户看到的产品信息
     * @return
     */
    public static Product[] loadProductsBuyerData() {
//        String jsonStr = getJsonFile("Products.json", MyApplication.getsContext());
//        Gson gson = new Gson();
//
//        Products products = gson.fromJson(jsonStr, Products.class);
//        List<Products.ProductsBean> productList = products.getProducts();
//
//        Products.ProductsBean[] productsBeans = new Products.ProductsBean[productList.size()];
//
//        int i = 0;
//        for(Products.ProductsBean bean : productList){
//            productsBeans[i++] = bean;
//        }
//
//        return productsBeans;

        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        List<Product> productList = productDao.loadAll();

        Product[] products = new Product[productList.size()];
        for(int i = 0; i < productList.size(); i++){
            products[i] = productList.get(i);
        }
        return products;
    }

    /**
     * 加载商户看到的产品信息
     * @return
     */
    public static Product[] loadProductsSellerData() {
//        String jsonStr = getJsonFile("Products.json", MyApplication.getsContext());
//        Gson gson = new Gson();
//
//        Products products = gson.fromJson(jsonStr, Products.class);
//        List<Products.ProductsBean> productsBeanList = products.getProducts();
//
//        Products.ProductsBean[] productsBeans = new Products.ProductsBean[productsBeanList.size()];
//
//        int i = 0;
//        for(Products.ProductsBean bean : productsBeanList){
//            productsBeans[i++] = bean;
//        }
//
//        return productsBeans;
        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        List<Product> productList = productDao.loadAll();
        if(productList.size() == 0){
            return null;
        }
        Product[] products = new Product[productList.size()];
        for(int i = 0; i < productList.size(); i++){
            products[i] = productList.get(i);
        }
        return products;
    }

    /**
     * 加载订单信息
     * @return
     * @param mBuyerId
     */
    public static Order[] loadOrdersBuyerData(Long mBuyerId) {
//        String jsonStr = getJsonFile("Orders.json", MyApplication.getsContext());
//        Gson gson = new Gson();
//
//        Orders orders = gson.fromJson(jsonStr, Orders.class);
//        List<Orders.OrdersBean> ordersBeanList = orders.getOrders();
//
//        Orders.OrdersBean[] ordersBeans = new Orders.OrdersBean[ordersBeanList.size()];
//
//        int i = 0;
//        for(Orders.OrdersBean bean : ordersBeanList){
//            ordersBeans[i++] = bean;
//        }
//
//        return ordersBeans;
        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        List<Order> orderList = orderDao.queryBuilder().where(OrderDao.Properties.BuyerId.eq(mBuyerId)).list();
        if(orderList.size() == 0){
            return null;
        }
        Order[] orders = new Order[orderList.size()];
        for(int i = 0; i < orderList.size(); i++){
            orders[i] = orderList.get(i);
        }
        return orders;
    }

    public static Order[] loadOrdersSellerData() {
//        String jsonStr = getJsonFile("Orders.json", MyApplication.getsContext());
//        Gson gson = new Gson();
//
//        Orders orders = gson.fromJson(jsonStr, Orders.class);
//        List<Orders.OrdersBean> ordersBeanList = orders.getOrders();
//
//        Orders.OrdersBean[] ordersBeans = new Orders.OrdersBean[ordersBeanList.size()];
//
//        int i = 0;
//        for(Orders.OrdersBean bean : ordersBeanList){
//            ordersBeans[i++] = bean;
//        }
//
//        return ordersBeans;

        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        List<Order> orderList = orderDao.queryBuilder().list();
        if(orderList.size() == 0){
            return null;
        }
        Order[] orders = new Order[orderList.size()];
        for(int i = 0; i < orderList.size(); i++){
            orders[i] = orderList.get(i);
        }
        return orders;
    }

    public static Buyer[] loadBuyersData() {
        return new Buyer[0];
    }

    public static Seller[] loadSellersData() {
        return new Seller[0];
    }

    /**
     * 以Json格式加载商品信息
     * @return
     */
    public static Products.ProductsBean[] loadProductsSellerDataFromJson() {
        String jsonStr = getJsonFile("Products.json", MyApplication.getsContext());
        Gson gson = new Gson();

        Products products = gson.fromJson(jsonStr, Products.class);
        List<Products.ProductsBean> productsBeanList = products.getProducts();

        Products.ProductsBean[] productsBeans = new Products.ProductsBean[productsBeanList.size()];

        int i = 0;
        for(Products.ProductsBean bean : productsBeanList){
            productsBeans[i++] = bean;
        }

        return productsBeans;
    }
}
