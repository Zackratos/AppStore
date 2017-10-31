package org.zackratos.appstore.result;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.common.DLoadStatus;
import org.zackratos.appstore.main.recommend.RecommendAdapter;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class AppInfo implements MultiItemEntity {

    /**
     * addTime : 0
     * hasSameDevApp : false
     * videoId : 0
     * source :
     * versionName : 5.9.1
     * hdIcon : {"main":"AppStore/0196e15194b904d9021c9f8f4e02237d5a1ee2215"}
     * ratingScore : 3
     * briefShow : 全新魔域，荣耀回归
     * developerId : 0
     * fitness : 0
     * id : 430381
     * level1CategoryId : 15
     * releaseKeyHash : 905be057d40212727c43ed49eb3dbb6f
     * relateAppHasMore : false
     * rId : 0
     * suitableType : 2
     * briefUseIntro : false
     * ads : 0
     * publisherName : 西山居世游
     * level2CategoryId : 19
     * position : 0
     * favorite : false
     * isFavorite : false
     * appendSize : 0
     * level1CategoryName : 游戏
     * samDevAppHasMore : false
     * displayName : 魔域手游
     * icon : AppStore/01f2d47d93ab8f73ea2d005ed2309e8aaa2439f90
     * screenshot : AppStore/0d8ef149f707749843ef7dc9dc64a087428f8b133,AppStore/037974f30f75aadcaf7c145dda99129a2a24117be,AppStore/0381da425b3c74d5434b35dd89a98ca1960b90ccc,AppStore/038ef429f4087a98f1ef7dc9d0b4ac81497433c01,AppStore/038ef429f4087b98f1ef73c9d3b4a481437433c01
     * ratingTotalCount : 0
     * adType : 0
     * apkSize : 520139583
     * packageName : com.xishanju.mysy.mi
     * updateTime : 1508499738401
     * grantCode : 0
     * versionCode : 171
     * appTags : [{"tagId":278,"link":"sametag/278","tagName":"角色"},{"tagId":350,"link":"sametag/350","tagName":"3D RPG"}]
     * diffFileSize : 0
     */

    private int addTime;
    private boolean hasSameDevApp;
    private int videoId;
    private String source;
    private String versionName;
    private float ratingScore;
    private String briefShow;
    private int developerId;
    private int fitness;
    private int id;
    private int level1CategoryId;
    private String releaseKeyHash;
    private boolean relateAppHasMore;
    private int rId;
    private int suitableType;
    private boolean briefUseIntro;
    private int ads;
    private String publisherName;
    private int level2CategoryId;
    private int position;
    private boolean favorite;
    private boolean isFavorite;
    private int appendSize;
    private String level1CategoryName;
    private boolean samDevAppHasMore;
    private String displayName;
    private String icon;
    private String screenshot;
    private int ratingTotalCount;
    private int adType;
    private int apkSize;
    private String packageName;
    private long updateTime;
    private int grantCode;
    private int versionCode;
    private int diffFileSize;
    private HdIcon hdIcon;
    private List<AppTag> appTags;

    private List<AppInfo> relateAppInfoList;

    private String changeLog;

    private String permissionIds;

    private String web;

    private String introduction;

    private String detailHeaderImage;

    private List<AppInfo> sameDevAppInfoList;

    private String categoryId;

//    private DownloadInfo downloadInfo;

    private String downloadUrl;

    private DLoadStatus dLoadStatus;


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setBriefShow(String briefShow) {
        this.briefShow = briefShow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddTime() {
        return addTime;
    }

    public boolean isHasSameDevApp() {
        return hasSameDevApp;
    }

    public int getVideoId() {
        return videoId;
    }

    public String getSource() {
        return source;
    }

    public String getVersionName() {
        return versionName;
    }

    public float getRatingScore() {
        return ratingScore;
    }

    public String getBriefShow() {
        return briefShow;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public int getFitness() {
        return fitness;
    }

    public int getId() {
        return id;
    }

    public int getLevel1CategoryId() {
        return level1CategoryId;
    }

    public String getReleaseKeyHash() {
        return releaseKeyHash;
    }

    public boolean isRelateAppHasMore() {
        return relateAppHasMore;
    }

    public int getrId() {
        return rId;
    }

    public int getSuitableType() {
        return suitableType;
    }

    public boolean isBriefUseIntro() {
        return briefUseIntro;
    }

    public int getAds() {
        return ads;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public int getLevel2CategoryId() {
        return level2CategoryId;
    }

    public int getPosition() {
        return position;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getAppendSize() {
        return appendSize;
    }

    public String getLevel1CategoryName() {
        return level1CategoryName;
    }

    public boolean isSamDevAppHasMore() {
        return samDevAppHasMore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public int getRatingTotalCount() {
        return ratingTotalCount;
    }

    public int getAdType() {
        return adType;
    }

    public int getApkSize() {
        return apkSize;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public int getGrantCode() {
        return grantCode;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public int getDiffFileSize() {
        return diffFileSize;
    }

    public HdIcon getHdIcon() {
        return hdIcon;
    }

    public List<AppTag> getAppTags() {
        return appTags;
    }

    public void setdLoadStatus(DLoadStatus dLoadStatus) {
        this.dLoadStatus = dLoadStatus;
    }

    public DLoadStatus getdLoadStatus() {
        return dLoadStatus;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIntroduction() {
        return introduction;
    }


    public List<AppInfo> getRelateAppInfoList() {
        return relateAppInfoList;
    }

    public List<AppInfo> getSameDevAppInfoList() {
        return sameDevAppInfoList;
    }

    @Override
    public int getItemType() {
        return RecommendAdapter.APP;
    }

}
