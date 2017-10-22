package org.zackratos.appstore.Test;

import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.BaseView;

/**
 * Created by Administrator on 2017/10/15.
 */

public interface TestContract {

    interface View extends BaseView {
        void viewTest();
    }

    interface Presenter extends BasePresenter<View> {
        void test();
    }
}
