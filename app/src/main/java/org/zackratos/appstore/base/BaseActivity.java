package org.zackratos.appstore.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * Created by Administrator on 2017/9/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        onViewCreated(savedInstanceState);
        initEventAndData();
    }

    protected void onViewCreated(Bundle savedInstanceState) {

    }

    protected void setToolbar(Toolbar toolbar, boolean displayHome) {
        setSupportActionBar(toolbar);
        if (displayHome) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != Unbinder.EMPTY)
            unbinder.unbind();
    }

    @LayoutRes
    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
