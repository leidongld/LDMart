package com.example.leidong.ldmart.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 事件相关工具类
 */
public class TimeUtils {
    private static final int RANDOM_COUNTS = 4;

    /**
     * 生成
     * @return
     */
    public static String getCurrentSysTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 产生有0-9的随机数组成的4位字符串用于生成订单号
     * @param randomCounts
     * @return
     */
    private static String generateRandomStr(int randomCounts) {
        Random random = new Random();
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < randomCounts; i++) {
            res.append(random.nextInt(10));
        }
        return res.toString().trim();
    }

    /**
     * 生成订单号
     * @return
     */
    public static String generateOrderId() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String randomStr = generateRandomStr(RANDOM_COUNTS);
        String res = time + randomStr;
        return res;
    }
}
