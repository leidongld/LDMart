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
import java.util.ArrayList;
import java.util.List;

/**
 * 数据加载相关工具类
 * @author Lei Dong
 */
public class LoadUtils {
    /**
     * 获取assets文件夹下的对应Json文件并将其转换为String类型返回
     * @param fileName 文件名
     * @param context Context
     * @return Json字符串
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
     * 加载客户看到的商品信息
     * @return 商品信息
     */
    public static Product[] loadBuyerProducts() {
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
     * @return 商品信息
     */
    public static Product[] loadSellerProducts() {
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
     * 加载买家的订单信息
     * @param mBuyerId 买家Id
     * @return 该买家的全部订单
     */
    public static Order[] loadBuyerOrders(Long mBuyerId) {
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

    /**
     * 加载全部的卖家订单
     * @return 全部的卖家订单
     */
    public static Order[] loadSellerOrders() {
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
     * @return 商品信息
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

    /**
     * 根据输入的日期过滤买家订单
     * @param mBuyerId 买家Id
     * @param s 输入内容
     * @return 符合条件的订单数组
     */
    public static Order[] loadBuyerOrdersByDate(Long mBuyerId, String s) {
        Order[] mOrders;
        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        List<Order> orderList = orderDao.queryBuilder()
                .where(OrderDao.Properties.BuyerId.eq(mBuyerId)).list();
        List<Order> tempList = new ArrayList<>();
        for(Order order : orderList){
            if(order.getOrderId().contains(s)){
                tempList.add(order);
            }
        }
        mOrders = new Order[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            mOrders[i] = tempList.get(i);
        }
        return mOrders;
    }

    /**
     * 根据输入的日期过滤卖家订单
     * @param s 输入内容
     * @return 符合条件的订单数组
     */
    public static Order[] loadSellerOrdersByDate(String s) {
        Order[] mOrders;
        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        List<Order> orderList = orderDao.loadAll();
        List<Order> tempList = new ArrayList<>();
        for(Order order : orderList){
            if(order.getOrderId().contains(s)){
                tempList.add(order);
            }
        }
        mOrders = new Order[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            mOrders[i] = tempList.get(i);
        }
        return mOrders;
    }

    /**
     * 根据输入的名称过滤买家商品
     * @param s 输入的名称
     * @return 符合要求的商品数组
     */
    public static Product[] loadBuyerProductsByName(String s) {
        Product[] products;
        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        List<Product> productList = productDao.loadAll();
        List<Product> tempList = new ArrayList<>();
        for(Product product : productList){
            if(product.getProductName().contains(s)){
                tempList.add(product);
            }
        }
        products = new Product[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            products[i] = tempList.get(i);
        }
        return products;
    }

    /**
     * 根据输入的名称过滤卖家商品
     * @param s 输入的名称
     * @return 符合要求的商品数组
     */
    public static Product[] loadSellerProductsByName(String s) {
        Product[] products;
        ProductDao productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        List<Product> productList = productDao.loadAll();
        List<Product> tempList = new ArrayList<>();
        for(Product product : productList){
            if(product.getProductName().contains(s)){
                tempList.add(product);
            }
        }
        products = new Product[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            products[i] = tempList.get(i);
        }
        return products;
    }
}
