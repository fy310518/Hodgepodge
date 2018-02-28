package hodgepodge.fy.com.main.fragment;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.utils.ResourceUtils;

import hodgepodge.fy.com.R;

/**
 * 我的
 * Created by fangs on 2017/12/12.
 */
public class FragmentFive extends BaseFragment {


    @Override
    protected int setContentLayout() {
        return R.layout.fragment_main_five;
    }

    @Override
    protected void baseInit() {

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {

    }

    /**
     * 使用 Spannable 动态设置 textview 显示内容
     *
     * @param id
     * @param replaceStr
     * @return
     */
    private Spannable getSpann(int id, String replaceStr) {
        Spannable sp = new SpannableString(ResourceUtils.getText(id, replaceStr));
        sp.setSpan(new AbsoluteSizeSpan(21, true), 0, replaceStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.login)), 0, replaceStr.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(14, true), replaceStr.length(), sp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return sp;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }


}
