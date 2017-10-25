package org.zackratos.appstore.main.applist.ranking;


import com.google.gson.Gson;

import org.zackratos.appstore.http.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.main.applist.AppListPresenter;
import org.zackratos.appstore.main.PageParams;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;

import javax.inject.Inject;

import io.reactivex.ObservableSource;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class RankPresenter extends AppListPresenter {

    @Inject
    public RankPresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        super(serviceApi, gson, params);
    }

    @Override
    protected ObservableSource<BaseResult<PageBean<AppInfo>>> getBaseResultObservable(String params) {
        return serviceApi.rxTopList(params);
    }

    /*    private ServiceApi serviceApi;

    private PageParams pageParams;

    private Gson gson;

    @Inject
    public RankPresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        this.serviceApi = serviceApi;
        this.gson = gson;
        this.pageParams = params;
    }

//    private Observable<PageBean<AppInfo>> topListObservable;

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
                        return serviceApi.rxTopList(s);
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
                    public void handlerError(String message) {
                        view.loadMoreFail(message);
                    }
                });

        addSubscribe(disposable);
    }



    private static final String TAG = "RankPresenter";*/
}
