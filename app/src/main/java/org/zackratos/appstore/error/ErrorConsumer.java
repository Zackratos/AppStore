package org.zackratos.appstore.error;


import android.app.Application;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.App;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public abstract class ErrorConsumer implements Consumer<Throwable> {

    @Inject
    Application application;

    public ErrorConsumer() {
        App.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void accept(Throwable throwable) throws Exception {

        String message;

        if (throwable instanceof ConnectException) {
            message = application.getString(R.string.http_connect_exception);
        } else if (throwable instanceof HttpException) {
            message = application.getString(R.string.http_http_exception);
        } else if (throwable instanceof ApiException) {
            message = throwable.getMessage();
        } else {
            message = application.getString(R.string.http_unknown_exception);
        }

        handlerError(message);
    }

    public abstract void handlerError(String message);
}
