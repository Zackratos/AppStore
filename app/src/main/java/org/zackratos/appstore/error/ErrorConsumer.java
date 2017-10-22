package org.zackratos.appstore.error;

import android.support.annotation.StringRes;

import org.zackratos.appstore.R;

import java.lang.*;
import java.net.ConnectException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public abstract class ErrorConsumer implements Consumer<Throwable> {

//    private static final String HTTP_EXCEPTION = "网络异常";
//    private static final String CONNECT_EXCEPTION = "网络连接异常";

    @Override
    public void accept(Throwable throwable) throws Exception {

        @StringRes
        int messageId;

        if (throwable instanceof ConnectException) {
            messageId = R.string.http_connect_exception;
        } else if (throwable instanceof HttpException) {
            messageId = R.string.http_http_exception;
        } else if (throwable instanceof ApiException) {
            messageId = R.string.http_api_exception;
        } else {
            messageId = R.string.http_unknown_exception;
        }

        handlerError(messageId);
    }

    public abstract void handlerError(@StringRes int messageId);
}
