package org.zackratos.appstore.main.recommend;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.zackratos.appstore.R;
import org.zackratos.appstore.common.SimpleParams;
import org.zackratos.appstore.base.RxPresenter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.Banners;
import org.zackratos.appstore.result.DownloadInfo;
import org.zackratos.appstore.result.Header;
import org.zackratos.appstore.result.IndexData;
import org.zackratos.appstore.result.NavIcon;
import org.zackratos.appstore.utils.RxUtils;

import java.util.ArrayList;
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
import zlc.season.rxdownload2.RxDownload;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public class RecommendPresenter extends RxPresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private static final String TAG = "RecommendPresenter";

    private ServiceApi serviceApi;

    private String params;

    private Gson gson;

    private SimpleParams simpleParams;

    private RxDownload rxDownload;

    @Inject
    RecommendPresenter(ServiceApi serviceApi, Gson gson,
                       SimpleParams simpleParams, RxDownload rxDownload) {
        this.serviceApi = serviceApi;
        this.gson = gson;
        this.simpleParams = simpleParams;
        this.params = gson.toJson(simpleParams);
        this.rxDownload = rxDownload;
    }

    @Override
    public void index() {
        Disposable disposable = serviceApi.rxIndex(params)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<IndexData>handlerBaseError())
                .map(new Function<IndexData, List<MultiItemEntity>>() {
                    @Override
                    public List<MultiItemEntity> apply(@NonNull IndexData data) throws Exception {
                        List<MultiItemEntity> entities = new ArrayList<>();
                        entities.add(new Banners(data.getBannerBeen()));
                        entities.add(new NavIcon());
                        entities.add(new Header(R.string.rec_app));
                        entities.addAll(data.getRecommendApps());
                        entities.add(new Header(R.string.rec_game));
                        entities.addAll(data.getRecommendGames());
                        return entities;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MultiItemEntity>>() {
                    @Override
                    public void accept(List<MultiItemEntity> entities) throws Exception {
                        view.setData(entities);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        view.loadFail(message);
                    }
                });

        addSubscribe(disposable);
    }


    @Override
    public void download(AppInfo appInfo) {
        Disposable disposable = Observable.just(appInfo)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<AppInfo, ObservableSource<BaseResult<DownloadInfo>>>() {
                    @Override
                    public ObservableSource<BaseResult<DownloadInfo>> apply(@NonNull AppInfo appInfo) throws Exception {
                        String params = gson.toJson(simpleParams);
                        return serviceApi.rxDownload(appInfo.getId(), params);
                    }
                })
                .compose(RxUtils.<DownloadInfo>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadInfo>() {
                    @Override
                    public void accept(DownloadInfo downloadInfo) throws Exception {

                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        view.downloadError(message);
                    }
                });


        addSubscribe(disposable);

    }
}
