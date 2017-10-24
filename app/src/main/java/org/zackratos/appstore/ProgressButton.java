package org.zackratos.appstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class ProgressButton extends FrameLayout {

    private ProgressBar progressBar;

    private Button button;

    public ProgressButton(@NonNull Context context) {
        this(context, null);
    }

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressBar = new ProgressBar(getContext());
        button = new Button(getContext());
        LayoutParams progressParams = (LayoutParams) progressBar.getLayoutParams();
        progressParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        progressParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        LayoutParams buttonParams = (LayoutParams) button.getLayoutParams();
        buttonParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        buttonParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        progressBar.setLayoutParams(progressParams);
        button.setLayoutParams(buttonParams);
    }




}
