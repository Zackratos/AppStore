package org.zackratos.appstore.main.recommend;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.youth.banner.Banner;

import org.zackratos.appstore.GlideImageLoader;
import org.zackratos.appstore.R;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class RecommendAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public static final int BANNER = 1;

    public static final int NAV = 2;

    public static final int HEADER = 3;

    public static final int DATA = 4;


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
        addItemType(DATA, R.layout.item_recommend_data);
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
                helper.addOnClickListener(R.id.text_1)
                        .addOnClickListener(R.id.text_2)
                        .addOnClickListener(R.id.text_3);
                break;
            case HEADER:
                break;
            case DATA:
                break;
            default:
                break;
        }
    }

}
