package org.zackratos.appstore.main.ranking;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;
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
        helper.setText(R.id.text_3, String.format("%d .", item.getPosition() + 1))
                .setText(R.id.text_1, item.getDisplayName())
                .setText(R.id.text_2, item.getLevel1CategoryName());

        ImageView imageView = helper.getView(R.id.image_1);
        Glide.with(imageView.getContext()).load(Constant.BASE_IMG_URL + item.getIcon()).into(imageView);
    }
}
