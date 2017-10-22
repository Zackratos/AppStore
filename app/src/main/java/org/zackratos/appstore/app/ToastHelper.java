package org.zackratos.appstore.app;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 *
 * Created by Administrator on 2017/10/14.
 */

public class ToastHelper {

    private Toast toast;
    private Context context;

    public ToastHelper(Context context) {
        this.context = context;
    }

    public void show(String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }


    public void show(@StringRes int textId) {
        if (toast == null) {
            toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(textId);
        }
        toast.show();
    }
}
