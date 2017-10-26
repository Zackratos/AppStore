package org.zackratos.appstore.manager.installed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.zackratos.appstore.AndroidAPK;
import org.zackratos.appstore.R;
import org.zackratos.appstore.base.BaseFragment;
import org.zackratos.appstore.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class InstalledFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @Override
    protected int getLayout() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private Disposable disposable;

    private InstalledAdapter adapter;

    @Override
    protected void initDataAndEvent() {
        disposable = Observable.just(this)
                .subscribeOn(Schedulers.io())
                .map(new Function<InstalledFragment, List<AndroidAPK>>() {
                    @Override
                    public List<AndroidAPK> apply(@NonNull InstalledFragment installedFragment) throws Exception {
                        return AppUtils.getInstalledApps(installedFragment.getActivity());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AndroidAPK>>() {
                    @Override
                    public void accept(List<AndroidAPK> androidAPKs) throws Exception {
                        adapter = new InstalledAdapter(androidAPKs);
                        rv.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
