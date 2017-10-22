package org.zackratos.appstore.main.recommend;



import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class Banners implements MultiItemEntity {

    private List<BannerBean> bannerBeen;

    public Banners(List<BannerBean> bannerBeen) {
        this.bannerBeen = bannerBeen;
    }

    @Override
    public int getItemType() {
        return RecommendAdapter.BANNER;
    }

    public List<BannerBean> getBannerBeen() {
        return bannerBeen;
    }

    private List<String> images;

    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        } else {
            images.clear();
        }
        for (BannerBean bannerBean : bannerBeen) {
            images.add(bannerBean.getThumbnail());
        }

        return images;
    }
}
