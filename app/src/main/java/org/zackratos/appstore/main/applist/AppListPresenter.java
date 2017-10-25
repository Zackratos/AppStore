package org.zackratos.appstore.main.applist;

import com.google.gson.Gson;

import org.zackratos.appstore.base.RxPresenter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.main.PageParams;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;
import org.zackratos.appstore.utils.RxUtils;

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
 * Created by xiboke on 2017/10/25.
 */

public abstract class AppListPresenter extends RxPresenter<AppListContract.View> implements AppListContract.Presenter {

    protected ServiceApi serviceApi;

    private int page;

    private PageParams pageParams;

    private Gson gson;


    public AppListPresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        this.serviceApi = serviceApi;
        this.gson = gson;
        this.pageParams = params;
    }


    private Observable<PageBean<AppInfo>> getTopListObservable() {
        return Observable.just(page)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        pageParams.setPage(integer);
                        return gson.toJson(pageParams);
                    }
                })
                .flatMap(new Function<String, ObservableSource<BaseResult<PageBean<AppInfo>>>>() {
                    @Override
                    public ObservableSource<BaseResult<PageBean<AppInfo>>> apply(@NonNull String s) throws Exception {
//                        return serviceApi.rxTopList(s);
                        return getBaseResultObservable(s);
                    }
                })
                .compose(RxUtils.<PageBean<AppInfo>>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread());
    }




    @Override
    public void loadFirstPage() {
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
                    public void handlerError(String message) {
                        view.loadFirstFail(message);
                    }
                });

        addSubscribe(disposable);
    }

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
                    public void handlerError(String message) {
                        view.loadMoreFail(message);
                    }
                });

        addSubscribe(disposable);
    }

    protected abstract ObservableSource<BaseResult<PageBean<AppInfo>>> getBaseResultObservable(String params);

}
