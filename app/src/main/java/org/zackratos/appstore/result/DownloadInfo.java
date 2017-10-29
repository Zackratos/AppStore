package org.zackratos.appstore.result;

/**
 *
 * Created by Administrator on 2017/10/28.
 */

public class DownloadInfo {


    /**
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     * releaseKeyHash : 35c36f531f8c9eb76ae9d726f5155db2
     * icon : AppStore/01bd64f35aca00db7d4fa06282172b258be40d063
     * apkHash : b3c7ff02a9c7b6cb022f7cb3ada8835f
     * appendExpansionPackSize : 0
     * hdIcon : {"main":"AppStore/09e6f4c867fd9f5a838e08aef2bfa57d7314026c8"}
     * mainExpansionPackSize : 0
     * channelApkId : -1
     * fitness : 0
     * gamePackSize : 0
     * host : http://f6.market.xiaomi.com/download/
     * diffFileSize : 0
     * apkSize : 9547291
     * id : 434830
     * apk : AppStore/0396d0495f92d4c4205527f85940f0c44097bc8b2
     * refPosition : -1
     */

    private String thumbnail;
    private String releaseKeyHash;
    private String icon;
    private String apkHash;
    private int appendExpansionPackSize;
    private HdIcon hdIcon;
    private int mainExpansionPackSize;
    private int channelApkId;
    private int fitness;
    private int gamePackSize;
    private String host;
    private int diffFileSize;
    private int apkSize;
    private int id;
    private String apk;
    private int refPosition;

    public String getHost() {
        return host;
    }

    public String getApk() {
        return apk;
    }

    public String getDownloadUrl() {
        return String.format("%s%s", host, apk);
    }
}
