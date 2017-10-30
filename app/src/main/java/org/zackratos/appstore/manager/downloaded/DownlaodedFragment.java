package org.zackratos.appstore.manager.downloaded;

import org.zackratos.appstore.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 *
 * Created by Administrator on 2017/10/31.
 */

public class DownlaodedFragment extends BaseFragment {

    @Inject
    RxDownload rxDownload;

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initDataAndEvent() {
        rxDownload.getDownloadRecord("sdf")
                .subscribe(new Consumer<DownloadRecord>() {
                    @Override
                    public void accept(DownloadRecord downloadRecord) throws Exception {

                    }
                });
    }
}
