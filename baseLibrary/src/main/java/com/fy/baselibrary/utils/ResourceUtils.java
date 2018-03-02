package com.fy.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.fy.baselibrary.application.BaseApp;

/**
 * 通过 getResources() 为控件 设置内容
 * Created by fangs on 2017/9/13.
 */
public class ResourceUtils {

    private ResourceUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取资源 dimens 文件 内容
     * @param dimenId
     * @return
     */
    public static float getDimen(int dimenId){
        return BaseApp.getAppCtx().getResources().getDimension(dimenId);
    }

//    %d   （表示整数）
//    %f   （表示浮点数）
//    %s   （表示字符串）
    /**
     * %1$s Android string （java & Android 格式化字符串）
     * @param tv
     * @param id 资源ID（如：ID内容为 “病人ID：%1$s”）
     * @param replaceStr 将要替换的内容
     */
    public static void setText(TextView tv, int id, String replaceStr){
        String format = BaseApp.getAppCtx().getResources().getString(id);
        String text = String.format(format, replaceStr);
        tv.setText(text);
    }

    /**
     * %1$d Android string （java & Android 格式化字符串）
     * @param tv
     * @param id 资源ID（如：ID内容为 “病人ID：%1$d”）
     * @param replaceInt 将要替换的内容
     */
    public static void setText(TextView tv, int id, int replaceInt){
        String format = BaseApp.getAppCtx().getResources().getString(id);
        String text = String.format(format, replaceInt);
        tv.setText(text);
    }

    /**
     * 获取string资源文件 指定 id 的资源，替换后的字符串
     * @param id            资源ID（如：ID内容为 “病人ID：%1$s”）
     * @param replaceStr    将要替换的内容
     * @return 替换后的字符串
     */
    public static String getText(int id, String replaceStr){
        String format = BaseApp.getAppCtx().getResources().getString(id);

        return String.format(format, replaceStr);
    }

    /**
     * 获取string资源文件 指定 id 的资源，替换后的字符串
     * @param id            资源ID（如：ID内容为 “病人ID：%1$d”）
     * @param replaceStr    将要替换的内容
     * @return 替换后的字符串
     */
    public static String getText(int id, int replaceStr){
        String format = BaseApp.getAppCtx().getResources().getString(id);

        return String.format(format, replaceStr);
    }

    /**
     * 动态设置 TextView 颜色 (getResources().getColor 过时 替代方式)
     * @param colorId
     * @return
     */
    public static void setTvColor(TextView tv, int colorId){
        tv.setTextColor(ContextCompat.getColor(BaseApp.getAppCtx(), colorId));
    }

    /**
     * 设置TextView 四个方向某一个方向上的icon (非vector图标)
     * @param tv
     * @param id
     * @param position 设置的位置
     */
    public static void setCompoundDrawable(TextView tv, int id, int position) {
        Context ctx = BaseApp.getAppCtx();
        Drawable drawable = ContextCompat.getDrawable(ctx, id);
        setTxtIconLocal(tv, drawable, position);
    }

    /**
     * 设置TextView 四个方向某一个方向上的icon (vector图标)
     * @param tv
     * @param id
     * @param position
     */
    public static void setCompoundVctDrawable(TextView tv, int id, int position){
        Context ctx = BaseApp.getAppCtx();
        VectorDrawableCompat vDrawable = VectorDrawableCompat.create(ctx.getResources(),
                id, ctx.getTheme());

        setTxtIconLocal(tv, vDrawable, position);
    }

    /**
     * 设置icon 在TextView的位置
     * @param tv
     * @param drawable
     * @param position
     */
    public static void setTxtIconLocal(TextView tv, Drawable drawable, int position){
        drawable.setBounds(0, 0,
                drawable.getMinimumWidth(), drawable.getMinimumHeight());

        switch (position) {
            case 1:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

}
