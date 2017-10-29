package org.zackratos.appstore.http;

import android.app.Application;

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
        return RxDownload.getInstance()
                .retrofit(retrofit)
                .context(application)
                .defaultSavePath(application.getExternalFilesDir("app").getAbsolutePath())
                .maxDownloadNumber(10)
                .maxThread(10);
    }

}
