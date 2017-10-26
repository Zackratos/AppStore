package org.zackratos.appstore.appinfo;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class AppParams {

    private PublicParams publicParams;

    @Inject
    public AppParams(PublicParams publicParams) {
        this.publicParams = publicParams;
    }
}
