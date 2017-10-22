package org.zackratos.appstore.mvp;

import android.widget.Toast;

import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.base.BaseActivity;

import javax.inject.Inject;

/**
 *
 * Created by Administrator on 2017/9/17.
 */

public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    @Inject
    protected P presenter;

    @Inject
    protected ToastHelper toastHelper;

    protected PresenterComponent component;

    @Override
    protected void initEventAndData() {
        component = DaggerPresenterComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .build();
        injectView();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected abstract void injectView();
}
