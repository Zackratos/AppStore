package org.zackratos.appstore.appinfo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.zackratos.appstore.R;
import org.zackratos.appstore.SimpleParams;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by xiboke on 2017/10/25.
 */

public class AppInfoActivity extends BaseActivity {

    private static final String KEY_APP = "app_info";

    private static final String KEY_ICON = "icon";

    public static Intent newIntent(Context context, AppInfo appInfo) {
        Intent intent = new Intent(context, AppInfoActivity.class);
        intent.putExtra(KEY_APP, appInfo);
//        intent.putExtra(KEY_ICON, bitmap);
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
        shotRecycler.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));
        shotRecycler.setNestedScrollingEnabled(false);
    }


    @Override
    protected void initEventAndData() {
//        App.getInstance().getAppComponent().inject(this);
        Intent intent = getIntent();
        AppInfo appInfo = intent.getParcelableExtra(KEY_APP);
        Glide.with(this)
                .load(String.format("%s%s", Constant.BASE_IMG_URL, appInfo.getIcon()))
                .into(iconImage);
        String[] screenShot = appInfo.getScreenshot().split(",");
        shotRecycler.setAdapter(new ShotAdapter(this, screenShot));
        collapsingToolbarLayout.setTitle(appInfo.getDisplayName());

/*        serviceApi.app(appInfo.getId(), gson.toJson(simpleParams))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                        try {
                            String sdf = body.string();
                            Log.d("TAG", "onResponse: " + sdf);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });*/

    }
}
