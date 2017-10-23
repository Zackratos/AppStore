package org.zackratos.appstore.main.ranking;

import org.zackratos.appstore.http.PublicParams;

import javax.inject.Inject;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class TopListParams {

    private int page;

    private PublicParams publicParams;

    @Inject
    public TopListParams(PublicParams params) {
        this.publicParams = params;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
