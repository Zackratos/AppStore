package org.zackratos.appstore;

import android.graphics.drawable.Drawable;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class AndroidAPK {

    private String appName;
    private String appVersionName;
    private String appVersionCode;
    private String packageName;
    private String minSdkVersion;
    private String targetSdkVersion;
    private Drawable drawable;
    private boolean isSystem; // 是否是系统自带的App
    private String apkPath;
    private long lastUpdateTime;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setMinSdkVersion(String minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public void setTargetSdkVersion(String targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAppName() {
        return appName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public String getPackageName() {
        return packageName;
    }
}
