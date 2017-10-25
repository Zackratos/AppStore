package org.zackratos.appstore.app;

import android.app.Application;

import com.google.gson.Gson;

import org.zackratos.appstore.data.DataHelper;
import org.zackratos.appstore.http.PublicParams;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by zack on 17-10-10.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public ToastHelper provideToastHelper(Application context) {
        return new ToastHelper(context);
    }

    @Singleton
    @Provides
    public PublicParams providePublicParams(Application application, DataHelper dataHelper) {
        return new PublicParams(application, dataHelper);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }


}
