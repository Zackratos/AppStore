package org.zackratos.appstore.app;

import android.app.Application;

import com.google.gson.Gson;

import org.zackratos.appstore.common.DownloadHelper;
import org.zackratos.appstore.Test.TestActivity;
import org.zackratos.appstore.appinfo.AppInfoActivity;
import org.zackratos.appstore.data.DataModule;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.HttpModule;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.login.LoginActivity;
import org.zackratos.appstore.main.MainActivity;
import org.zackratos.appstore.manager.download.DownloadFragment;
import org.zackratos.appstore.manager.update.UpdateFragment;

import javax.inject.Singleton;

import dagger.Component;
import zlc.season.rxdownload2.RxDownload;

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

    RxDownload rxDownload();

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(ErrorConsumer consumer);

//    void inject(DownloadButtonHelper2 helper);

    void inject(DownloadHelper helper);

    void inject(AppInfoActivity activity);

    void inject(TestActivity activity);

    void inject(DownloadFragment fragment);

    void inject(UpdateFragment fragment);


}
