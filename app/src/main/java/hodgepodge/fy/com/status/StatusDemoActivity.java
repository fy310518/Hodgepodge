package hodgepodge.fy.com.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statuslayout.OnRetryListener;
import com.fy.baselibrary.statuslayout.OnShowHideViewListener;
import com.fy.baselibrary.statuslayout.StatusLayoutManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import hodgepodge.fy.com.R;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 多状态布局 demo
 * Created by fangs on 2018/3/16.
 */
public class StatusDemoActivity extends AppCompatActivity implements IBaseActivity{

    StatusLayoutManager slManager;

    @BindView(R.id.tvKing)
    TextView tvKing;

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
        initSLManager();

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        slManager.showError();
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void reTry() {
        slManager.showEmptyData();

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Long, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Long aLong) throws Exception {
                        slManager.showNetWorkError();
                        return Observable.timer(3000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Object, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Object o) throws Exception {
                        slManager.showError();
                        return Observable.timer(3000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        slManager.showContent();
                    }
                });
    }

    /**
     * 设置 多状态视图 管理器
     */
    protected void initSLManager() {
        slManager = StatusLayoutManager.newBuilder(this, this)
                .setShowHeadView(isShowHeadView())
                .errorView(R.layout.activity_error)
                .netWorkErrorView(R.layout.activity_networkerror)
                .emptyDataView(R.layout.activity_emptydata)
                .retryViewId(R.id.tvTry)
                .onRetryListener(() -> reTry())
                .build();

        slManager.showContent();
    }
}
