package org.zackratos.appstore.utils;

import org.zackratos.appstore.error.ApiException;
import org.zackratos.appstore.http.BaseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by zack on 17-10-11.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> thread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<BaseResult<T>, T> errorHandler() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResult<T>> upstream) {

                return upstream.flatMap(new Function<BaseResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull BaseResult<T> tBaseResult) throws Exception {
                        if (tBaseResult.getStatus() == 1) {
                            return Observable.just(tBaseResult.getData());
                        }
                        return Observable.error(new ApiException(tBaseResult.getMessage()));
                    }
                });
            }
        };
    }

}
