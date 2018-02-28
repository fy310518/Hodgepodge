package com.fy.baselibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.fy.baselibrary.application.BaseApplication;

/**
 * Tint 是 Android5.0 引入的一个属性
 * Created by fangs on 2018/2/11.
 */
public class TintUtils {

    /**
     * tint这个属性，是ImageView有的，它可以给ImageView的src设置，除了tint 之外，
     * 还有backgroundTint,foregroundTint,drawableTint,它们分别对应对背景、前景、drawable进行着色处理。
     */
    private TintUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 使用 tint改变 drawable 颜色
     * @param drawableId    将要改变的 drawable 的资源 id
     * @param colorId       将要改变的 颜色 id
     * @return
     */
    public static Drawable getTintDrawable(@DrawableRes int drawableId, @ColorRes int colorId) {
        Context ctx = BaseApplication.getApplication();
        Drawable drawable = ContextCompat.getDrawable(ctx, drawableId);
        int color = ContextCompat.getColor(ctx, colorId);

        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat
                .wrap(state == null ? drawable : state.newDrawable())
                .mutate();//调用此方法 避免同一界面 展示同一Drawable 所有的Drawable 的颜色都改变

        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, color);

        return drawable1;
    }


    /****************优雅的实现背景选择器*******************/
    /*
        1、显示不同的颜色 数组
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.pink),
            ContextCompat.getColor(this, R.color.colorPrimaryDark)};
       /2、View状态数组（比如按下，选中等）
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_pressed};
            states[1] = new int[]{};

        方式一：
            Drawable drawable2 = tintSelector(drawable, colors, states);
            imageView1.setBackground(drawable2);
        方式二：
            StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
            Drawable drawable3 = tintSelector(stateListDrawable, colors, states);
            imageView2.setBackground(drawable3);
     */

    /**
     * StateListDrawable 设置背景选择器
     * @param drawable              图片资源（shape,png图片，svg图）
     * @param states                View状态数组（比如按下，选中等）
     * @return
     */
    public static StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state : states) {
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }

    /**
     * Tint 方式实现 selector
     * @param drawable          图片资源（shape, png图片，svg图）
     * @param colors            不同状态 显示不同的颜色 数组
     * @param states            View状态数组（比如按下，选中等）
     * @return
     */
    public static Drawable tintSelector(Drawable drawable, int[] colors, int[][] states) {
        ColorStateList colorList = new ColorStateList(states, colors);

        Drawable.ConstantState state = drawable.getConstantState();
        drawable = DrawableCompat
                .wrap(state == null ? drawable : state.newDrawable())
                .mutate();

        DrawableCompat.setTintList(drawable, colorList);

        return drawable;
    }
}
