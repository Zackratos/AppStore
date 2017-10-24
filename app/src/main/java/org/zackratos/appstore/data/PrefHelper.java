package org.zackratos.appstore.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.zackratos.appstore.result.UserInfo;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class PrefHelper implements DataHelper {

    private static final String SP_USER = "user";

    private static final String KEY_ID = "id";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_NAME = "name";

    private static final String KEY_MOBI = "mobi";

    private static final String KEY_TOKEN = "token";

    private SharedPreferences defaultSP;

    private SharedPreferences userSP;

    private Context context;

    public PrefHelper(Context context) {
        this.context = context;
    }


    @Override
    public void putUserInfo(UserInfo userInfo) {
        initUserSP();
        userSP.edit().putInt(KEY_ID, userInfo.getId())
                .putString(KEY_EMAIL, userInfo.getEmail())
                .putString(KEY_NAME, userInfo.getUsername())
                .putString(KEY_MOBI, userInfo.getMobi())
                .apply();
    }

    @Override
    public UserInfo getUserInfo() {
        initUserSP();
        int id = userSP.getInt(KEY_ID, -1);
        if (id == -1) {
            return null;
        }
        return new UserInfo.Builder()
                .id(id)
                .email(userSP.getString(KEY_EMAIL, null))
                .userName(userSP.getString(KEY_NAME, null))
                .mobi(userSP.getString(KEY_MOBI, null))
                .build();
    }

    @Override
    public void clearUserInfo() {
        initUserSP();
        userSP.edit().clear().apply();
    }

    @Override
    public void putToken(String token) {
        initDefaultSP();
        defaultSP.edit().putString(KEY_TOKEN, token).apply();
    }

    @Override
    public String getToken() {
        initDefaultSP();
        return defaultSP.getString(KEY_TOKEN, null);
    }


    private void initUserSP() {
        if (userSP == null) {
            userSP = context.getSharedPreferences(SP_USER, Context.MODE_PRIVATE);
        }
    }


    private void initDefaultSP() {
        if (defaultSP == null) {
            defaultSP = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

}
