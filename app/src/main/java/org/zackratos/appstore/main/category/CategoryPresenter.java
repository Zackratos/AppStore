package org.zackratos.appstore.main.category;

import com.google.gson.Gson;

import org.zackratos.appstore.SimpleParams;
import org.zackratos.appstore.base.RxPresenter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.Category;
import org.zackratos.appstore.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class CategoryPresenter extends RxPresenter<CategoryContract.View> implements CategoryContract.Presenter {

    private ServiceApi serviceApi;

    private Gson gson;

    private SimpleParams simpleParams;

    @Inject
    CategoryPresenter(ServiceApi serviceApi, Gson gson, SimpleParams simpleParams) {
        this.serviceApi = serviceApi;
        this.gson = gson;
        this.simpleParams = simpleParams;
    }


    @Override
    public void category() {
        Observable.just(simpleParams)
                .subscribeOn(Schedulers.io())
                .map(new Function<SimpleParams, String>() {
                    @Override
                    public String apply(@NonNull SimpleParams simpleParams) throws Exception {
                        return gson.toJson(simpleParams);
                    }
                })
                .flatMap(new Function<String, ObservableSource<BaseResult<List<Category>>>>() {
                    @Override
                    public ObservableSource<BaseResult<List<Category>>> apply(@NonNull String s) throws Exception {
                        return serviceApi.rxCategroy(s);
                    }
                })
                .compose(RxUtils.<List<Category>>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        view.categorySuccess(categories);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        view.categoryFail(message);
                    }
                });
    }
}
