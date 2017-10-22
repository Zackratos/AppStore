package org.zackratos.appstore.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;


import org.zackratos.appstore.R;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.main.recommend.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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
    }

    @Override
    protected void initEventAndData() {

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
        fragmentInfos.add(new FragmentInfo(new RecommendFragment(), getString(R.string.main_ranking)));
        fragmentInfos.add(new FragmentInfo(new RecommendFragment(), getString(R.string.main_game)));
        fragmentInfos.add(new FragmentInfo(new RecommendFragment(), getString(R.string.main_category)));
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



    private void test(List<String[]> data) {
        Observable.fromIterable(data)
                .flatMap(new Function<String[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String[] strings) throws Exception {
                        return Observable.fromArray(strings);
                    }
                })
                .toList()
                .map(new Function<List<String>, String[]>() {
                    @Override
                    public String[] apply(@NonNull List<String> strings) throws Exception {
                        return new String[0];
                    }
                });

    }
}