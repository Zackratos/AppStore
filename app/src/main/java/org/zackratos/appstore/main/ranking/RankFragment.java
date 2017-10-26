package org.zackratos.appstore.main.ranking;

import org.zackratos.appstore.base.applist.AppListFragment;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class RankFragment extends AppListFragment<RankPresenter> {
    @Override
    protected void injectView() {
        component.inject(this);
    }

    @Override
    protected boolean showNumber() {
        return true;
    }

    /*    private RecyclerView rv;

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
        presenter.loadFirstPage();
    }


    ///////////////////////


    private AppListAdapter adapter;

    private void initAdapter(List<AppInfo> appInfos) {
        adapter = new AppListAdapter(appInfos);

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
    }*/

}
