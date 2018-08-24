package yanzs.hfutlibrary.util;

import android.content.Context;
import android.graphics.Color;

import java.util.Random;

import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;

public class ColorUtil {
    /**
     * 颜色加深处理
     */
    public static int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xff;
        int green = RGBValues >> 8 & 0xff;
        int blue = RGBValues & 0xff;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    public static int getRandColorCode(){
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r,g,b);
    }

    public static int getThemeColor(Context context){
        int color=ShareUtil.loadIntData(context,ShareKey.SHARED_KEY, ShareKey.KEY_THEME);
        if (color==0){
            color= Color.parseColor(Values.DEFAULT_COLOR);
        }
        return color;
    }


    /**
     * 颜色变浅处理
     */
    public static int colorEasy(int RGBValues) {
        int red = RGBValues >> 16 & 0xff;
        int green = RGBValues >> 8 & 0xff;
        int blue = RGBValues & 0xff;
        if (red == 0) {
            red = 10;
        }
        if (green == 0) {
            green = 10;
        }
        if (blue == 0) {
            blue = 10;
        }
        red = (int) Math.floor(red * (1 + 0.1));
        green = (int) Math.floor(green * (1 + 0.1));
        blue = (int) Math.floor(blue * (1 + 0.1));
        return Color.rgb(red, green, blue);
    }
}
