package yanzs.hfutlibrary.activity.mine;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.activity.inform.Inform_Detail;
import yanzs.hfutlibrary.adapter.MineHaveAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.base.BaseScrollStateChanged;
import yanzs.hfutlibrary.bean.Bean_Mine_Have;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.listener.OnRefreshListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Mine_Have extends BaseActivity implements OnRefreshListener,OnFinishRequestListener,OnItemClickListener,View.OnClickListener {

    @BindView(R.id.mine_have_refreshlayout)
    SwipeRefreshLayout have_refreshlayout;
    @BindView(R.id.mine_have_recycle)
    RecyclerView have_recycle;
    @BindView(R.id.mine_have_img_back)
    ImageView have_img_back;
    private GridLayoutManager layoutManager;
    private int currPager=1;
    private int pagerNum=1;
    private boolean isDoing;
    private MineHaveAdapter mineHaveAdapter;
    private BaseScrollStateChanged stateChanged;
    private List<Bean_Mine_Have> dataList;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;
    private boolean isMore;

    @Override
    protected void initActivity() {

        pagerNum = JsoupUtil.getMineHavePager(this);
        dataList = JsoupUtil.getMineHave(this);
        isMore=pagerNum>currPager;
        have_refreshlayout.setEnabled(false);
        mineHaveAdapter = new MineHaveAdapter(dataList,isMore);
        mineHaveAdapter.setOnItemClickListener(this);
        layoutManager=new GridLayoutManager(this, 1);
        have_recycle.setLayoutManager(layoutManager);
        have_recycle.addItemDecoration(new GridItemDecoration(16));
        have_recycle.setAdapter(mineHaveAdapter);
        stateChanged=new BaseScrollStateChanged(mineHaveAdapter,layoutManager);
        stateChanged.setOnRefreshListener(this);
        have_img_back.setOnClickListener(this);
        have_recycle.addOnScrollListener(stateChanged);
        if (pagerNum>1){
            initNextPager();
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_mine_have;
    }

    @Override
    public void refreshView() {
        List<Bean_Mine_Have> newData = getNextPagerData();
        if (newData.size() > 0) {
            mineHaveAdapter.updataList(newData, true);
        } else {
            mineHaveAdapter.updataList(null, false);
        }
    }

    private List<Bean_Mine_Have> getNextPagerData() {
        if (pagerNum>=currPager&&!isDoing){
            List<Bean_Mine_Have> newData;
            newData = JsoupUtil.getMineHave(this);
            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,ShareKey.KEY_USER_HAVE,"");
            if (currPager<pagerNum){
                initNextPager();
            }
            return newData;
        }else {
            return new ArrayList<>();
        }
    }

    private void initNextPager() {
        currPager++;
        ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,ShareKey.KEY_USER_HAVE,"");
        if (!isDoing){
            isDoing=true;
            callBack=new RequestCallBack(ShareKey.KEY_USER_HAVE,this);
            callBack.setOnFinishRequestListener(this);
            String url= Urls.URL_USER_HAVE+"?page="+currPager;
            try {
                OkHttpUtil.getResponseFromGET(url, callBack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterRequest(String sign) {
        if (sign.equals(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL)){
            Intent intent=new Intent();
            intent.setClass(this,Inform_Detail.class);
            startActivity(intent);
            dialog.dismiss();
        }else {
            isDoing=false;
        }

    }

    @Override
    public void itemClick(int pos) {
        dialog= DialogUtil.initLoadDialog(this,Values.HINT_DIALOG_LOAD);
        dialog.show();
        initPager(pos);
    }

    private void initPager(int pos) {
        String url= dataList.get(pos).getUrl();
        url=Urls.URL_ORIGIN+url;
        callBack=new RequestCallBack(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL,this);
        callBack.setOnFinishRequestListener(this);
        try {
            OkHttpUtil.getResponseFromGET(url,callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.mine_have_img_back:
                finish();
                break;
        }
    }
}
