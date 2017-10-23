package org.zackratos.appstore.http;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

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

    private int sdk;

    public PublicParams(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
//        this.imei = tm != null ? tm.getDeviceId() : null;
        this.model = Build.MODEL;
        this.la = Locale.getDefault().getLanguage();
        this.densityScaleFactor = String.valueOf(context
                .getResources().getDisplayMetrics().density);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.resolution = dm.widthPixels + "*" + dm.heightPixels;
        this.os = Build.VERSION.INCREMENTAL;
        this.sdk = Build.VERSION.SDK_INT;
    }


    public static class Builder {

        private String imei;

        private String model;

        private String la;

        private String densityScaleFactor;

        private String resolution;

        private String os;

        private int sdk;

        public Builder imei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder la(String la) {
            this.la = la;
            return this;
        }

        public Builder densityScaleFactor(String densityScaleFactor) {
            this.densityScaleFactor = densityScaleFactor;
            return this;
        }

        public Builder resolution(String resolution) {
            this.resolution = resolution;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder sdk(int sdk) {
            this.sdk = sdk;
            return this;
        }
    }
}
