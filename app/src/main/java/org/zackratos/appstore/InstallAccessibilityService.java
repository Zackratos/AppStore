package org.zackratos.appstore;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 */

public class InstallAccessibilityService extends AccessibilityService {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onAccessibilityEvent(AccessibilityEvent event) {


        AccessibilityNodeInfo nodeInfo = event.getSource();

        if (nodeInfo == null) {
            return;
        }


        int evenType = event.getEventType();


        if (evenType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                evenType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {


            click("安装");
            click("下一步");
            click("确定");
            click("完成");

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void click(String text) {


        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();


        List<AccessibilityNodeInfo> nodeInfos = rootNodeInfo.findAccessibilityNodeInfosByText(text);

        if (nodeInfos == null) {
            return;
        }

        for (AccessibilityNodeInfo info : nodeInfos) {

            if (info.getClassName().equals("android.widget.Button") && info.isClickable()) {

                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
