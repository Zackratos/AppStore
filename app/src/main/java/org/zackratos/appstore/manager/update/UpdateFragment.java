package org.zackratos.appstore.manager.update;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import org.zackratos.appstore.common.AndroidAPK;
import org.zackratos.appstore.R;
import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.base.BaseFragment;
import org.zackratos.appstore.base.applist.AppListAdapter;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.utils.AppUtils;
import org.zackratos.appstore.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/31.
 */

public class UpdateFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @Inject
    ServiceApi serviceApi;

    @Inject
    Gson gson;

    @Inject
    PublicParams publicParams;

    @Inject
    ToastHelper toastHelper;


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
        disposable = Observable.create(new ObservableOnSubscribe<List<AndroidAPK>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AndroidAPK>> e) throws Exception {
                e.onNext(AppUtils.getInstalledApps(getActivity()));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<List<AndroidAPK>, String>() {
                    @Override
                    public String apply(@NonNull List<AndroidAPK> androidAPKs) throws Exception {
                        UpdateParams updateParams = new UpdateParams();
                        updateParams.setPublicParams(publicParams);
                        StringBuilder name = new StringBuilder();
                        StringBuilder version = new StringBuilder();
                        for (int i = 0; i < androidAPKs.size(); i++) {
                            AndroidAPK androidAPK = androidAPKs.get(i);
                            if (i == 0) {
                                name.append(androidAPK.getPackageName());
                                version.append(androidAPK.getAppVersionCode());
                            } else {
                                name.append(",").append(androidAPK.getPackageName());
                                version.append(",").append(androidAPK.getAppVersionCode());
                            }
                        }
                        updateParams.setPackageName(name.toString());
                        updateParams.setVersionCode(version.toString());
                        return gson.toJson(updateParams);
                    }
                })
                .flatMap(new Function<String, ObservableSource<BaseResult<List<AppInfo>>>>() {
                    @Override
                    public ObservableSource<BaseResult<List<AppInfo>>> apply(@NonNull String s) throws Exception {
                        return serviceApi.rxUpdateInfo(s);
                    }
                })
                .compose(RxUtils.<List<AppInfo>>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppInfo>>() {
                    @Override
                    public void accept(List<AppInfo> appInfos) throws Exception {
                        rv.setAdapter(new AppListAdapter(getActivity(), appInfos, false));
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        toastHelper.show(message);
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
