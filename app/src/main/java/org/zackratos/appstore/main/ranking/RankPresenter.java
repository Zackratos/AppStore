package org.zackratos.appstore.main.ranking;

import android.support.annotation.StringRes;

import com.google.gson.Gson;

import org.zackratos.appstore.base.RxPresenter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;
import org.zackratos.appstore.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class RankPresenter extends RxPresenter<RankContract.View> implements RankContract.Presenter {

    private ServiceApi serviceApi;

//    private String params;

    private TopListParams topListParams;

    private Gson gson;

    @Inject
    public RankPresenter(ServiceApi serviceApi, Gson gson, TopListParams params) {
        this.serviceApi = serviceApi;
        this.gson = gson;
        this.topListParams = params;
    }


    @Override
    public void topList(int page) {
        Disposable disposable = Observable.just(page)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        topListParams.setPage(integer);
                        return gson.toJson(topListParams);
                    }
                })
                .flatMap(new Function<String, ObservableSource<BaseResult<PageBean<AppInfo>>>>() {
                    @Override
                    public ObservableSource<BaseResult<PageBean<AppInfo>>> apply(@NonNull String s) throws Exception {
                        return serviceApi.rxTopList(s);
                    }
                })
                .compose(RxUtils.<PageBean<AppInfo>>handlerBaseError())
                .compose(RxUtils.<AppInfo>handlerPageError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppInfo>>() {
                    @Override
                    public void accept(List<AppInfo> appInfos) throws Exception {
                        view.setData(appInfos);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(@StringRes int messageId) {

                    }
                });

        addSubscribe(disposable);

    }




    private static final String TAG = "RankPresenter";
}
