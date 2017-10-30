package org.zackratos.appstore.http;

import android.app.Application;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zlc.season.rxdownload2.RxDownload;

/**
 *
 * Created by Administrator on 2017/10/21.
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ServiceApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ServiceApi provideApi(Retrofit retrofit) {
        return retrofit.create(ServiceApi.class);
    }

    @Singleton
    @Provides
    public RxDownload provideRxDownload(Retrofit retrofit, Application application) {
        File folder = application.getExternalFilesDir("app");
        if (folder == null) {

        }
        return RxDownload.getInstance(application)
                .retrofit(retrofit)
                .maxDownloadNumber(10)
                .defaultSavePath(application.getExternalFilesDir("app").getAbsolutePath())
                .maxThread(10);
    }

}
