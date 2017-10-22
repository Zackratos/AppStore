package org.zackratos.appstore.Test;

import android.util.Log;

import org.zackratos.appstore.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/10/15.
 */

public class TestPresenter extends RxPresenter<TestContract.View> implements TestContract.Presenter {

    private static final String TAG = "TestPresenter";

    @Inject
    TestPresenter() {

    }


    @Override
    public void test() {
        Log.d(TAG, "test: ");
        view.viewTest();
    }
}
