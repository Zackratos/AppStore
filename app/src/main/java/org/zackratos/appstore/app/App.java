package org.zackratos.appstore.app;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.zackratos.appstore.http.HttpModule;


/**
 *
 * Created by zack on 17-10-10.
 */

public class App extends Application{

    private static App application;

    private AppComponent appComponent;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static App getInstance() {
        return application;
    }

    public static RefWatcher getRefWatcher() {
//        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }


    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }


}
