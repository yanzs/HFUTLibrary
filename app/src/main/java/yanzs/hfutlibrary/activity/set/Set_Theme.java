package yanzs.hfutlibrary.activity.set;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import butterknife.BindView;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class Set_Theme extends BaseActivity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    @BindView(R.id.set_theme_bar_black)
    SeekBar bar_black;
    @BindView(R.id.set_theme_bar_blue)
    SeekBar bar_blue;
    @BindView(R.id.set_theme_bar_red)
    SeekBar bar_red;
    @BindView(R.id.set_theme_bar_green)
    SeekBar bar_green;
    @BindView(R.id.set_theme_rela_toolbar)
    RelativeLayout rela_toolbar;
    @BindView(R.id.set_theme_img_show)
    ImageView img_show;
    @BindView(R.id.set_theme_img_get)
    ImageView img_get;
    @BindView(R.id.set_theme_img_back)
    ImageView img_back;
    private int alpha,green,red,blue,color;


    @Override
    protected void initActivity() {
        int color= ColorUtil.getThemeColor(this);
        alpha=Color.alpha(color);
        green=Color.green(color);
        blue=Color.blue(color);
        red=Color.red(color);
        bar_black.setProgress(alpha*100/255);
        bar_blue.setProgress(blue*100/255);
        bar_green.setProgress(green*100/255);
        bar_red.setProgress(red*100/255);
        bar_black.setOnSeekBarChangeListener(this);
        bar_blue.setOnSeekBarChangeListener(this);
        bar_green.setOnSeekBarChangeListener(this);
        bar_red.setOnSeekBarChangeListener(this);
        img_get.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_set_theme;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id=seekBar.getId();
        switch (id){
            case R.id.set_theme_bar_black:
                alpha=progress*255/100;
                break;
            case R.id.set_theme_bar_blue:
                blue=progress*255/100;
                break;
            case R.id.set_theme_bar_red:
                red=progress*255/100;
                break;
            case R.id.set_theme_bar_green:
                green=progress*255/100;
                break;
        }
        color=Color.argb(alpha,red,green,blue);
        rela_toolbar.setBackgroundColor(color);
        img_show.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.set_theme_img_get:
                ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,ShareKey.KEY_THEME,color);
                finish();
                break;
            case R.id.set_theme_img_back:
                finish();
                break;
        }
    }
}
