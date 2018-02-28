package com.fy.baselibrary.retrofit;

import com.fy.baselibrary.application.BaseApplication;
import com.fy.baselibrary.statuslayout.RootFrameLayout;
import com.fy.baselibrary.utils.ConstantUtils;
import com.fy.baselibrary.utils.L;
import com.fy.baselibrary.utils.NetUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 网络请求入口
 * Created by fangs on 2017/8/28.
 */
public class NetRequest {

    private Builder builder;

    private volatile static NetRequest netRequest;

    private NetRequest() {
        super();
        ConstantUtils.custom_Url = SpfUtils.getSpfSaveStr("ServiceAddress");
    }

    public static NetRequest getInstens(){
        if (null == netRequest) {
            synchronized (NetRequest.class) {
                if (null == netRequest) {
                    netRequest = new NetRequest();
                }
            }
        }

        return netRequest;
    }

    public NetRequest setBuilder(Builder builder) {
        this.builder = builder;

        return this;
    }

    /**
     * 采用 dagger2 依赖注入 框架 + retorfit 请求 后台 数据
     * @param callBack
     * @param <V>
     */
    public <V> void requestDate(Observable<V> fromNetwrok, NetCallBack<V> callBack) {

        RxRetrofitCache.load(ConstantUtils.userId + builder.getApi(),
                1000 * 60 * 10,fromNetwrok, builder.isRefresh())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                        if (!NetUtils.isConnected(BaseApplication.getApplication())){
                            T.showShort("似乎没有网络哦!!!");
                            callBack.updataLayout(RootFrameLayout.LAYOUT_NETWORK_ERROR_ID);
                            disposable.dispose();
                        } else {
                            callBack.updataLayout(RootFrameLayout.LAYOUT_CONTENT_ID);
                        }
                    }
                })
                .subscribe(callBack);
    }

    /**
     * 使用rxJava2 合并请求
     * @param fromNetwrok1
     * @param callBack
     * @param <V1>
     * @param <V2>
     */
    public <V1, V2> void requestZip(Observable<V1> fromNetwrok1, NetCallBackZip<V1, V2> callBack){

        fromNetwrok1.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {

                if (!NetUtils.isConnected(BaseApplication.getApplication())){
                    T.showShort("似乎没有网络哦!!!");
                    callBack.updataLayout(RootFrameLayout.LAYOUT_NETWORK_ERROR_ID);
                    disposable.dispose();
                } else {
                    callBack.updataLayout(RootFrameLayout.LAYOUT_CONTENT_ID);
                }
            }
        }).map(new Function<V1, V1>() {
            @Override
            public V1 apply(@NonNull V1 result) throws Exception {
                L.e("net map", result.toString());
                return result;
            }
        }).doOnNext(new Consumer<V1>() {
            @Override
            public void accept(V1 result) throws Exception {
                L.e("net doOnNext", result.toString());
            }
        }).flatMap(new Function<V1, ObservableSource<V2>>() {
            @Override
            public ObservableSource<V2> apply(V1 v1) throws Exception {
                L.e("net flatMap", v1.toString());
                return callBack.zipRequest(v1);
            }
        }).subscribe(callBack);
    }



    public static class Builder {
        String api = "";
        boolean Refresh;

        public Builder() {
        }

        public String getApi() {
            return api;
        }

        public Builder setApi(String api) {
            this.api = api;
            return this;
        }

        public boolean isRefresh() {
            return Refresh;
        }

        public Builder setRefresh(boolean refresh) {
            Refresh = refresh;
            return this;
        }

        public NetRequest create(){
            return getInstens().setBuilder(this);
        }
    }
}
