package org.zackratos.appstore;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.zackratos.appstore.app.App;
import org.zackratos.appstore.app.ToastHelper;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.result.DownloadInfo;
import org.zackratos.appstore.utils.AppUtils;
import org.zackratos.appstore.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 *
 * Created by xiboke on 2017/10/30.
 */

public class DownloadHelper {

    private AppInfo appInfo;
    private DownloadButton button;

    private String fileName;

    @Inject
    RxDownload rxDownload;

    @Inject
    ServiceApi serviceApi;

    @Inject
    Gson gson;

    @Inject
    SimpleParams simpleParams;

    @Inject
    PublicParams publicParams;

//    @Inject
//    Application application;

    private Context context;

    @Inject
    ToastHelper toastHelper;



    public DownloadHelper(Context context, AppInfo appInfo, DownloadButton button) {
        this.appInfo = appInfo;
        this.button = button;
        this.context = context;
        fileName = appInfo.getPackageName() + ".apk";
        App.getInstance().getAppComponent().inject(this);
    }


    public void handleButton() {
        setStatus();
        setClick();
    }


    private void setStatus() {
        DLoadStatus dLoadStatus = appInfo.getdLoadStatus();
        if (dLoadStatus != null) {
            button.setDLoadStatus(dLoadStatus);
            return;
        }
        boolean installed = AppUtils.isInstalled(context, appInfo.getPackageName());
        if (installed) {
            updateInfo();
        } else {
            DLoadStatus newDLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNDOWNLOAD);
            appInfo.setdLoadStatus(newDLoadStatus);
            button.setDLoadStatus(newDLoadStatus);
            downloadStatus();
        }
    }


    private void setClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLoadStatus dLoadStatus = appInfo.getdLoadStatus();
                if (dLoadStatus == null) {
                    return;
                }
                switch (dLoadStatus.getStatus()) {
                    case DLoadStatus.STATUS_UNDOWNLOAD:
                        startDownload();
                        break;
                    case DLoadStatus.STATUS_UNUPDATE:
                        startDownload();
                        break;
                    case DLoadStatus.STATUS_DOWNLOADING:
                        pauseDownload();
                        break;
                    case DLoadStatus.STATUS_PAUSE:
                        startDownload();
                        break;
                    case DLoadStatus.STATUS_UNINSTALL:
                        break;
                    case DLoadStatus.STATUS_INSTALLED:
                        runApp();
                        break;
                    default:
                        startDownload();
                        break;
                }
            }
        });

    }


    private void updateInfo() {
        Observable.just(this)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<DownloadHelper, ObservableSource<BaseResult<List<AppInfo>>>>() {
                    @Override
                    public ObservableSource<BaseResult<List<AppInfo>>> apply(@NonNull DownloadHelper helper) throws Exception {
                        UpdateParams updateParams = new UpdateParams();
                        updateParams.setPublicParams(publicParams);
                        updateParams.setPackageName(appInfo.getPackageName());
                        updateParams.setVersionCode(String.valueOf(AppUtils
                                .getAppVersionCode(context, appInfo.getPackageName())));

                        String params = gson.toJson(updateParams);
                        return serviceApi.rxUdateInfo(params);
                    }
                })
                .compose(RxUtils.<List<AppInfo>>handlerBaseError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppInfo>>() {
                    @Override
                    public void accept(List<AppInfo> appInfos) throws Exception {
                        DLoadStatus dLoadStatus;
                        if (appInfos.isEmpty()) {
                            dLoadStatus = new DLoadStatus(DLoadStatus.STATUS_INSTALLED);
                        } else {
                            dLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNUPDATE);
                        }
                        appInfo.setdLoadStatus(dLoadStatus);
                        button.setDLoadStatus(dLoadStatus);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {

                    }
                });
    }


    private void startDownload() {
        Observable.just(this)
                .subscribeOn(Schedulers.io())
                .compose(this.<DownloadHelper>downloadUrl())
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        return rxDownload.serviceDownload(s, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {
                        toastHelper.show(message);
                    }
                });
    }


    private void pauseDownload() {
        rxDownload.pauseServiceDownload(appInfo.getDownloadUrl()).subscribe();
    }



    private void downloadStatus() {
        Observable.just(this)
                .subscribeOn(Schedulers.io())
                .compose(this.<DownloadHelper>downloadUrl())
                .flatMap(new Function<String, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull String s) throws Exception {
                        return rxDownload.receiveDownloadStatus(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {
                        int status = DLoadStatus.STATUS_UNDOWNLOAD;
                        Log.d(TAG, "accept: " + downloadEvent.getFlag());
                        switch (downloadEvent.getFlag()) {
                            case DownloadFlag.NORMAL:
                            case DownloadFlag.WAITING:
                            case DownloadFlag.CANCELED:
                            case DownloadFlag.FAILED:
                                status = DLoadStatus.STATUS_UNDOWNLOAD;
                                break;
                            case DownloadFlag.STARTED:
                                status = DLoadStatus.STATUS_DOWNLOADING;
                                break;
                            case DownloadFlag.PAUSED:
                                status = DLoadStatus.STATUS_PAUSE;
                                break;
                            case DownloadFlag.COMPLETED:
                                status = DLoadStatus.STATUS_UNINSTALL;
                                installApp();
                                break;
                            default:
                                break;
                        }
                        DLoadStatus dLoadStatus = new DLoadStatus(status);
                        DownloadStatus downloadStatus = downloadEvent.getDownloadStatus();
                        float downloadSize = downloadStatus.getDownloadSize();
                        float totalSize = downloadStatus.getTotalSize();
                        float percent = totalSize == 0 ? 0 : downloadSize / totalSize * 100;
                        dLoadStatus.setProgress(percent);
                        appInfo.setdLoadStatus(dLoadStatus);
                        button.setDLoadStatus(dLoadStatus);
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {

                    }
                });

    }


    private void runApp() {
        AppUtils.runApp(context, appInfo.getPackageName());
    }


    private void installApp() {
        AppUtils.installNormal(context, context.getExternalFilesDir("app")
                .getAbsolutePath() + "/" + fileName);
    }


    private static final String TAG = "DownloadHelper";


    private <T> ObservableTransformer<T, String> downloadUrl() {
        return new ObservableTransformer<T, String>() {
            @Override
            public ObservableSource<String> apply(@NonNull Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull T t) throws Exception {
                        final String downloadUrl = appInfo.getDownloadUrl();
                        if (downloadUrl == null) {
                            return serviceApi.rxDownload(appInfo.getId(), gson.toJson(simpleParams))
                                    .compose(RxUtils.<DownloadInfo>handlerBaseError())
                                    .flatMap(new Function<DownloadInfo, ObservableSource<String>>() {
                                        @Override
                                        public ObservableSource<String> apply(@NonNull DownloadInfo downloadInfo) throws Exception {
                                            String url = downloadInfo.getDownloadUrl();
                                            appInfo.setDownloadUrl(url);
                                            return Observable.just(url);
                                        }
                                    });
                        }
                        return Observable.just(downloadUrl);
                    }
                });
            }
        };
    }

}
