package org.zackratos.appstore;

/**
 *
 * Created by Administrator on 2017/10/29.
 */

public class DLoadStatus {

    public static final int STATUS_UNDOWNLOAD = 1;
    public static final int STATUS_UNUPDATE = 2;
    public static final int STATUS_DOWNLOADING = 3;
    public static final int STATUS_PAUSE = 4;
    public static final int STATUS_UNINSTALL = 5;
    public static final int STATUS_INSTALLING = 6;
    public static final int STATUS_INSTALLED = 7;


    public DLoadStatus(int status) {
        this.status = status;
        if (status != STATUS_DOWNLOADING) {
            progress = 0;
        }
    }

    private int status;

    private float progress;

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public float getProgress() {
        return progress;
    }
}
