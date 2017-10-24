package org.zackratos.appstore.app;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.zackratos.appstore.http.HttpModule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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





    private void test(String[][] datass) {
        List<List<String>> listss = new ArrayList<>();
        for (String[] datas : datass) {
            List<String> lists = new ArrayList<>();
            for (String data : datas) {
                lists.add(data);
            }
            listss.add(lists);
        }
    }



    private void rxTest(String[][] datass) {
        Observable.just(datass)
                .flatMap(new Function<String[][], ObservableSource<String[]>>() {
                    @Override
                    public ObservableSource<String[]> apply(@NonNull String[][] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                })
                .map(new Function<String[], List<String>>() {
                    @Override
                    public List<String> apply(@NonNull String[] strings) throws Exception {
                        List<String> lists = new ArrayList<>();
                        for (String data : strings) {
                            lists.add(data);
                        }
                        return lists;
                    }
                })
                .toList()
                .subscribe(new Consumer<List<List<String>>>() {
                    @Override
                    public void accept(List<List<String>> lists) throws Exception {

                    }
                });
    }

}
