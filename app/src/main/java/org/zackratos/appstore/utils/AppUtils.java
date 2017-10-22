package org.zackratos.appstore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.zackratos.appstore.app.App;

import java.util.Locale;

/**
 *
 * Created by Administrator on 2017/10/21.
 */

public class AppUtils {

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) App.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getINCREMENTAL() {
        return Build.VERSION.INCREMENTAL;
    }

    public static int getScreenW() {
        return App.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenH() {
        return App.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getSDKINT() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDensityScaleFactor() {
        return String.valueOf(App.getInstance().getResources().getDisplayMetrics().density);
    }


}
