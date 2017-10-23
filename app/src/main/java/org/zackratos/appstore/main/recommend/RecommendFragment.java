package org.zackratos.appstore.main.recommend;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.R;
import org.zackratos.appstore.base.RefreshFragment;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/15.
 */

public class RecommendFragment extends RefreshFragment<RecommendPresenter> implements RecommendContract.View {

    private RecyclerView rv;

    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    protected int getRealLayout() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = rootView.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        presenter.index();
    }


    /////////////////////


    private RecommendAdapter adapter;

    @Override
    public void setData(List<MultiItemEntity> entities) {
        refreshSuccess();
        if (adapter == null) {
            adapter = new RecommendAdapter(entities);
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.text_1:

                            break;
                        case R.id.text_2:
                            break;
                        case R.id.text_3:
                            break;
                        default:
                            break;
                    }
                }
            });
            rv.setAdapter(adapter);
        } else {
            adapter.setNewData(entities);
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void loadFail(@StringRes int messageId) {
        refreshFail(messageId);
    }
}
