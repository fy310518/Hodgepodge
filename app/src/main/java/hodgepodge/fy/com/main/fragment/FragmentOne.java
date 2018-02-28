package hodgepodge.fy.com.main.fragment;

import android.view.View;

import com.fy.baselibrary.base.BaseFragment;

import butterknife.OnClick;
import hodgepodge.fy.com.R;

/**
 * Created by Administrator on 2017/12/12.
 * 首页
 */
public class FragmentOne extends BaseFragment {


    private String[] images = {
//            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
//            "http://img5.duitang.com/uploads/item/201606/01/20160601001315_wC3mU.jpeg",
//            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
//            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513314151355&di=3c0d90cff5a9c95b5696720a1282adcf&imgtype=0&src=http%3A%2F%2Fi2.17173cdn.com%2Fz0og4j%2FYWxqaGBf%2Fnewgame%2F20160729%2FSNzFzpbkyAkFEwk.jpg",
//            "http://192.168.100.30:8098/YDYS/20171219/20171220161832.png"
            "http://116.196.95.169:8080/images/banner/android/mipmap-xhdpi/banner1.png"
    };


    @Override
    protected int setContentLayout() {
        return R.layout.fragment_main_one;
    }

    @Override
    protected void baseInit() {

    }


    @OnClick({R.id.tvImgPicker})
    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
