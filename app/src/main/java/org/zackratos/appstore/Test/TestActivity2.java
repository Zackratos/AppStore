package org.zackratos.appstore.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.zackratos.appstore.R;
import org.zackratos.appstore.main.FragmentAdapter;
import org.zackratos.appstore.main.FragmentInfo;
import org.zackratos.appstore.main.recommend.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public class TestActivity2 extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
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
}
