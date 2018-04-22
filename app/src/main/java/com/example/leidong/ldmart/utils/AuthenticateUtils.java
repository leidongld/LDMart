package com.example.leidong.ldmart.utils;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Root;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.RootDao;
import com.example.leidong.ldmart.greendao.SellerDao;

import java.util.List;

/**
 * 认证相关工具类
 */
public class AuthenticateUtils {
    /**
     * 认证Root管理员的登陆信息
     * @param usernameTemp
     * @param passwordTemp
     */
    public static boolean authenticateRoot(String usernameTemp, String passwordTemp) {
        RootDao rootDao = MyApplication.getInstance().getDaoSession().getRootDao();
        List<Root> rootList = rootDao.loadAll();
        Root root = rootList.get(0);
        String username = root.getUsername();
        String password = root.getPassword();
        return username.equals(usernameTemp) && password.equals(passwordTemp);
    }

    /**
     * 认证商家的登录信息
     * @return
     * @param usernameTemp
     * @param passwordTemp
     */
    public static boolean authenticateSeller(String usernameTemp, String passwordTemp) {
        //TODO
        SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
        List<Seller> sellerList = sellerDao.loadAll();
        if(sellerList.size() == 0){
            return false;
        }
        String realUsername = sellerList.get(0).getUsername();
        String realPassword = sellerList.get(0).getPassword();
        return realUsername.equals(usernameTemp)
                && realPassword.equals(passwordTemp);
    }

    /**
     * 认证买家的登录信息
     * @param usernameTemp
     * @param passwordTemp
     * @return
     */
    public static boolean authenticateBuyer(String usernameTemp, String passwordTemp) {
        //TODO
        BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        List<Buyer> buyerList = buyerDao.loadAll();
        if(buyerList.size() == 0){
            return false;
        }
        for(Buyer buyer : buyerList){
            if(buyer.getUsername().equals(usernameTemp) && buyer.getPassword().equals(passwordTemp)){
                return true;
            }

        }
        return false;
    }

    /**
     * 判断此Buyer是否已经存在
     * @param username
     * @return
     */
    public static boolean buyerNotExsit(String username) {
        BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        List<Buyer> buyerList = buyerDao.loadAll();
        if(buyerList.size() == 0){
            return true;
        }
        for(Buyer buyer : buyerList){
            if(buyer.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
}
