package org.zackratos.appstore.main.recommend;


import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class IndexData {

    private List<BannerBean> banners;

    private List<RecommendData> recommendApps;

    private List<RecommendData> recommendGames;

    public List<BannerBean> getBannerBeen() {
        return banners;
    }

    public List<RecommendData> getRecommendApps() {
        return recommendApps;
    }

    public List<RecommendData> getRecommendGames() {
        return recommendGames;
    }

}
