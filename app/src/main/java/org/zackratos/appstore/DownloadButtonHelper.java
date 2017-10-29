package org.zackratos.appstore;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.zackratos.appstore.app.App;
import org.zackratos.appstore.error.ErrorConsumer;
import org.zackratos.appstore.http.PublicParams;
import org.zackratos.appstore.http.ServiceApi;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.result.DownloadInfo;
import org.zackratos.appstore.utils.AppUtils;
import org.zackratos.appstore.utils.RxUtils;

import java.io.File;
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
 * Created by Administrator on 2017/10/29.
 */

public class DownloadButtonHelper {

    private static final String TAG = "DownloadButtonHelper";
    private DownloadButton button;
    private AppInfo appInfo;

    @Inject
    Application application;

    @Inject
    ServiceApi serviceApi;

    @Inject
    Gson gson;

    @Inject
    PublicParams publicParams;

    @Inject
    SimpleParams simpleParams;

    @Inject
    RxDownload rxDownload;
    


    public DownloadButtonHelper(DownloadButton button, AppInfo appInfo) {
        this.button = button;
        this.appInfo = appInfo;
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
        boolean installed = AppUtils.isInstalled(application, appInfo.getPackageName());
        if (installed) {
            updateInfo();
        } else {
            boolean exist = new File(application.getExternalFilesDir("app"),
                    appInfo.getPackageName() + ".apk").exists();
            if (exist) {
                DLoadStatus newDLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNINSTALL);
                appInfo.setdLoadStatus(newDLoadStatus);
                button.setDLoadStatus(newDLoadStatus);
            } else {
                downloadStatus();
            }
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
                        installApk();
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
        Observable.just(appInfo)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<AppInfo, ObservableSource<BaseResult<List<AppInfo>>>>() {
                    @Override
                    public ObservableSource<BaseResult<List<AppInfo>>> apply(@NonNull AppInfo appInfo) throws Exception {
                        UpdateParams updateParams = new UpdateParams();
                        updateParams.setPublicParams(publicParams);
                        updateParams.setPackageName(appInfo.getPackageName());
                        updateParams.setVersionCode(String.valueOf(AppUtils
                                .getAppVersionCode(application, appInfo.getPackageName())));

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
                        if (appInfos.size() > 0) {
                            dLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNUPDATE);
                        } else {
                            dLoadStatus = new DLoadStatus(DLoadStatus.STATUS_INSTALLED);
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



    private void downloadStatus() {
        Observable.just(appInfo)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<AppInfo, ObservableSource<BaseResult<DownloadInfo>>>() {
                    @Override
                    public ObservableSource<BaseResult<DownloadInfo>> apply(@NonNull AppInfo appInfo) throws Exception {
                        return serviceApi.rxDownload(appInfo.getId(), gson.toJson(simpleParams));
                    }
                })
                .compose(RxUtils.<DownloadInfo>handlerBaseError())
                .flatMap(new Function<DownloadInfo, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(@NonNull DownloadInfo downloadInfo) throws Exception {
                        appInfo.setDownloadInfo(downloadInfo);
                        return rxDownload.receiveDownloadStatus(downloadInfo.getDownloadUrl());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {
                        int status = DLoadStatus.STATUS_UNDOWNLOAD;
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


    private Observable<?> download(String url) {
        return rxDownload.serviceDownload(url, appInfo.getPackageName() + ".apk",
                application.getExternalFilesDir("app").getAbsolutePath());
    }


    private void startDownload() {
        Observable.just(this)
                .subscribeOn(Schedulers.io())
                .compose(this.<DownloadButtonHelper>downloadInfo())
                .flatMap(new Function<DownloadInfo, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull DownloadInfo downloadInfo) throws Exception {
                        return download(downloadInfo.getDownloadUrl());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG, "accept: download");
//                        setStatus();
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {

                    }
                });
    }


    private void pauseDownload() {
        rxDownload.pauseServiceDownload(appInfo.getDownloadInfo().getDownloadUrl())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG, "accept: ");
                        setStatus();
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {

                    }
                });
    }

    private void installApk() {

    }

    private void runApp() {
        AppUtils.runApp(application, appInfo.getPackageName());

    }





    private <T> ObservableTransformer<T, DownloadInfo> downloadInfo() {
        return new ObservableTransformer<T, DownloadInfo>() {
            @Override
            public ObservableSource<DownloadInfo> apply(@NonNull Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<DownloadInfo>>() {
                    @Override
                    public ObservableSource<DownloadInfo> apply(@NonNull T t) throws Exception {
                        DownloadInfo downloadInfo = appInfo.getDownloadInfo();
                        if (downloadInfo == null) {
                            return serviceApi.rxDownload(appInfo.getId(), gson.toJson(simpleParams))
                                    .compose(RxUtils.<DownloadInfo>handlerBaseError());
                        }
                        return Observable.just(downloadInfo);
                    }
                });
            }
        };
    }
    

}
