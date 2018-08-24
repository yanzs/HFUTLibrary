package yanzs.hfutlibrary.activity.inform;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.adapter.InformDetailAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_Inform_Detail;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnDetailScrollStateChangedListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.StatusBarUtil;

public class Inform_Detail extends BaseActivity{

    @BindView(R.id.inform_detail_rela_toolbar)
    RelativeLayout detail_rela_toolbar;
    @BindView(R.id.inform_detail_img_header)
    ImageView detail_img_header;
    @BindView(R.id.inform_detail_recycle)
    RecyclerView detail_recycle;
    @BindView(R.id.inform_detail_img_back)
    ImageView detail_img_back;
    @BindView(R.id.inform_detail_img_inform)
    ImageView detail_img_inform;
    private InformDetailAdapter informDetailAdapter;
    private Bean_Inform_Detail data;

    @Override
    protected void initActivity() {
        data= JsoupUtil.getInformBookDetail(this);
        informDetailAdapter = new InformDetailAdapter(this,detail_img_header,data,data.getBeanInformLocals());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detail_recycle.setLayoutManager(layoutManager);
        detail_recycle.setAdapter(informDetailAdapter);

        // toolbar 的高
        int toolbarHeight = detail_rela_toolbar.getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + getStatusBarHeight();
        ViewGroup.LayoutParams params = detail_img_header.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = headerBgHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            detail_img_header.setImageAlpha(0);
        }
        StatusBarUtil.setTranslucentImageHeader(this, 0, detail_rela_toolbar);
        detail_recycle.addOnScrollListener(new OnDetailScrollStateChangedListener(detail_img_header));
    }


    private int getStatusBarHeight() {
        // 获得状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_detail;
    }


    @OnClick({R.id.inform_detail_img_inform,R.id.inform_detail_img_back})
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.inform_detail_img_back:
                finish();
                break;
            case R.id.inform_detail_img_inform:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_INFORM_DETAIL_TITLE,Values.DETAIL_DIALOG_INFORM_DETAIL_CONTANT);
        }
    }
}
