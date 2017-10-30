package org.zackratos.appstore;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
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

public class DownloadButtonHelper2 {

    private static final String TAG = "DownloadButtonHelper2";
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

    @Inject
    ToastHelper toastHelper;
    

    public DownloadButtonHelper2(DownloadButton button, AppInfo appInfo) {
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
            // 如果进来时正在下载，获取下载进度并刷新
            if (dLoadStatus.getStatus() == DLoadStatus.STATUS_DOWNLOADING) {
                downloadStatus();
            }
            return;
        }
        boolean installed = AppUtils.isInstalled(application, appInfo.getPackageName());
        if (installed) {
            updateInfo();
        } else {
            File file = new File(application.getExternalFilesDir("app"),
                    appInfo.getPackageName() + ".apk");
            boolean exist = file.exists();
            if (exist) {
                file.delete();
//                DLoadStatus newDLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNINSTALL);
//                appInfo.setdLoadStatus(newDLoadStatus);
//                button.setDLoadStatus(newDLoadStatus);
            }

            DLoadStatus newDLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNDOWNLOAD);
            appInfo.setdLoadStatus(newDLoadStatus);
            button.setDLoadStatus(newDLoadStatus);
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
//                        installApk();
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



    private String errorMessage;

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
                .compose(this.<AppInfo>downloadUrl())
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
        return rxDownload.serviceDownload(url, appInfo.getPackageName() + ".apk");
    }


    private void startDownload() {
        Observable.just(this)
                .subscribeOn(Schedulers.io())
                .compose(this.<DownloadButtonHelper2>downloadUrl())
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        return download(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        downloadStatus();
                    }
                }, new ErrorConsumer() {
                    @Override
                    public void handlerError(String message) {

                    }
                });
    }



    private void pauseDownload() {
        rxDownload.pauseServiceDownload(appInfo.getDownloadUrl())
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

    private void installApk(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        application.startActivity(intent);

    }

    private void runApp() {
        AppUtils.runApp(application, appInfo.getPackageName());

    }


/*    private <T> ObservableTransformer<T, DownloadInfo> downloadInfo() {
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
    }*/



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
