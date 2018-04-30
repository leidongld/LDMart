package com.example.leidong.ldmart.secure;

import com.example.leidong.ldmart.MyApplication;
import com.example.leidong.ldmart.beans.Buyer;
import com.example.leidong.ldmart.beans.Seller;
import com.example.leidong.ldmart.greendao.BuyerDao;
import com.example.leidong.ldmart.greendao.SellerDao;

/**
 * 安全相关类
 * @author Lei Dong
 */
public class SecureUtils {
    /**
     * 密码是否合法
     * @param password1 输入密码1
     * @param password2 输入密码2
     * @return 是否合法
     */
    public static boolean isPasswordLegal(String password1, String password2) {
        return isEqual(password1, password2)
                && isLengthEnough(password1);
    }

    /**
     * 长度是否达标
     * @param password1 输入密码
     * @return 是否合法
     */
    private static boolean isLengthEnough(String password1) {
        return password1.length() >= 6;
    }

    /**
     * 字符串是否相等
     * @param password1 输入密码1
     * @param password2 输入密码2
     * @return 是否相等
     */
    private static boolean isEqual(String password1, String password2) {
        return password1.equals(password2);
    }

    /**
     * 买家密码是否匹配
     * @param passwordTemp 输入的密码
     * @param buyerId 买家Id
     * @return 密码匹配是否正确
     */
    public static boolean isBuyerPasswordRight(String passwordTemp, long buyerId) {
        BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        Buyer buyer = buyerDao.queryBuilder().where(BuyerDao.Properties.Id.eq(buyerId)).unique();
        String password = null;
        if(buyer != null){
            password = buyer.getPassword();
        }
        return passwordTemp.equals(password);
    }

    /**
     * 卖家密码是否匹配
     * @param passwordTemp 输入的密码
     * @param sellerId 卖家Id
     * @return 密码匹配是否正确
     */
    public static boolean isSellererPasswordRight(String passwordTemp, Long sellerId) {
        SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
        Seller seller = sellerDao.queryBuilder().where(SellerDao.Properties.Id.eq(sellerId)).unique();
        String password = null;
        if(seller != null){
            password = seller.getPassword();
        }
        return passwordTemp.equals(password);
    }
}
