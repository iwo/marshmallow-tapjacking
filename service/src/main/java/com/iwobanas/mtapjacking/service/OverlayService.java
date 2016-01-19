package com.iwobanas.mtapjacking.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class OverlayService extends Service {

    private View mOverlay;

    public OverlayService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ("SHOW".equals(intent.getAction())) {
            showOverlay();
        } else {
            hideOverlay();
        }
        return START_REDELIVER_INTENT;
    }

    private void showOverlay() {
        if (mOverlay != null)
            return;

        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.setTitle("Tapjacking Test Overlay");
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = dipToPx(-46);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.windowAnimations = 0;

        mOverlay = View.inflate(getApplicationContext(), R.layout.overlay, null);

        windowManager.addView(mOverlay, layoutParams);
    }

    private void hideOverlay() {
        if (mOverlay != null) {
            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
            windowManager.removeViewImmediate(mOverlay);
            mOverlay = null;
        }
    }

    private int dipToPx(int dip) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics()));
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
