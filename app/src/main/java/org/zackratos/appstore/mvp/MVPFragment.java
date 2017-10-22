package org.zackratos.appstore.mvp;

import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.base.BaseFragment;

import javax.inject.Inject;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    @Inject
    protected P presenter;

    @Inject
    ToastHelper toastHelper;

    protected PresenterComponent component;


    @Override
    protected void initDataAndEvent() {
        component = DaggerPresenterComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .build();
        injectView();
        presenter.attachView(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void showMsg(String msg) {
        toastHelper.show(msg);
    }

    protected abstract void injectView();


}
