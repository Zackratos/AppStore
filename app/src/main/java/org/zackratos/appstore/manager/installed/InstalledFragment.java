package org.zackratos.appstore.manager.installed;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.zackratos.appstore.AndroidAPK;
import org.zackratos.appstore.R;
import org.zackratos.appstore.base.BaseFragment;
import org.zackratos.appstore.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by xiboke on 2017/10/26.
 */

public class InstalledFragment extends BaseFragment {

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

    private Disposable disposable;

    private InstalledAdapter adapter;

    private BroadcastReceiver receiver;

    @Override
    protected void initDataAndEvent() {
        disposable = Observable.just(this)
                .subscribeOn(Schedulers.io())
                .map(new Function<InstalledFragment, InstalledAdapter>() {
                    @Override
                    public InstalledAdapter apply(@NonNull InstalledFragment installedFragment) throws Exception {

                        adapter = new InstalledAdapter(getApps());
                        adapter.setOnItemChildClickListener(itemChildClickListener());
                        return adapter;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<InstalledAdapter>() {
                    @Override
                    public void accept(InstalledAdapter installedAdapter) throws Exception {
                        rv.setAdapter(installedAdapter);
                    }
                });

        receiver = new AppRemoveReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        Activity activity = getActivity();
        activity.registerReceiver(receiver, intentFilter);
    }


    private static final int REQUEST_UNINSTALL = 1;

    private int uninstallPosition;


    private static final String TAG = "InstalledFragment";
    
    private List<AndroidAPK> getApps() {
        return AppUtils.getInstalledApps(getActivity());
    }

    private void unInstallApp(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + pkgName));
        startActivityForResult(i, REQUEST_UNINSTALL);
    }

    private BaseQuickAdapter.OnItemChildClickListener itemChildClickListener() {
        return new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.image_2) {
                    AndroidAPK androidAPK = (AndroidAPK) adapter.getData().get(position);
                    if (androidAPK.isSystem()) {
                        showDialog(androidAPK, getString(R.string.install_un_sys));
                        return;
                    }
                    if (androidAPK.getPackageName().equals(getActivity().getPackageName())) {
                        showDialog(androidAPK, getString(R.string.install_un_self));
                        return;
                    }
                    uninstallPosition = position;
                    unInstallApp(androidAPK.getPackageName());
                }
                
            }
        };
    }

    private void showDialog(AndroidAPK androidAPK, String message) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(androidAPK.getDrawable())
                .setTitle(androidAPK.getAppName())
                .setMessage(message)
                .setPositiveButton(getString(R.string.install_un_err_pos),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        getActivity().unregisterReceiver(receiver);
    }

    private class AppRemoveReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                adapter.remove(uninstallPosition);
            }
        }
    }

}
