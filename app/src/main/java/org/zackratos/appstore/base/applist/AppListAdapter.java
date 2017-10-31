package org.zackratos.appstore.base.applist;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.appstore.common.DownloadButton;
import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.appinfo.AppInfoActivity;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class AppListAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    private Context context;
    private boolean number;


    public AppListAdapter(final Context context, @Nullable List<AppInfo> data, boolean number) {
        super(number ? R.layout.item_app_list_number : R.layout.item_app_list, data);
        this.context = context;
        this.number = number;

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppInfo appInfo = (AppInfo) adapter.getData().get(position);
                Intent intent = AppInfoActivity.newIntent(context, appInfo.getId(),
                        appInfo.getDisplayName(), appInfo.getIcon());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                            ((Activity) context, view.findViewById(R.id.image_1), "share").toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }



    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {

        BaseViewHolder viewHolder = helper.setText(R.id.text_1, item.getDisplayName())
                .setText(R.id.text_2, item.getBriefShow());
        if (number) {
            viewHolder.setText(R.id.text_3, String.format("%d .", item.getPosition() + 1));
        }
        ImageView imageView = helper.getView(R.id.image_1);
        Glide.with(context).load(Constant.BASE_IMG_URL + item.getIcon()).into(imageView);
        DownloadButton button = helper.getView(R.id.download_button);
//        new DownloadHelper(context, item, button).handleButton();
    }


}
