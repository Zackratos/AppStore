package org.zackratos.appstore.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;

import org.zackratos.appstore.R;
import org.zackratos.appstore.common.RxBus;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.data.DataHelper;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.LoginResult;
import org.zackratos.appstore.result.UserInfo;
import org.zackratos.appstore.utils.RxUtils;
import org.zackratos.appstore.utils.StringUtils;
import org.zackratos.ultimatebar.UltimateBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tel_num)
    AutoCompleteTextView telView;

    @BindView(R.id.password)
    AutoCompleteTextView passView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.sign_in_button)
    void onLoginClick() {
        login();
    }

    @Inject
    ToastHelper toastHelper;

    @Inject
    ServiceApi serviceApi;

    @Inject
    LoginParams loginParams;

    @Inject
    DataHelper dataHelper;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        setToolbar(toolbar, true);
        int color = ContextCompat.getColor(this, R.color.main);
        new UltimateBar(this).setColorBar(color, color);
    }

    @Override
    protected void initEventAndData() {
        App.getInstance().getAppComponent().inject(this);
    }


    private Disposable disposable;

    private void login() {
        String telNumber = telView.getText().toString();
        String password = passView.getText().toString();
        if (!StringUtils.isMobile(telNumber)) {
            telView.setError(getString(R.string.login_tel_error));
            return;
        }
        if (password.length() < 6) {
            passView.setError(getString(R.string.login_password_short));
            return;
        }
        loginParams.setEmail(telNumber);
        loginParams.setPassword(password);
        disposable = serviceApi.rxLogin(loginParams)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<LoginResult>handlerBaseError())
                .map(new Function<LoginResult, UserInfo>() {
                    @Override
                    public UserInfo apply(@NonNull LoginResult loginResult) throws Exception {
                        UserInfo userInfo = loginResult.getUser();
                        dataHelper.putToken(loginResult.getToken());
                        dataHelper.putUserInfo(userInfo);
                        loginParams.getPublicParams().setToken(loginResult.getToken());
                        return userInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        dismissProgressDialog();
                        RxBus.getDefault().post(userInfo);
                        finish();
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        dismissProgressDialog();
                        toastHelper.show(message);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showProgressDialog();
                    }
                });

    }


    private ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(R.string.login_load_title);
            progressDialog.setMessage(getString(R.string.login_load_content));
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private static final String TAG = "LoginActivity";
}
