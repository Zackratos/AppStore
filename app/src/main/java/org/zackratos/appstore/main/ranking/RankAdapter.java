package org.zackratos.appstore.main.ranking;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.appstore.R;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class RankAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    public RankAdapter(@Nullable List<AppInfo> data) {
        super(R.layout.item_rank_app, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        helper.setText(R.id.text_1, String.valueOf(item.getPosition() + 1))
                .setText(R.id.text_2, item.getDisplayName())
                .setText(R.id.text_3, item.getBriefShow());
    }
}
