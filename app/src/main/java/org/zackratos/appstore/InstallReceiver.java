package org.zackratos.appstore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 * Created by Administrator on 2017/10/30.
 */

public class InstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getDataString();

        }
    }
}
