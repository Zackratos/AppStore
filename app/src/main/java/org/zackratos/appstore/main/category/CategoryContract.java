package org.zackratos.appstore.main.category;

import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;
import org.zackratos.appstore.result.Category;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public interface CategoryContract {

    interface View extends BaseView {
        void categorySuccess(List<Category> categories);
        void categoryFail(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void category();
    }

}

