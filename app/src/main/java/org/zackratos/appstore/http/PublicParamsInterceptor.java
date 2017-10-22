package org.zackratos.appstore.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 * Created by Administrator on 2017/10/21.
 */

public class PublicParamsInterceptor implements Interceptor {

    private String paramsString;

    public PublicParamsInterceptor(String paramsString) {
        this.paramsString = paramsString;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
