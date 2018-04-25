package com.example.leidong.ldmart.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.example.leidong.ldmart.MyApplication;

/**
 * 字体相关工具类
 * @author Lei Dong
 */
public class FontUtils {

    public static void setFontFromAssets(TextView textView, String fontPath){
        Typeface typeface = Typeface.createFromAsset(MyApplication.getsAssetManager(), fontPath);
        textView.setTypeface(typeface);
    }
}
