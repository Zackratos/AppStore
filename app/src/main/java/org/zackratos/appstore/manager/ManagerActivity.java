package org.zackratos.appstore.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.zackratos.appstore.R;
import org.zackratos.appstore.Test.TestFragment;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.main.FragmentAdapter;
import org.zackratos.appstore.main.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class ManagerActivity extends BaseActivity {

    private static final String KEY_POSITION = "position";

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, ManagerActivity.class);
        intent.putExtra(KEY_POSITION, position);
        return intent;
    }


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @Override
    protected int getLayout() {
        return R.layout.app_bar_main;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        setToolbar(toolbar, true);
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initEventAndData() {

    }


    private void initViewPager() {
        List<FragmentInfo> fragmentInfos = new ArrayList<>();
        fragmentInfos.add(new FragmentInfo(new TestFragment(), getString(R.string.main_recommend)));
        fragmentInfos.add(new FragmentInfo(new TestFragment(), getString(R.string.main_ranking)));
        fragmentInfos.add(new FragmentInfo(new TestFragment(), getString(R.string.main_game)));
        fragmentInfos.add(new FragmentInfo(new TestFragment(), getString(R.string.main_category)));
        viewPager.setOffscreenPageLimit(fragmentInfos.size());
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentInfos));
        int position = getIntent().getIntExtra(KEY_POSITION, 0);
        viewPager.setCurrentItem(position);
    }
}
