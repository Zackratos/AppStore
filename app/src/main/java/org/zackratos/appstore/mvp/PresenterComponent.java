package org.zackratos.appstore.mvp;

import org.zackratos.appstore.Test.TestActivity2;
import org.zackratos.appstore.Test.TestFragment;
import org.zackratos.appstore.app.AppComponent;
import org.zackratos.appstore.main.ranking.RankFragment;
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

    void inject(TestActivity2 activity);

    void inject(RankFragment fragment);

//    void inject(TestFragment fragment);


}
