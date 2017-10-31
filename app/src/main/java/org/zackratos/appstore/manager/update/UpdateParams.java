package org.zackratos.appstore.manager.update;

import org.zackratos.appstore.http.PublicParams;

/**
 *
 * Created by Administrator on 2017/10/29.
 */

public class UpdateParams {

    private String packageName;
    private String versionCode;
    private PublicParams publicParams;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public void setPublicParams(PublicParams publicParams) {
        this.publicParams = publicParams;
    }
}
