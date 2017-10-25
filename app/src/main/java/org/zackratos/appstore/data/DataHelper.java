package org.zackratos.appstore.data;

import org.zackratos.appstore.result.UserInfo;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public interface DataHelper {

    void putUserInfo(UserInfo userInfo);

    UserInfo getUserInfo();

    void clearUserInfo();

    void putToken(String token);

    String getToken();

    void clearToken();

}
