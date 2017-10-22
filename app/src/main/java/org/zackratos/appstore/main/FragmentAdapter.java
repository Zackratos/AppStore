package org.zackratos.appstore.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<FragmentInfo> fragmentInfos;

    public FragmentAdapter(FragmentManager fm, List<FragmentInfo> fragmentInfos) {
        super(fm);
        this.fragmentInfos = fragmentInfos;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentInfos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentInfos.get(position).getTitle();
    }
}
