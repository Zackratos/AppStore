package org.zackratos.appstore.main.recommend;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.youth.banner.Banner;

import org.zackratos.appstore.DownloadButton;
import org.zackratos.appstore.DownloadButtonHelper;
import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;
import org.zackratos.appstore.result.Banners;
import org.zackratos.appstore.result.Header;
import org.zackratos.appstore.result.AppInfo;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class RecommendAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public static final int BANNER = 1;

    public static final int NAV = 2;

    public static final int HEADER = 3;

    public static final int APP = 4;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param entities A new list is created out of this one to avoid mutable list
     */
    public RecommendAdapter(List<MultiItemEntity> entities) {
        super(entities);
        addItemType(BANNER, R.layout.item_recommend_banner);
        addItemType(NAV, R.layout.item_recommend_nav);
        addItemType(HEADER, R.layout.item_recommned_header);
        addItemType(APP, R.layout.item_app_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case BANNER:
                Banner banner = helper.getView(R.id.banner);
                banner.setImages(((Banners) item).getImages())
                        .setImageLoader(new GlideImageLoader())
                        .start();
                break;
            case NAV:
                helper.addOnClickListener(R.id.linear_layout_1)
                        .addOnClickListener(R.id.linear_layout_2)
                        .addOnClickListener(R.id.linear_layout_3);
                break;
            case HEADER:
                helper.setText(R.id.text_1, ((Header) item).getTitleId());
                break;
            case APP:
                AppInfo data = (AppInfo) item;
                helper.setText(R.id.text_1, data.getDisplayName())
                        .setText(R.id.text_2, data.getBriefShow())
                        .addOnClickListener(R.id.image_2);
                ImageView imageView = helper.getView(R.id.image_1);
                DownloadButton downloadButton = helper.getView(R.id.download_button);
                new DownloadButtonHelper(downloadButton, data).handleButton();

                Glide.with(imageView.getContext())
                        .load(String.format("%s%s", Constant.BASE_IMG_URL, data.getIcon()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            default:
                break;
        }
    }

}
