package org.zackratos.appstore.mvp;

import org.zackratos.appstore.app.AppComponent;
import org.zackratos.appstore.category.fine.FineFragment;
import org.zackratos.appstore.category.news.NewsFragment;
import org.zackratos.appstore.category.rank.CateRankFragment;
import org.zackratos.appstore.main.game.GameFragment;
import org.zackratos.appstore.main.ranking.RankFragment;
import org.zackratos.appstore.main.category.CategoryFragment;
import org.zackratos.appstore.main.recommend.RecommendFragment;

import dagger.Component;

/**
 *
 * Created by Administrator on 2017/10/15.
 */
@PresenterScope
@Component(modules = PresenterModule.class, dependencies = AppComponent.class)
public interface PresenterComponent {

    void inject(RecommendFragment fragment);

    void inject(RankFragment fragment);

    void inject(GameFragment fragment);

    void inject(CategoryFragment fragment);

    void inject(FineFragment fragment);

    void inject(CateRankFragment fragment);

    void inject(NewsFragment fragment);

}
