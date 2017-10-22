package org.zackratos.appstore.main.recommend;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class BannerBean implements MultiItemEntity {


    /**
     * thumbnail : http://t4.market.mi-img.com/thumbnail/jpeg/l750/AppStore/0ff69546de24355c8a484aafc27222e230f41f76f
     * action : subject
     * id : 169136
     */
    private String thumbnail;
    private String action;
    private String id;

    @Override
    public int getItemType() {
        return RecommendAdapter.BANNER;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
