package org.zackratos.appstore.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.zackratos.appstore.R;
import org.zackratos.appstore.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class TestFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @Override
    protected int getLayout() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initDataAndEvent() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("test " + i);
        }
        TestAdapter adapter = new TestAdapter(data);
        rv.setAdapter(adapter);
    }
}
