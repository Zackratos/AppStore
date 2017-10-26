package org.zackratos.appstore.main.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.zackratos.appstore.R;
import org.zackratos.appstore.base.RefreshFragment;
import org.zackratos.appstore.category.CategoryActivity;
import org.zackratos.appstore.result.Category;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class CategoryFragment extends RefreshFragment<CategoryPresenter> implements CategoryContract.View {

//    @BindView(R.id.recycler_view)
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
        presenter.category();
    }




    //////////////////////////////////


    private CategoryAdapter adapter;

    private void initAdapter(List<Category> categories) {
        adapter = new CategoryAdapter(categories);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Category category = (Category) adapter.getData().get(position);
                startActivity(CategoryActivity.newIntent(getActivity(), category.getId()));
            }
        });
    }

    @Override
    public void categorySuccess(List<Category> categories) {
        refreshSuccess();
        if (adapter == null) {
            initAdapter(categories);
            rv.setAdapter(adapter);
        } else {
            adapter.setNewData(categories);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void categoryFail(String message) {
        refreshFail(message);
    }
}
