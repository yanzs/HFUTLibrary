package yanzs.hfutlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.view.CircleImageView;

public class ThemeUtil {

    private volatile static ThemeUtil themeInstance;
    public final static int USER_DEFAULT = 1;
    public final static int USER_DEFINED = 2;


    public static ThemeUtil getInstance() {
        if (themeInstance == null) {
            synchronized (ThemeUtil.class) {
                if (themeInstance == null) {
                    themeInstance = new ThemeUtil();
                }
            }
        }
        return themeInstance;
    }


    public static void setThemeUI(Activity activity, View view, int color) {
        getInstance()._setThemeUI(activity, view, color);
    }

    private void _setThemeUI(Activity activity, View view, int color) {
        List<View> views = getAllChildViews(view);
        for (View v : views) {
            if (v.getId() == R.id.inform_goodbook_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.inform_hottop_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.inform_newbook_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.inform_search_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.inform_willbuy_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.login_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.sort_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.main_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.mine_have_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.mine_info_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.mine_now_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.mine_lend_rela_toolbar) {
                v.setBackgroundColor(color);
            }else if (v.getId() == R.id.news_detail_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.search_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.news_list_rela_toolbar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.viewholder_news_list_rela_calendar) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.welcome_rela_background) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.set_theme_img_show) {
                v.setBackgroundColor(color);
            } else if (v.getId() == R.id.set_theme_rela_toolbar) {
                v.setBackgroundColor(color);
            }else if (v.getId() == R.id.set_about_rela_toolbar) {
                v.setBackgroundColor(color);
            }


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    private List<View> getAllChildViews(View view) {
        List<View> allChildren = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allChildren.add(viewchild);
                allChildren.addAll(getAllChildViews(viewchild));//递归遍历所有子图
            }
        }
        return allChildren;
    }


    public static void setUserHead(Context context, CircleImageView circleImageView) {
        boolean defaultHead = ShareUtil.loadBoolData(context, ShareKey.SHARED_KEY, ShareKey.KEY_THEME_HEAD_DEFAULT);
        if (defaultHead) {
            circleImageView.setImageResource(R.drawable.user_default_head);
        } else {
            try {
                String path = context.getFilesDir().getPath() + File.separator + Values.UESR_DEFAULT_HEAD;
                File headImg = new File(path);
                if (headImg.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap != null) {
                        circleImageView.setImageBitmap(bitmap);
                    }
                } else {
                    circleImageView.setImageResource(R.drawable.user_default_head);
                }
            } catch (Exception e) {
                circleImageView.setImageResource(R.drawable.user_default_head);
            }

        }
    }


    public static void setUserTheme(Context context, RelativeLayout relativeLayout) {
        boolean defaultTheme = ShareUtil.loadBoolData(context, ShareKey.SHARED_KEY, ShareKey.KEY_THEME_BACKGROUND_DEFAULT);
        if (defaultTheme) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.user_default_background));
            }
        } else {
            try {
                String path = context.getFilesDir().getPath() + File.separator + Values.UESR_DEFAULT_BACKGROUND;
                System.out.println(path);
                File backImg = new File(path);
                if (backImg.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            relativeLayout.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.user_default_background));
                    }
                }
            } catch (Exception e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.user_default_background));
                }
            }

        }
    }

}
