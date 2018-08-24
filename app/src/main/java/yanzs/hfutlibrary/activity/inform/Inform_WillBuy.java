package yanzs.hfutlibrary.activity.inform;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.adapter.InformWillBuyAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.base.BaseScrollStateChanged;
import yanzs.hfutlibrary.bean.Bean_Inform_WillBuy;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnRefreshListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class Inform_WillBuy extends BaseActivity implements OnRefreshListener, OnFinishRequestListener{
    @BindView(R.id.inform_willbuy_refreshlayout)
    SwipeRefreshLayout willbuy_refreshlayout;
    @BindView(R.id.inform_willbuy_recycle)
    RecyclerView willbuy_recycle;
    @BindView(R.id.inform_willbuy_img_back)
    ImageView willbuy_img_back;

    private GridLayoutManager layoutManager;
    private int currPager = 1;
    private int pagerNum = 1;
    private boolean isDoing;
    private InformWillBuyAdapter informWillBuyAdapter;
    private BaseScrollStateChanged stateChanged;
    private List<Bean_Inform_WillBuy> dataList;
    private RequestCallBack callBack;
    private boolean isMore;


    @Override
    protected void initActivity() {
        pagerNum = JsoupUtil.getInformWillBuyPager(this);
        dataList = JsoupUtil.getInformWillBuy(this);
        isMore = pagerNum > currPager;
        willbuy_refreshlayout.setEnabled(false);
        informWillBuyAdapter = new InformWillBuyAdapter(dataList,isMore);
        layoutManager = new GridLayoutManager(this, 1);
        willbuy_recycle.setLayoutManager(layoutManager);
        willbuy_recycle.setAdapter(informWillBuyAdapter);
        stateChanged=new BaseScrollStateChanged(informWillBuyAdapter,layoutManager);
        stateChanged.setOnRefreshListener(this);
        willbuy_recycle.addOnScrollListener(stateChanged);
        if (pagerNum > 1) {
            initNextPager();
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_willbuy;
    }

    @Override
    public void refreshView() {
        List<Bean_Inform_WillBuy> newData = getNextPagerData();
        if (newData.size() > 0) {
            informWillBuyAdapter.updataList(newData, true);
        } else {
            informWillBuyAdapter.updataList(null, false);
        }
    }

    private List<Bean_Inform_WillBuy> getNextPagerData() {
        if (pagerNum >= currPager && !isDoing) {
            List<Bean_Inform_WillBuy> newData;
            newData=JsoupUtil.getInformWillBuy(this);
            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_WILL_BUY, "");
            if (currPager < pagerNum) {
                initNextPager();
            }
            return newData;
        } else {
            return new ArrayList<>();
        }
    }

    private void initNextPager() {
        currPager++;
        ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_WILL_BUY, "");
        String url = Urls.URL_INFORM_PAGER_WILLBUY + "?page=" + currPager;
        if (!isDoing) {
            isDoing = true;
            callBack = new RequestCallBack(ShareKey.KEY_INFORM_PAGE_WILL_BUY, this);
            callBack.setOnFinishRequestListener(this);
            try {
                OkHttpUtil.getResponseFromGET(url, callBack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterRequest(String sign) {
        isDoing = false;
    }

    @OnClick(R.id.inform_willbuy_img_back)
    public void onClick(View v) {
        if (v.getId()==R.id.inform_willbuy_img_back){
            finish();
        }
    }
}
