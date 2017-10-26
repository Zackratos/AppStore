package org.zackratos.appstore.main.game;


import org.zackratos.appstore.base.applist.AppListFragment;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class GameFragment extends AppListFragment<GamePresenter> {

    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    protected boolean showNumber() {
        return false;
    }
}
