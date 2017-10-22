package org.zackratos.appstore.main.recommend;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class Header implements MultiItemEntity {

    @Override
    public int getItemType() {
        return RecommendAdapter.HEADER;
    }
}
