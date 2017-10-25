package org.zackratos.appstore.http;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import org.zackratos.appstore.data.DataHelper;

import java.util.Locale;

/**
 *
 * Created by Administrator on 2017/10/21.
 */

public class PublicParams {

    private String imei;

    private String model;

    private String la;

    private String densityScaleFactor;

    private String resolution;

    private String os;

    private String token;

    private int sdk;

    public PublicParams(Context context, DataHelper dataHelper) {
        this.imei = "5284047f4ffb4e04824a2fd1d1f0cd62";
        this.model = Build.MODEL;
        this.la = Locale.getDefault().getLanguage();
        this.densityScaleFactor = String.valueOf(context
                .getResources().getDisplayMetrics().density);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.resolution = dm.widthPixels + "*" + dm.heightPixels;
        this.os = Build.VERSION.INCREMENTAL;
        this.sdk = Build.VERSION.SDK_INT;
        this.token = dataHelper.getToken();
    }


    private void initParams(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        this.imei = tm != null ? tm.getDeviceId() : null;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
