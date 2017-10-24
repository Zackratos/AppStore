package org.zackratos.appstore.main.ranking;

import android.support.annotation.StringRes;

import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public interface RankContract {

    interface View extends BaseView {
        void setFirstData(PageBean<AppInfo> pageBean);
        void setMoreData(PageBean<AppInfo> pageBean);
        void loadFirstFail(@StringRes int messageId);
        void loadMoreFail(@StringRes int messageId);
    }

    interface Presenter extends BasePresenter<View> {

        void loadFirstPager();
        void loadMore();
    }

}
