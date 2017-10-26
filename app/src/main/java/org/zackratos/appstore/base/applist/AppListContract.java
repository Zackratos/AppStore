package org.zackratos.appstore.base.applist;


import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public interface AppListContract {

    interface View extends BaseView {
        void setFirstData(PageBean<AppInfo> pageBean);
        void setMoreData(PageBean<AppInfo> pageBean);
        void loadFirstFail(String message);
        void loadMoreFail(String message);
    }

    interface Presenter extends BasePresenter<View> {

        void loadFirstPage();
        void loadMore();
    }

}
