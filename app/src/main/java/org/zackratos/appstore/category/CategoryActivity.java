package org.zackratos.appstore.category;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.zackratos.appstore.R;
import org.zackratos.appstore.base.BaseActivity;
import org.zackratos.appstore.category.fine.FineFragment;
import org.zackratos.appstore.category.news.NewsFragment;
import org.zackratos.appstore.category.rank.CateRankFragment;
import org.zackratos.appstore.main.FragmentAdapter;
import org.zackratos.appstore.main.FragmentInfo;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class CategoryActivity extends BaseActivity {

    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    public static Intent newIntent(Context context, int id, String name) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_NAME, name);
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
        int color = ContextCompat.getColor(this, R.color.main);
        new UltimateBar(this).setColorBar(color, color);
        super.onViewCreated(savedInstanceState);
        setToolbar(toolbar, true);
        setTitle(getIntent().getStringExtra(KEY_NAME));
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initEventAndData() {

    }



    private void initViewPager() {
        int id = getIntent().getIntExtra(KEY_ID, 0);
        List<FragmentInfo> fragmentInfos = new ArrayList<>();
        fragmentInfos.add(new FragmentInfo(FineFragment.newInstance(id), getString(R.string.cate_fine)));
        fragmentInfos.add(new FragmentInfo(CateRankFragment.newInstance(id), getString(R.string.cate_rank)));
        fragmentInfos.add(new FragmentInfo(NewsFragment.newInstance(id), getString(R.string.cate_news)));
        viewPager.setOffscreenPageLimit(fragmentInfos.size());
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentInfos));
    }
}
