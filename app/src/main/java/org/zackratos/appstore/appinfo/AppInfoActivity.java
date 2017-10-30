package org.zackratos.appstore.appinfo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.zackratos.appstore.R;
import org.zackratos.appstore.SimpleParams;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.utils.RxUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/25.
 */

public class AppInfoActivity extends BaseActivity {

    private static final String KEY_ID = "id";

    private static final String KEY_ICON = "icon";

    private static final String KEY_NAME = "name";

    public static Intent newIntent(Context context, int id, String name, String icon) {
        Intent intent = new Intent(context, AppInfoActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_NAME, name);
        intent.putExtra(KEY_ICON, icon);
        return intent;
    }


    @BindView(R.id.image_1)
    ImageView iconImage;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.recycler_view_1)
    RecyclerView shotRecycler;

    @BindView(R.id.expandable_text_view)
    ExpandableTextView desView;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.update_time)
    TextView timeView;

    @BindView(R.id.last_version)
    TextView versionView;

    @BindView(R.id.app_size)
    TextView sizeView;

    @BindView(R.id.developer)
    TextView devView;

    @BindView(R.id.recycler_view_2)
    RecyclerView otherRecycler;

    @BindView(R.id.recycler_view_3)
    RecyclerView relateRecycler;

    @BindView(R.id.linear_layout_1)
    LinearLayout otherRoot;

    @BindView(R.id.linear_layout_2)
    LinearLayout relateRoot;

    @BindView(R.id.text_error)
    TextView errorView;

    @Inject
    ServiceApi serviceApi;

    @Inject
    SimpleParams simpleParams;

    @Inject
    Gson gson;


    @Override
    protected int getLayout() {
        return R.layout.activity_appinfo;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        setToolbar(toolbar, true);
        shotRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        otherRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        relateRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        shotRecycler.setNestedScrollingEnabled(false);
        otherRecycler.setNestedScrollingEnabled(false);
        relateRecycler.setNestedScrollingEnabled(false);
    }

    private Disposable disposable;

    @Override
    protected void initEventAndData() {
        App.getInstance().getAppComponent().inject(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_ID, 0);
        String icon = intent.getStringExtra(KEY_ICON);
        Glide.with(this)
                .load(String.format("%s%s", Constant.BASE_IMG_URL, icon))
                .into(iconImage);
        collapsingToolbarLayout.setTitle(intent.getStringExtra(KEY_NAME));

        disposable = serviceApi.rxApp(id, gson.toJson(simpleParams))
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<AppInfo>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppInfo>() {
                    @Override
                    public void accept(AppInfo appInfo) throws Exception {
                        updateUI(appInfo);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        scrollView.setVisibility(View.GONE);
                        errorView.setVisibility(View.VISIBLE);
                        errorView.setText(message);
                    }
                });

    }




    private void updateUI(AppInfo appInfo) {
        shotRecycler.setAdapter(new ShotAdapter(AppInfoActivity.this, appInfo.getScreenshot().split(",")));
        desView.setText(appInfo.getIntroduction());
        timeView.setText(new SimpleDateFormat("yyyy - MM - dd").format(new Date(appInfo.getUpdateTime())));
        versionView.setText(appInfo.getVersionName());
        sizeView.setText(String.format("%dMb", appInfo.getApkSize() / 1024 / 1024));
        List<AppInfo> otherApps = appInfo.getSameDevAppInfoList();
        List<AppInfo> relateApps = appInfo.getRelateAppInfoList();
        if (otherApps.isEmpty()) {
            otherRoot.setVisibility(View.GONE);
        } else {
            otherRecycler.setAdapter(new OtherAdapter(otherApps, this));
        }

        if (relateApps.isEmpty()) {
            relateRoot.setVisibility(View.GONE);
        } else {
            relateRecycler.setAdapter(new OtherAdapter(relateApps, this));
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
