package org.zackratos.appstore.result;


import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class IndexData {

    private List<BannerBean> banners;

    private List<AppInfo> recommendApps;

    private List<AppInfo> recommendGames;

    public List<BannerBean> getBannerBeen() {
        return banners;
    }

    public List<AppInfo> getRecommendApps() {
        return recommendApps;
    }

    public List<AppInfo> getRecommendGames() {
        return recommendGames;
    }

}
