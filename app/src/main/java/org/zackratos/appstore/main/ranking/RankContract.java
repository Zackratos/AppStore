package org.zackratos.appstore.main.ranking;

import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public interface RankContract {

    interface View extends BaseView {
        void setData(List<AppInfo> appInfos);
    }

    interface Presenter extends BasePresenter<View> {
        void topList(int page);
    }

}
