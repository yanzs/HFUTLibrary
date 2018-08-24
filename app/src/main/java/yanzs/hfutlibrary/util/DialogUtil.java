package yanzs.hfutlibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


public class DialogUtil {

    public static ZLoadingDialog initLoadDialog(Context context, String s) {
        int color=ColorUtil.getThemeColor(context);
        ZLoadingDialog dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)
                .setLoadingColor(color)
                .setHintText(s)
                .setHintTextSize(16)
                .setHintTextColor(Color.GRAY).show();
        return dialog;
    }


    public static void showInformDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
