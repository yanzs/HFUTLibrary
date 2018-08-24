package yanzs.hfutlibrary.activity.set;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.adapter.SetAboutAdpter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.util.ColorUtil;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.TencentUtil;

public class Set_About extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.set_about_img_ic)
    ImageView set_about_img_ic;
    @BindView(R.id.set_about_recycler)
    RecyclerView set_about_recycler;
    private SetAboutAdpter setAboutAdpter;

    @Override
    protected void initActivity() {
        set_about_img_ic.setColorFilter(ColorUtil.getThemeColor(this));
        set_about_recycler.setLayoutManager(new LinearLayoutManager(this));
        setAboutAdpter = new SetAboutAdpter();
        setAboutAdpter.setOnItemClickListener(this);
        set_about_recycler.setAdapter(setAboutAdpter);
    }

    @OnClick({R.id.set_about_img_back, R.id.set_about_img_inform})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.set_about_img_back:
                finish();
                break;
            case R.id.set_about_img_inform:
                DialogUtil.showInformDialog(this,Values.DETAIL_DIALOG_SET_ABOUT_INFORM_TITLE,Values.DETAIL_DIALOG_SET_ABOUT_INFORM_CONTANT);
                break;
        }
    }


    @Override
    protected int getResId() {
        return R.layout.module_activity_set_about;
    }

    @Override
    public void itemClick(int pos) {
        switch (pos) {
            case 1:
                boolean qq = TencentUtil.joinQQGroup(Values.TENTENT_KEY, this);
                if (!qq) {
                    Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_SET_ABOUT_THANKS_TITLE, Values.DETAIL_DIALOG_SET_ABOUT_THANKS_CONTANT);
                break;
        }
    }
}
