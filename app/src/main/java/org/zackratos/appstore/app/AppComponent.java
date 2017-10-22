package org.zackratos.appstore.app;

import android.app.Application;

import com.google.gson.Gson;

import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.http.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * Created by zack on 17-10-10.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Application application();

    ToastHelper toastHelper();

    ServiceApi serviceApi();

    Gson gson();

    PublicParams publicParams();

//    @Named("params")
//    String publicParams();

}
