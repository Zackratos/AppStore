package org.zackratos.appstore.result;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.zackratos.appstore.main.recommend.RecommendAdapter;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class NavIcon implements MultiItemEntity {

    @Override
    public int getItemType() {
        return RecommendAdapter.NAV;
    }
}
