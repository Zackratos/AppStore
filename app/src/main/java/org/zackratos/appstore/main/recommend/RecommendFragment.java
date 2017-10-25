package org.zackratos.appstore.main.recommend;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SquaringDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.R;
import org.zackratos.appstore.RxBus;
import org.zackratos.appstore.appinfo.AppInfoActivity;
import org.zackratos.appstore.base.RefreshFragment;
import org.zackratos.appstore.result.AppInfo;

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

    private void initAdapter(List<MultiItemEntity> entities) {
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItemViewType(position) == RecommendAdapter.APP) {
                    AppInfo appInfo = (AppInfo) adapter.getItem(position);
                    ImageView iconImage = view.findViewById(R.id.image_1);
//                    iconImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = iconImage.getDrawingCache();
                    Intent intent = AppInfoActivity.newIntent(getActivity(), appInfo);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                                (getActivity(), iconImage, "share").toBundle());
                    } else {
                        startActivity(intent);
                    }
//                    iconImage.setDrawingCacheEnabled(false);
                }
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    public void setData(List<MultiItemEntity> entities) {
        refreshSuccess();
        if (adapter == null) {
            initAdapter(entities);
        } else {
            adapter.setNewData(entities);
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void loadFail(String message) {
        refreshFail(message);
    }
}
