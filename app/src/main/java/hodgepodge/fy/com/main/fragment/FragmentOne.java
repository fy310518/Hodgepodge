package hodgepodge.fy.com.main.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.utils.ResourceUtils;
import com.fy.baselibrary.utils.TintUtils;

import butterknife.BindView;
import butterknife.OnClick;
import hodgepodge.fy.com.R;

/**
 * 首页
 * Created by Administrator on 2017/12/12.
 */
public class FragmentOne extends BaseFragment {

    @BindView(R.id.tvImgPicker)
    TextView tvImgPicker;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_main_one;
    }

    @Override
    protected void baseInit() {


        Drawable drawable = TintUtils.getDrawable(R.drawable.vector_mainfive_normal);
        int[][] states = new int[2][];
        int[] colors = new int[]{ResourceUtils.getColor(R.color.pink),
                ResourceUtils.getColor(R.color.colorPrimaryDark)};

        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        Drawable drawable2 = TintUtils.tintSelector(drawable, colors, states);
        TintUtils.setTxtIconLocal(tvImgPicker, drawable2, 1);
    }


    @OnClick({R.id.tvImgPicker})
    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
