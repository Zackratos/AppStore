package org.zackratos.appstore.main;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class PageParams {

    private int page;

    private PublicParams publicParams;

    @Inject
    public PageParams(PublicParams params) {
        this.publicParams = params;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
