package yanzs.hfutlibrary.activity.mine;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.adapter.FragMainAdapter;
import yanzs.hfutlibrary.adapter.FragMineAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.responsemine.Rootclasssort;
import yanzs.hfutlibrary.bean.responsemine.Rootmonthsort;
import yanzs.hfutlibrary.bean.responsemine.Rootyearsort;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;

public class Mine_Lend extends BaseActivity {

    @BindViews({R.id.mine_lend_img_column, R.id.mine_lend_img_line, R.id.mine_lend_img_pie})
    List<ImageView> mine_lend_img = new ArrayList<>();
    @BindViews({R.id.mine_lend_text_column, R.id.mine_lend_text_line, R.id.mine_lend_text_pie})
    List<TextView> mine_lend_text = new ArrayList<>();
    @BindViews({R.id.mine_lend_rela_column, R.id.mine_lend_rela_line, R.id.mine_lend_rela_pie})
    List<RelativeLayout> mine_lend_rela = new ArrayList<>();
    @BindView(R.id.mine_lend_text_title)
    TextView mine_lend_text_title;
    @BindView(R.id.mine_lend_img_back)
    ImageView mine_lend_img_back;
    @BindView(R.id.mine_lend_img_inform)
    ImageView mine_lend_img_inform;
    private FragMineAdapter fragMineAdapter;

    @Override
    protected void initActivity() {
        fragMineAdapter = new FragMineAdapter(getSupportFragmentManager());
        initTabPager(0);
    }

    private void initTabPager(int n) {
        fragMineAdapter.loadFragment(n);
        for (int i = 0; i < 3; i++) {
            if (i == n) {
                mine_lend_img.get(i).setColorFilter(ColorUtil.getThemeColor(this));
                mine_lend_text.get(i).setTextColor(ColorUtil.getThemeColor(this));
            } else {
                mine_lend_img.get(i).setColorFilter(Color.GRAY);
                mine_lend_text.get(i).setTextColor(Color.GRAY);
            }
        }
        mine_lend_text_title.setText(Values.MINE_LEND_TITLE[n]);
    }

    @OnClick({R.id.mine_lend_img_back,R.id.mine_lend_rela_column, R.id.mine_lend_rela_line, R.id.mine_lend_rela_pie,R.id.mine_lend_img_inform})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mine_lend_rela_column:
                initTabPager(0);
                break;
            case R.id.mine_lend_rela_line:
                initTabPager(1);
                break;
            case R.id.mine_lend_rela_pie:
                initTabPager(2);
                break;
            case R.id.mine_lend_img_back:
                finish();
                break;
            case R.id.mine_lend_img_inform:
                DialogUtil.showInformDialog(this,Values.DETAIL_DIALOG_MINE_LEND_TITLE,Values.DETAIL_DIALOG_MINE_LEND_CONTANT);
                break;
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_mine_lend;
    }

    @Override
    protected void onDestroy() {
        fragMineAdapter.clearFragment();
        super.onDestroy();

    }
}
