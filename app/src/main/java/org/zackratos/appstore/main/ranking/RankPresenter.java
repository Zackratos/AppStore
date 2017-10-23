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

//    private Observable<PageBean<AppInfo>> topListObservable;

    private Observable<PageBean<AppInfo>> getTopListObservable() {
        return Observable.just(page)
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
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public void loadFirstPager() {
        page = 0;
        Disposable disposable = getTopListObservable()
                .subscribe(new Consumer<PageBean<AppInfo>>() {
                    @Override
                    public void accept(PageBean<AppInfo> appInfoPageBean) throws Exception {
                        view.setFirstData(appInfoPageBean);
                        page++;
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(@StringRes int messageId) {
                        view.loadFirstFail(messageId);
                    }
                });

        addSubscribe(disposable);

    }

    private int page;

    @Override
    public void loadMore() {
        Disposable disposable = getTopListObservable()
                .subscribe(new Consumer<PageBean<AppInfo>>() {
                    @Override
                    public void accept(PageBean<AppInfo> appInfoPageBean) throws Exception {
                        view.setMoreData(appInfoPageBean);
                        page++;
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(@StringRes int messageId) {
                        view.loadMoreFail(messageId);
                    }
                });

        addSubscribe(disposable);
    }



    private static final String TAG = "RankPresenter";
}
