package org.zackratos.appstore.app;

import android.app.Application;

import com.google.gson.Gson;

import org.zackratos.appstore.data.DataHelper;
import org.zackratos.appstore.data.DataModule;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.http.HttpModule;
import org.zackratos.appstore.login.LoginActivity;
import org.zackratos.appstore.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * Created by zack on 17-10-10.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, DataModule.class})
public interface AppComponent {

    Application application();

    ToastHelper toastHelper();

    ServiceApi serviceApi();

    Gson gson();

    PublicParams publicParams();

    DataHelper dataHelper();

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

//    @Named("params")
//    String publicParams();

}
