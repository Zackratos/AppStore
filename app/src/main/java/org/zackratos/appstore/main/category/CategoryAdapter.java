package org.zackratos.appstore.main.category;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.result.Category;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {


    public CategoryAdapter(@Nullable List<Category> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category item) {
        helper.setText(R.id.text_1, item.getName());
        ImageView iconView = helper.getView(R.id.image_1);
        Glide.with(iconView.getContext())
                .load(Constant.BASE_IMG_URL + item.getIcon())
                .into(iconView);

    }
}
