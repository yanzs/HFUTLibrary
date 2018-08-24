package yanzs.hfutlibrary.activity.news;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.adapter.NewsListAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_News;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.listener.OnRefreshListener;
import yanzs.hfutlibrary.base.BaseScrollStateChanged;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class News_List extends BaseActivity implements OnRefreshListener, OnFinishRequestListener, OnItemClickListener {
    @BindView(R.id.news_list_refreshlayout)
    SwipeRefreshLayout news_list_refreshlayout;
    @BindView(R.id.news_list_recycle)
    RecyclerView news_list_recycle;
    @BindView(R.id.news_list_text_title)
    TextView newbook_text_title;
    @BindView(R.id.news_list_img_back)
    ImageView news_img_back;

    private int pos;
    private GridLayoutManager layoutManager;
    private int currPager = 1;
    private int pagerNum = 1;
    private boolean isDoing;
    private NewsListAdapter newsListAdapter;
    private BaseScrollStateChanged stateChanged;
    private List<Bean_News> dataList;
    private String baseUrl = "";
    private String sKey = "";
    private boolean isLib = false;
    private RequestCallBack callBack;
    private boolean isMore;

    private void initPos() {
        pos = getIntent().getIntExtra(Values.NEWS_INTENT_SIGN, -1);
        if (pos < 0) {
            Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            newbook_text_title.setText(Values.TAB_NEWS_ITEM_TITLE[pos]);
        }
        switch (pos) {
            case 0:
                baseUrl = Urls.URL_EDU_NEWS_HOST;
                sKey = ShareKey.KEY_NEWS_PAGE_EDU_NEWS;
                break;
            case 1:
                baseUrl = Urls.URL_EDU_INFO_HOST;
                sKey = ShareKey.KEY_NEWS_PAGE_EDU_INFO;
                break;
            case 2:
                baseUrl = Urls.URL_LIB_NEWS_HOST;
                sKey = ShareKey.KEY_NEWS_PAGE_LIB_NEWS;
                break;
        }
    }

    @Override
    protected void initActivity() {
        initPos();
        pagerNum = JsoupUtil.getNewsPager(this, sKey);
        System.out.println(pagerNum);
        if (sKey.equals(ShareKey.KEY_NEWS_PAGE_LIB_NEWS)) {
            dataList = JsoupUtil.getNewsLib(this);
            isMore = pagerNum > currPager;
            isLib = true;
        } else {
            dataList = JsoupUtil.getNewsEdu(this, sKey);
            isMore = pagerNum > currPager;
            isLib = false;
        }
        news_list_refreshlayout.setEnabled(false);
        newsListAdapter = new NewsListAdapter(dataList, isMore, this);
        newsListAdapter.setOnItemClickListener(this);
        layoutManager = new GridLayoutManager(this, 1);
        news_list_recycle.setLayoutManager(layoutManager);
        news_list_recycle.setAdapter(newsListAdapter);
        stateChanged = new BaseScrollStateChanged(newsListAdapter, layoutManager);
        stateChanged.setOnRefreshListener(this);
        news_list_recycle.addOnScrollListener(stateChanged);

        if (pagerNum > 1) {
            initNextPager();
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_news_list;
    }

    @Override
    public void refreshView() {
        List<Bean_News> newData = getNextPagerData();
        if (newData.size() > 0) {
            newsListAdapter.updataList(newData, true);
        } else {
            newsListAdapter.updataList(null, false);
        }
    }

    private List<Bean_News> getNextPagerData() {
        if (pagerNum >= currPager && !isDoing) {

            List<Bean_News> newData;
            if (isLib) {
                newData = JsoupUtil.getNewsLib(this);
            } else {
                newData = JsoupUtil.getNewsEdu(this, sKey);
            }

            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, sKey, "");
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
        ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, sKey, "");
        String url = baseUrl + "" + currPager + ".htm";
        if (!isDoing) {
            isDoing = true;
            callBack = new RequestCallBack(sKey, this);
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

    @OnClick(R.id.news_list_img_back)
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.news_list_img_back:
                finish();
                break;
        }
    }

    @Override
    public void itemClick(int pos) {
        Intent intent = new Intent();
        intent.setClass(this, News_Detail.class);
        intent.putExtra(Values.NEWS_INTENT_PAGER_SIGN,dataList.get(pos).getUrl());
        startActivity(intent);
    }
}
