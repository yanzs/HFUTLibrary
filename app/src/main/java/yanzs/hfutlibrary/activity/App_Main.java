package yanzs.hfutlibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import yanzs.hfutlibrary.activity.mine.Mine_Have;
import yanzs.hfutlibrary.activity.mine.Mine_Info;
import yanzs.hfutlibrary.activity.mine.Mine_Lend;
import yanzs.hfutlibrary.activity.mine.Mine_Now;
import yanzs.hfutlibrary.activity.set.Set_About;
import yanzs.hfutlibrary.activity.set.Set_Theme;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.adapter.FragMainAdapter;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ThemeUtil;
import yanzs.hfutlibrary.view.CircleImageView;

public class App_Main extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener, OnFinishRequestListener {

    @BindView(R.id.main_drawer)
    DrawerLayout main_drawer;
    @BindView(R.id.main_text_title)
    TextView main_text_title;
    @BindView(R.id.main_img_sidemenu)
    ImageView main_img_sidemenu;
    @BindView(R.id.main_img_search)
    ImageView main_img_search;
    @BindViews({R.id.main_img_index, R.id.main_img_inform, R.id.main_img_news})
    List<ImageView> main_tab_img = new ArrayList<>();
    @BindViews({R.id.main_text_index, R.id.main_text_inform, R.id.main_text_news})
    List<TextView> main_tab_text = new ArrayList<>();
    @BindViews({R.id.main_rela_index, R.id.main_rela_inform, R.id.main_rela_news})
    List<RelativeLayout> main_tab_rela = new ArrayList<>();
    @BindView(R.id.main_nav)
    NavigationView main_nav;
    private FragMainAdapter fragMainAdapter;
    private CircleImageView nav_header_img;
    private TextView nav_head_text;
    private RelativeLayout nav_head_rela;
    private RequestCallBack callBack;
    private ZLoadingDialog dialog;
    private boolean setClassSort=false;
    private boolean setYearSort=false;
    private boolean setMonthSort=false;
    private boolean changeTheme = false;

    @Override
    protected void initActivity() {
        initView();
    }

    private void initView() {
        fragMainAdapter = new FragMainAdapter(getSupportFragmentManager());
        initTabPager(0);
        main_nav.setNavigationItemSelectedListener(this);
        nav_header_img = main_nav.getHeaderView(0).findViewById(R.id.nav_head_img);
        nav_head_text = main_nav.getHeaderView(0).findViewById(R.id.nav_head_text);
        nav_head_rela = main_nav.getHeaderView(0).findViewById(R.id.nav_head_rela_back);
        ThemeUtil.setUserHead(this, nav_header_img);
        ThemeUtil.setUserTheme(this, nav_head_rela);
        nav_head_text.setText(JsoupUtil.getUserName(this));
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_main;
    }

