package org.zackratos.appstore.base.applist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.base.RefreshFragment;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.PageBean;

import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/25.
 */

public abstract class AppListFragment<P extends AppListContract.Presenter> extends RefreshFragment<P> implements AppListContract.View {

    private RecyclerView rv;

    @Inject
    ToastHelper toastHelper;

    @Override
    protected int getRealLayout() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onRefresh() {
        presenter.loadFirstPage();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = rootView.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    ///////////////////////


    private AppListAdapter adapter;

    private void initAdapter(List<AppInfo> appInfos) {
        adapter = new AppListAdapter(getActivity(), appInfos, showNumber());

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadMore();
            }
        }, rv);

        rv.setAdapter(adapter);
    }


    protected abstract boolean showNumber();



    @Override
    public void setFirstData(PageBean<AppInfo> pageBean) {
        refreshSuccess();
        if (adapter == null) {
            initAdapter(pageBean.getDatas());
        } else {
            adapter.setNewData(pageBean.getDatas());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setMoreData(PageBean<AppInfo> pageBean) {
        if (adapter == null) {
            return;
        }
        adapter.addData(pageBean.getDatas());
        if (pageBean.isHasMore()) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void loadFirstFail(String message) {
        refreshFail(message);
    }

    @Override
    public void loadMoreFail(String message) {
        adapter.loadMoreFail();
        toastHelper.show(message);
    }


}
