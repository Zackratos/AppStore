package org.zackratos.appstore.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * Created by Administrator on 2017/9/17.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    private boolean visible;

    private boolean created;

    private boolean first;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        created = true;
//        initDataAndEvent();
        lazyLoad();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        first = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        RefWatcher refWatcher = App.getRefWatcher();
//        refWatcher.watch(this);
    }

    protected abstract @LayoutRes int getLayout();

    protected abstract void initDataAndEvent();

    private void lazyLoad() {
        if (created && visible && first) {
            initDataAndEvent();
            first = false;
        }
    }


    private static final String TAG = "BaseFragment";


}
