package org.zackratos.appstore.main.recommend;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by Administrator on 2017/10/21.
 */

public class IndexParams {

    private PublicParams publicParams;

    @Inject
    public IndexParams(PublicParams publicParams) {
        this.publicParams = publicParams;
    }

}
