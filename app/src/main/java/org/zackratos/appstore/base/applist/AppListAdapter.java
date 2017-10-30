package org.zackratos.appstore.base.applist;

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

public class AppListAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    private boolean number;

    public AppListAdapter(@Nullable List<AppInfo> data, boolean number) {
        super(number ? R.layout.item_app_list_number : R.layout.item_app_list, data);
        this.number = number;

    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {

        BaseViewHolder viewHolder = helper.setText(R.id.text_1, item.getDisplayName())
                .setText(R.id.text_2, item.getBriefShow());
        if (number) {
            viewHolder.setText(R.id.text_3, String.format("%d .", item.getPosition() + 1));
        }

        ImageView imageView = helper.getView(R.id.image_1);
        Glide.with(imageView.getContext()).load(Constant.BASE_IMG_URL + item.getIcon()).into(imageView);
    }
}
