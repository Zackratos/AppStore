package org.zackratos.appstore.manager.installed;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.appstore.AndroidAPK;
import org.zackratos.appstore.R;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class InstalledAdapter extends BaseQuickAdapter<AndroidAPK, BaseViewHolder> {

    public InstalledAdapter(@Nullable List<AndroidAPK> data) {
        super(R.layout.item_manager_delete, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidAPK item) {
        helper.setImageDrawable(R.id.image_1, item.getDrawable())
                .setText(R.id.text_1, item.getAppName())
                .setTextColor(R.id.text_1, item.isSystem() ? Color.RED : Color.BLACK)
                .setText(R.id.text_2, item.getApkPath())
                .addOnClickListener(R.id.image_2);
    }

}
