package org.zackratos.appstore.http;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.Locale;

import io.reactivex.functions.Consumer;

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

    private int sdk;

    public PublicParams(final Context context) {
        this.imei = "5284047f4ffb4e04824a2fd1d1f0cd62";
        this.model = Build.MODEL;
        this.la = Locale.getDefault().getLanguage();
        this.densityScaleFactor = String.valueOf(context
                .getResources().getDisplayMetrics().density);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.resolution = dm.widthPixels + "*" + dm.heightPixels;
        this.os = Build.VERSION.INCREMENTAL;
        this.sdk = Build.VERSION.SDK_INT;


        RxPermissions.getInstance(context).request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            initParams(context);
                        } else {
                            imei = "5284047f4ffb4e04824a2fd1d1f0cd62";
                        }
                    }
                });
    }


    private void initParams(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        this.imei = tm != null ? tm.getDeviceId() : null;
    }

}
