package hodgepodge.fy.com.main.fragment;

import android.view.View;

import com.fy.baselibrary.base.BaseFragment;

import hodgepodge.fy.com.R;

/**
 * 修改：一般情况下都不需要自动刷新 只有自己发布消息回来本界面时才自动刷新
 * Created by Administrator on 2017/12/12.
 */
public class FragmentFour extends BaseFragment {

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_main_four;
    }

    @Override
    protected void baseInit() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }


}
