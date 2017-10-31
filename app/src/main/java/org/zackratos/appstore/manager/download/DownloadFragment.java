package org.zackratos.appstore.manager.download;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.base.BaseFragment;
import org.zackratos.appstore.base.applist.AppListAdapter;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 *
 * Created by Administrator on 2017/10/31.
 */

public class DownloadFragment extends BaseFragment {

    @Inject
    RxDownload rxDownload;

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


    @Override
    protected void initDataAndEvent() {
        App.getInstance().getAppComponent().inject(this);
        disposable = rxDownload.getTotalDownloadRecords()
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<DownloadRecord>, ObservableSource<DownloadRecord>>() {
                    @Override
                    public ObservableSource<DownloadRecord> apply(@NonNull List<DownloadRecord> downloadRecords) throws Exception {
                        return Observable.fromIterable(downloadRecords);
                    }
                })
                .map(new Function<DownloadRecord, AppInfo>() {
                    @Override
                    public AppInfo apply(@NonNull DownloadRecord downloadRecord) throws Exception {
                        AppInfo appInfo = new AppInfo();
                        appInfo.setDisplayName(downloadRecord.getExtra1());
                        appInfo.setBriefShow(downloadRecord.getExtra2());
                        appInfo.setIcon(downloadRecord.getExtra3());
                        appInfo.setId(Integer.valueOf(downloadRecord.getExtra4()));
                        return appInfo;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppInfo>>() {
                    @Override
                    public void accept(List<AppInfo> appInfos) throws Exception {
                        rv.setAdapter(new AppListAdapter(getActivity(), appInfos, false));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    private Disposable disposable;


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
