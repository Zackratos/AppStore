package org.zackratos.appstore.category.news;

import com.google.gson.Gson;

import org.zackratos.appstore.base.applist.AppListPresenter;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.main.PageParams;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.result.PageBean;

import javax.inject.Inject;

import io.reactivex.ObservableSource;

/**
 *
 * Created by xiboke on 2017/10/30.
 */

public class NewsPresenter extends AppListPresenter {

    private int id;

    @Inject
    public NewsPresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        super(serviceApi, gson, params);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected ObservableSource<BaseResult<PageBean<AppInfo>>> getBaseResultObservable(String params) {
        return serviceApi.rxNewlist(id, params);
    }
}
