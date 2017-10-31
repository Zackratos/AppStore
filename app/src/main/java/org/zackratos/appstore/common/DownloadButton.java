package org.zackratos.appstore.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import org.zackratos.appstore.R;

/**
 *
 * Created by Administrator on 2017/10/28.
 */

public class DownloadButton extends View {

    private Paint paint;

    private float radius;

    private DLoadStatus dLoadStatus;

    public DownloadButton(Context context) {
        this(context, null);
    }

    public DownloadButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        dLoadStatus = new DLoadStatus(DLoadStatus.STATUS_UNDOWNLOAD);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DownloadButton, 0, 0);
        paint.setColor(ta.getColor(R.styleable.DownloadButton_color, ContextCompat.getColor(context, R.color.main)));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = getMeasuredWidth() / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRing(canvas);
        drawProgress(canvas, dLoadStatus.getProgress());
        int status = dLoadStatus.getStatus();
        if (status == DLoadStatus.STATUS_UNDOWNLOAD) {
            drawButton(canvas, R.drawable.down_button_download);
        } else if (status == DLoadStatus.STATUS_UNUPDATE) {
            drawButton(canvas, R.drawable.down_button_update);
        } else if (status == DLoadStatus.STATUS_DOWNLOADING) {
            drawButton(canvas, R.drawable.down_button_pause);
        } else if (status == DLoadStatus.STATUS_PAUSE) {
            drawButton(canvas, R.drawable.down_button_downloading);
        } else if (status == DLoadStatus.STATUS_UNINSTALL) {
            drawButton(canvas, R.drawable.down_button_install);
        } else if (status == DLoadStatus.STATUS_INSTALLING) {
            drawButton(canvas, R.drawable.down_button_install);
        } else if (status == DLoadStatus.STATUS_INSTALLED) {
            drawButton(canvas, R.drawable.down_button_start);
        }
    }

    private void drawRing(Canvas canvas) {
        paint.setStrokeWidth(radius / 20);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius * 39 / 40, paint);
    }

    private void drawProgress(Canvas canvas, float progress) {
        paint.setStrokeWidth(radius / 10);
        float start = radius / 10;
        float end = radius * 19 / 10;
        RectF rectF = new RectF(start, start, end, end);
        canvas.drawArc(rectF, - 90, 3.6f * progress, false, paint);
    }

    private void drawButton(Canvas canvas, @DrawableRes int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
        RectF rectF = new RectF(radius / 2, radius / 2, radius * 3 / 2, radius * 3 / 2);
        canvas.drawBitmap(bitmap, null, rectF, null);
    }



    public void setDLoadStatus(DLoadStatus dLoadStatus) {
        if (dLoadStatus.getStatus() != DLoadStatus.STATUS_DOWNLOADING &&
                dLoadStatus.getStatus() != DLoadStatus.STATUS_PAUSE) {
            dLoadStatus.setProgress(0);
        }
        this.dLoadStatus = dLoadStatus;
        invalidate();
    }



}
