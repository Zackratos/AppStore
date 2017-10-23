package org.zackratos.appstore.main.ranking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
 * Created by xiboke on 2017/10/23.
 */

public class RankFragment extends RefreshFragment<RankPresenter> implements RankContract.View {

    private RecyclerView rv;

    @Inject
    ToastHelper toastHelper;

    @Override
    protected int getRealLayout() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = rootView.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        presenter.loadFirstPager();
    }


    ///////////////////////


    private RankAdapter adapter;

    private void initAdapter(List<AppInfo> appInfos) {
        adapter = new RankAdapter(appInfos);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadMore();
            }
        }, rv);

        rv.setAdapter(adapter);
    }

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
    public void loadFirstFail(@StringRes int messageId) {
        refreshFail(messageId);
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
    public void loadMoreFail(@StringRes int messageId) {
        adapter.loadMoreFail();
        toastHelper.show(messageId);
    }
}
