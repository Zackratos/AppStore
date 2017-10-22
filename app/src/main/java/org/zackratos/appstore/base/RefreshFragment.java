package org.zackratos.appstore.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.zackratos.appstore.R;
import org.zackratos.appstore.mvp.BasePresenter;
import org.zackratos.appstore.mvp.MVPFragment;

import butterknife.BindView;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public abstract class RefreshFragment<P extends BasePresenter> extends MVPFragment<P> implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "RefreshFragment";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.content)
    FrameLayout contentView;

    @BindView(R.id.error_msg)
    TextView msgView;

    protected View rootView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_refresh;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setColorSchemeResources(R.color.main);
        rootView = LayoutInflater.from(getActivity()).inflate(getRealLayout(), contentView, true);
    }

    @Override
    protected void initDataAndEvent() {
        super.initDataAndEvent();
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();

    }


    @Override
    public void onDestroyView() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }
        super.onDestroyView();
    }

    private void refreshOver() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    protected void refreshSuccess() {
        refreshOver();
        contentView.setVisibility(View.VISIBLE);
        msgView.setVisibility(View.GONE);
    }

    protected void refreshFail(@StringRes int messageId) {
        refreshOver();
        contentView.setVisibility(View.GONE);
        msgView.setVisibility(View.VISIBLE);
        msgView.setText(messageId);
    }

    @LayoutRes
    protected abstract int getRealLayout();

}
