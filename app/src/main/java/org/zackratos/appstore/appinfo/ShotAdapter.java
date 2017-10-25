package org.zackratos.appstore.appinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.zackratos.appstore.R;
import org.zackratos.appstore.app.Constant;

/**
 *
 * Created by xiboke on 2017/10/25.
 */

public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {

    private String[] screenShot;

    private Context context;

    public ShotAdapter(Context contexts, String[] screenShot) {
        this.context = contexts;
        this.screenShot = screenShot;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_appinfo_shot, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(String.format("%s%s", Constant.BASE_IMG_URL, screenShot[position]))
                .into((ImageView) holder.itemView);
    }

    @Override
    public int getItemCount() {
        return screenShot.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

}
