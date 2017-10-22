package org.zackratos.appstore.mvp;


/**
 *
 * Created by Administrator on 2017/9/17.
 */

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();



}
