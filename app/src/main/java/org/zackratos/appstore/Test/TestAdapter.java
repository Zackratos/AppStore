package org.zackratos.appstore.Test;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/23.
 */

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TestAdapter(@Nullable List<String> data) {
        super(android.R.layout.simple_list_item_1, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(android.R.id.text1, item);
    }
}
