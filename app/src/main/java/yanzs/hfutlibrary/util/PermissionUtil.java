package yanzs.hfutlibrary.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

import yanzs.hfutlibrary.callBack.PermissionCallBack;
import yanzs.hfutlibrary.R;

public class PermissionUtil {

    public static void requestPermission(final Activity activity, final PermissionCallBack callback, String... permissions) {
        AndPermission.with(activity)
                .permission(permissions)
                .rationale(new DefaultRationale())
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        callback.onSuccess();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            showSetting(activity, permissions);
                        }
                        callback.onDefined();
                    }
                })
                .start();
    }

    private static void showSetting(Context mContext, final List<String> permissions) {
        String message = mContext.getString(R.string.app_name) + "需要以下权限，请在设置中为我们开启：\n\n" + getPermissionText(mContext, permissions);

        final SettingService settingService = AndPermission.permissionSetting(mContext);
        new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.execute();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.cancel();
                    }
                })
                .show();
    }

    private static String getPermissionText(Context context, List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        StringBuilder sb = new StringBuilder();
        for (String permissionName : permissionNames) {
            sb.append(" ");
            sb.append(permissionName);
        }
        return sb.toString();
    }


    private static class DefaultRationale implements Rationale {

        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
            String message = context.getString(R.string.app_name) + "需要以下权限，请在设置中为我们开启：\n\n" + getPermissionText(context,permissions);
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.execute();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.cancel();
                        }
                    })
                    .show();
        }
    }
}