package org.zackratos.appstore.main.game;

import com.google.gson.Gson;

import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.base.applist.AppListPresenter;
import org.zackratos.appstore.main.PageParams;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;


import javax.inject.Inject;

import io.reactivex.ObservableSource;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class GamePresenter extends AppListPresenter {

    @Inject
    public GamePresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        super(serviceApi, gson, params);
    }

    @Override
    protected ObservableSource<BaseResult<PageBean<AppInfo>>> getBaseResultObservable(String params) {
        return serviceApi.rxGame(params);
    }
}
