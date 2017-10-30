package org.zackratos.appstore.appinfo;

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

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/30.
 */

public class OtherAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {


    public OtherAdapter(@Nullable List<AppInfo> data, final Context context) {
        super(R.layout.item_appinfo_other, data);
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
        helper.setText(R.id.text_1, item.getDisplayName());
        ImageView imageView = helper.getView(R.id.image_1);
        Glide.with(imageView.getContext()).load(Constant.BASE_IMG_URL + item.getIcon()).into(imageView);
    }
}
