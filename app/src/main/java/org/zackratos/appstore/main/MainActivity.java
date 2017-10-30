package org.zackratos.appstore.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.appstore.R;
import org.zackratos.appstore.RxBus;
import org.zackratos.appstore.Test.TestActivity;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.data.DataHelper;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.login.LoginActivity;
import org.zackratos.appstore.main.game.GameFragment;
import org.zackratos.appstore.main.ranking.RankFragment;
import org.zackratos.appstore.main.category.CategoryFragment;
import org.zackratos.appstore.main.recommend.RecommendFragment;
import org.zackratos.appstore.manager.ManagerActivity;
import org.zackratos.appstore.result.UserInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private View headerView;

    private TextView nameView;

    private ImageView userIcon;

    private FrameLayout userView;

    @Inject
    DataHelper dataHelper;

    @Inject
    PublicParams publicParams;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        setToolbar(toolbar, false);
        initDrawerLayout();
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
        headerView = navigationView.getHeaderView(0);
        nameView = headerView.findViewById(R.id.text_1);
        userIcon = headerView.findViewById(R.id.image_1);
        headerView.findViewById(R.id.frame_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                int position = 0;
                if (id == R.id.nav_update) {
                    startActivity(new Intent(MainActivity.this, TestActivity.class));
                    return true;
                } else if (id == R.id.nav_download) {

                } else if (id == R.id.nav_uninstall) {

                } else if (id == R.id.nav_setup) {

                } else if (id == R.id.nav_sign_up){
                    dataHelper.clearUserInfo();
                    dataHelper.clearToken();
                    publicParams.setToken("");
                    nameView.setText(R.string.login_name);
                    return true;
                }
                Intent intent = ManagerActivity.newIntent(MainActivity.this, position);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void initEventAndData() {
        App.getInstance().getAppComponent().inject(this);
        UserInfo userInfo = dataHelper.getUserInfo();
        if (userInfo != null) {
            nameView.setText(userInfo.getUsername());
        }
        registerLoginEvent();
    }

    private Disposable disposable;

    private void registerLoginEvent() {
        disposable = RxBus.getDefault().toObserbable(UserInfo.class)
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        nameView.setText(userInfo.getUsername());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void initViewPager() {
        List<FragmentInfo> fragmentInfos = new ArrayList<>();
        fragmentInfos.add(new FragmentInfo(new RecommendFragment(), getString(R.string.main_recommend)));
        fragmentInfos.add(new FragmentInfo(new RankFragment(), getString(R.string.main_ranking)));
        fragmentInfos.add(new FragmentInfo(new GameFragment(), getString(R.string.main_game)));
        fragmentInfos.add(new FragmentInfo(new CategoryFragment(), getString(R.string.main_category)));
        viewPager.setOffscreenPageLimit(fragmentInfos.size());
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentInfos));
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
