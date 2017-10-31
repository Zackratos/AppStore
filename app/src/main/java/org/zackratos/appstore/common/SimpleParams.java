package org.zackratos.appstore.common;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class SimpleParams {

    private PublicParams publicParams;

    @Inject
    public SimpleParams(PublicParams publicParams) {
        this.publicParams = publicParams;
    }

}
