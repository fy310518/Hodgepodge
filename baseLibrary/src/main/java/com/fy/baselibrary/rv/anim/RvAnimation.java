package com.fy.baselibrary.rv.anim;

import android.animation.Animator;
import android.view.View;

/**
 * RecyclerView 添加动画(策略模式)
 * 参考 https://www.jianshu.com/p/fa3f97c19263
 */
public interface RvAnimation {
    public Animator[] getAnimators(View view);
}
