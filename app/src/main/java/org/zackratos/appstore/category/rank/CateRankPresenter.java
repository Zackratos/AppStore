package org.zackratos.appstore.category.rank;

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

public class CateRankPresenter extends AppListPresenter {

    private int id;

    @Inject
    public CateRankPresenter(ServiceApi serviceApi, Gson gson, PageParams params) {
        super(serviceApi, gson, params);
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected ObservableSource<BaseResult<PageBean<AppInfo>>> getBaseResultObservable(String params) {
        return serviceApi.rxTopList(id, params);
    }
}
