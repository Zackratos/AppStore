package org.zackratos.appstore.category.rank;

import android.os.Bundle;

import org.zackratos.appstore.base.applist.AppListFragment;

/**
 *
 * Created by xiboke on 2017/10/30.
 */

public class CateRankFragment extends AppListFragment<CateRankPresenter> {

    private static final String KEY_ID = "id";

    public static CateRankFragment newInstance(int id) {
        CateRankFragment fragment = new CateRankFragment();
        Bundle arg = new Bundle();
        arg.putInt(KEY_ID, id);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    protected boolean showNumber() {
        return false;
    }

    @Override
    protected void initDataAndEvent() {
        super.initDataAndEvent();
        int id = getArguments().getInt(KEY_ID, 0);
        presenter.setId(id);
    }
}
