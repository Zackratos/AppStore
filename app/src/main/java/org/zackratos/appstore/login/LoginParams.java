package org.zackratos.appstore.login;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class LoginParams {

    private String email;

    private String password;

    private PublicParams publicParams;

    @Inject
    public LoginParams(PublicParams publicParams) {
        this.publicParams = publicParams;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
