package yanzs.hfutlibrary.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.util.ThemeUtil;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        initButterKnife();
        initTheme();
        initActivity();
    }

    public void initTheme() {
        int color= ShareUtil.loadIntData(this, ShareKey.SHARED_KEY,ShareKey.KEY_THEME);
        if (color==0){
            color= Color.parseColor(Values.DEFAULT_COLOR);
        }
        ThemeUtil.setThemeUI(this,getWindow().getDecorView(),color);
    }


    private void initButterKnife() {
        ButterKnife.bind(this);
        ButterKnife.setDebug(false);
    }

    protected abstract void initActivity();

    protected abstract int getResId();
}
