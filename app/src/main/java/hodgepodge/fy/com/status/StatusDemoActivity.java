package hodgepodge.fy.com.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;

import java.util.concurrent.TimeUnit;

import hodgepodge.fy.com.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 多状态布局 demo
 * Created by fangs on 2018/3/16.
 */
public class StatusDemoActivity extends AppCompatActivity implements IBaseActivity{
    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.activity_status;
    }

    @Override
    public void setStatusBar(Activity activity) {
        MdStatusBar.setColorBar(activity, R.color.blue, R.color.blue);
    }

    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void reTry() {

    }
}
