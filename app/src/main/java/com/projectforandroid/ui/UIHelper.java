package com.projectforandroid.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.projectforandroid.R;
import com.projectforandroid.ui.activity.CollectActivity;
import com.projectforandroid.ui.activity.DetailActivity;
import com.projectforandroid.ui.activity.IndexActivity;
import com.projectforandroid.ui.activity.PersonalActivity;
import com.projectforandroid.ui.activity.SettingActivity;
import com.projectforandroid.utils.camerautils.CameraUtils;
import com.projectforandroid.widget.popup.PopupCamera;

/**
 * Created by 大灯泡 on 2015/9/1.
 * 请将所有的startActivity，所有关于UI的工具类都放到这里
 */
public class UIHelper {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStateBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int titleBarHeight = 0;
        // 定义区域
        Rect titleFrame = new Rect();
        // 得到高度
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(titleFrame);
        statusBarHeight = titleFrame.top;
        return statusBarHeight;
    }

    /**
     * 获取标题栏高度
     */
    public static int getTitleBarHeight(Activity activity) {
        int titleBarHeight = 0;
        int contenttop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        titleBarHeight = contenttop - getStateBarHeight(activity);
        return titleBarHeight;
    }

    /**
     * 得到屏幕宽高，返回Int数组，int[0]=宽，int[1]=高
     */
    public static int[] getScreenWH(Activity activity) {
        int[] WH = { 0, 0 };
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WH[0] = metrics.widthPixels;
        WH[1] = metrics.heightPixels;
        return WH;
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /** 隐藏软键盘 */
    public static void hideInputMethod(View view) {
        InputMethodManager imm =
            (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /** 显示软键盘 */
    public static void showInputMethod(View view) {
        InputMethodManager imm =
            (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /** 显示软键盘 */
    public static void showInputMethod(Context context) {
        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /** 自定义信息 */
    public static void ToastMessage(Context context, String msg, int iconResid) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.widget_toast_view, null);
        TextView toast_msg = (TextView) view.findViewById(R.id.toast_msg);
        ImageView toast_icon = (ImageView) view.findViewById(R.id.toast_icon);
        if (iconResid > 0) {
            toast_icon.setVisibility(View.VISIBLE);
            toast_icon.setImageResource(iconResid);
        }
        toast_msg.setText(msg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    /** 判断view是否为空 */
    public static boolean isViewNull(View... views) {
        for (View v : views) {
            if (v == null) return true;
        }
        return false;
    }


    /**打开拍摄照片和图片裁剪窗口*/
    public static void startPhotoSelectActivity(Activity c){
        PopupCamera popupCamera=new PopupCamera(c);
        popupCamera.showPopupWindow();
    }

    /**打开拍照窗口*/
    public static void startToTakePhothActivity(Context c){
        CameraUtils.getPhtotFromCamera(c);
    }

    //------------------------------------------启动Activity的方法请放到这里---------------------------------------------
    /**启动到首页*/
    public static void startToIndexActivity(Activity c) {
        Intent intent = new Intent(c, IndexActivity.class);
        c.startActivity(intent);
        c.finish();
    }
    public static void startToCollectActivity(Activity c) {
        Intent intent = new Intent(c, CollectActivity.class);
        c.startActivity(intent);
    }

    public static void startToPersonalActivity(Activity c) {
        Intent intent = new Intent(c, PersonalActivity.class);
        c.startActivity(intent);
    }

    public static void startToSettingActivity(Activity c) {
        Intent intent = new Intent(c, SettingActivity.class);
        c.startActivity(intent);
    }
}