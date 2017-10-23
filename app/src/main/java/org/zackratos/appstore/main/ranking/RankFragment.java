package org.zackratos.appstore.main.ranking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.zackratos.appstore.R;
import org.zackratos.appstore.base.RefreshFragment;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class RankFragment extends RefreshFragment<RankPresenter> implements RankContract.View {

    private RecyclerView rv;

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
        presenter.topList(0);
    }


    ///////////////////////


    private RankAdapter adapter;

    @Override
    public void setData(List<AppInfo> appInfos) {
        refreshSuccess();
        if (adapter == null) {
            adapter = new RankAdapter(appInfos);
            rv.setAdapter(adapter);
        } else {
            adapter.setNewData(appInfos);
            adapter.notifyDataSetChanged();
        }

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rv);


    }
}
