package org.zackratos.appstore.main.recommend;


import android.support.annotation.StringRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.zackratos.appstore.base.RxPresenter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public class RecommendPresenter extends RxPresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private static final String TAG = "RecommendPresenter";

    private ServiceApi serviceApi;

    private String params;

    @Inject
    RecommendPresenter(ServiceApi serviceApi, Gson gson, IndexParams params) {
        this.serviceApi = serviceApi;
        this.params = gson.toJson(params);
    }

    @Override
    public void index() {
        Disposable disposable = serviceApi.rxIndex(params)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<IndexData>errorHandler())
                .map(new Function<IndexData, List<MultiItemEntity>>() {
                    @Override
                    public List<MultiItemEntity> apply(@NonNull IndexData data) throws Exception {
                        List<MultiItemEntity> entities = new ArrayList<>();
                        entities.add(new Banners(data.getBannerBeen()));
                        entities.add(new NavIcon());
                        entities.add(new Header());
                        entities.addAll(data.getRecommendApps());
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
                    public void handlerError(@StringRes int messageId) {
                        view.loadFail(messageId);
                    }
                });

        addSubscribe(disposable);
    }
}
