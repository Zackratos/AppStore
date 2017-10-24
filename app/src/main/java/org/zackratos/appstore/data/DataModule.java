package org.zackratos.appstore.data;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by xiboke on 2017/10/24.
 */
@Module
public class DataModule {

    @Singleton
    @Provides
    public DataHelper provideDataHelper(Application application) {
        return new PrefHelper(application);
    }

}
