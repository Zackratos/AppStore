package org.zackratos.appstore.category.fine;

import android.os.Bundle;

import org.zackratos.appstore.base.applist.AppListFragment;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class FineFragment extends AppListFragment<FinePresenter> {

    private static final String KEY_ID = "id";

    public static FineFragment newInstance(int id) {
        FineFragment fragment = new FineFragment();
        Bundle arg = new Bundle();
        arg.putInt(KEY_ID, id);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected void initDataAndEvent() {
        super.initDataAndEvent();
        int id = getArguments().getInt(KEY_ID, 0);
        presenter.setId(id);
    }

    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    protected boolean showNumber() {
        return false;
    }
}
