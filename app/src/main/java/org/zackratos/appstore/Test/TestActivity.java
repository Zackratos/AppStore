package org.zackratos.appstore.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.zackratos.appstore.DownloadButton;
import org.zackratos.appstore.DownloadHelper;
import org.zackratos.appstore.R;
import org.zackratos.appstore.SimpleParams;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.IndexData;
import org.zackratos.appstore.utils.RxUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.download_button)
    DownloadButton button;

    @BindView(R.id.image_1)
    ImageView iconView;

    @BindView(R.id.text_1)
    TextView titleView;

    @Inject
    ServiceApi serviceApi;

    @Inject
    SimpleParams simpleParams;

    @Inject
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        App.getInstance().getAppComponent().inject(this);
        init();
    }


    private void init() {

        serviceApi.rxApp(64151, gson.toJson(simpleParams))
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<AppInfo>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppInfo>() {
                    @Override
                    public void accept(AppInfo appInfo) throws Exception {
                        Glide.with(TestActivity.this).load(Constant.BASE_IMG_URL + appInfo.getIcon()).into(iconView);
                        titleView.setText(appInfo.getDisplayName());
                        new DownloadHelper(TestActivity.this, appInfo, button).handleButton();
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        Log.d(TAG, "handlerError: ");
                    }
                });
    }


    private static final String TAG = "TestActivity";
}
