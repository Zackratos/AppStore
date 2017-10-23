package org.zackratos.appstore.result;

import android.support.annotation.StringRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.main.recommend.RecommendAdapter;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class Header implements MultiItemEntity {

    @StringRes
    private int titleId;

    public Header(@StringRes int titleId) {
        this.titleId = titleId;
    }

    @StringRes
    public int getTitleId() {
        return titleId;
    }

    @Override
    public int getItemType() {
        return RecommendAdapter.HEADER;
    }
}