    @OnClick({R.id.main_rela_index,R.id.main_rela_inform,R.id.main_rela_news,R.id.main_img_sidemenu,R.id.main_img_search})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_rela_index:
                initTabPager(0);
                break;
            case R.id.main_rela_inform:
                initTabPager(1);
                break;
            case R.id.main_rela_news:
                initTabPager(2);
                break;
            case R.id.main_img_sidemenu:
                main_drawer.openDrawer(main_nav);
                break;
            case R.id.main_img_search:
                Intent intent = new Intent();
                intent.setClass(this, App_Search.class);
                startActivity(intent);
                break;
        }
    }

    private void initTabPager(int n) {
        fragMainAdapter.loadFragment(n);
        for (int i = 0; i < 3; i++) {
            if (i == n) {
                main_tab_img.get(i).setColorFilter(ColorUtil.getThemeColor(this));
                main_tab_text.get(i).setTextColor(ColorUtil.getThemeColor(this));
            } else {
                main_tab_img.get(i).setColorFilter(Color.GRAY);
                main_tab_text.get(i).setTextColor(Color.GRAY);
            }
        }
        main_text_title.setText(Values.MAIN_TITLE[n]);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_have:
                dialog = DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
                initPager(Urls.URL_USER_HAVE, ShareKey.KEY_USER_HAVE);
                break;
            case R.id.menu_now:
                dialog = DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
                initPager(Urls.URL_USER_NOW, ShareKey.KEY_USER_NOW);
                break;
            case R.id.menu_info:
                dialog = DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
                initPager(Urls.URL_USER_INFO, ShareKey.KEY_USER_INFO);
                changeTheme = true;
                break;
            case R.id.menu_lend:
                dialog = DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
                initChart();
                break;
            case R.id.menu_about:
                Intent intentAbout = new Intent();
                intentAbout.setClass(this, Set_About.class);
                startActivity(intentAbout);
                break;
            case R.id.menu_set:
                Intent intentTheme = new Intent();
                intentTheme.setClass(this, Set_Theme.class);
                changeTheme = true;
                startActivity(intentTheme);
                break;
        }
        return true;
    }

    private void initChart() {
        try {
            RequestCallBack classCallBack, yearCallBack, monthCallBack;
            classCallBack = new RequestCallBack(ShareKey.KEY_GSON_CLASS_SORT, this);
            monthCallBack = new RequestCallBack(ShareKey.KEY_GSON_MONTH_SORT, this);
            yearCallBack = new RequestCallBack(ShareKey.KEY_GSON_YEAR_SORT, this);
            classCallBack.setOnFinishRequestListener(this);
            monthCallBack.setOnFinishRequestListener(this);
            yearCallBack.setOnFinishRequestListener(this);
            OkHttpUtil.getResponseFromGET(Urls.URL_CHART_CLASS_SORT, Urls.URL_HOST, Urls.URL_CHART_Referer_CLASS, classCallBack);
            OkHttpUtil.getResponseFromGET(Urls.URL_CHART_MONTH_SORT, Urls.URL_HOST, Urls.URL_CHART_Referer_MONTH, monthCallBack);
            OkHttpUtil.getResponseFromGET(Urls.URL_CHART_YEAR_SORT, Urls.URL_HOST, Urls.URL_CHART_Referer_YEAR, yearCallBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPager(String url, String key) {
        try {
            callBack = new RequestCallBack(key, this);
            callBack.setOnFinishRequestListener(this);
            OkHttpUtil.getResponseFromGET(url, callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterRequest(String sign) {
        Intent intent = new Intent();
        switch (sign) {
            case ShareKey.KEY_USER_HAVE:
                intent.setClass(this, Mine_Have.class);
                startActivity(intent);
                dialog.dismiss();
                break;
            case ShareKey.KEY_USER_NOW:
                intent.setClass(this, Mine_Now.class);
                startActivity(intent);
                dialog.dismiss();
                break;
            case ShareKey.KEY_USER_INFO:
                intent.setClass(this, Mine_Info.class);
                startActivity(intent);
                dialog.dismiss();
                break;
            case ShareKey.KEY_GSON_CLASS_SORT:
                setClassSort=true;
                if (isFinishSetChart()){
                    intent.setClass(this, Mine_Lend.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
                break;
            case ShareKey.KEY_GSON_MONTH_SORT:
                setMonthSort=true;
                if (isFinishSetChart()){
                    intent.setClass(this, Mine_Lend.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
                break;
            case ShareKey.KEY_GSON_YEAR_SORT:
                setYearSort=true;
                if (isFinishSetChart()){
                    intent.setClass(this, Mine_Lend.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
                break;
        }
    }

    private boolean isFinishSetChart(){
        if (setYearSort&&setMonthSort&&setClassSort){
            setYearSort=false;
            setMonthSort=false;
            setClassSort=false;
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (changeTheme) {
            initTheme();
            fragMainAdapter.clearFragment();
            initTabPager(0);
            ThemeUtil.setUserHead(this, nav_header_img);
            ThemeUtil.setUserTheme(this, nav_head_rela);
            changeTheme = false;
        }

    }
}
