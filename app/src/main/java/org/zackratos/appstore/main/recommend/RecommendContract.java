package org.zackratos.appstore.main.recommend;

import android.support.annotation.StringRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public interface RecommendContract {

    interface View extends BaseView {
        void setData(List<MultiItemEntity> entities);
        void loadFail(String message);
        void downloadError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void index();

        void download(AppInfo appInfo);
    }
}
