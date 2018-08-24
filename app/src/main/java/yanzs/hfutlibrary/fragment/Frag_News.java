package yanzs.hfutlibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import yanzs.hfutlibrary.activity.news.News_List;
import yanzs.hfutlibrary.adapter.TabNewsAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;

public class Frag_News extends BaseFragment implements OnItemClickListener,OnFinishRequestListener {
    @BindView(R.id.tab_recycle_news)
    RecyclerView tab_recycle_news;
    private ZLoadingDialog dialog;
    private TabNewsAdapter tabNewsAdapter;
    private RequestCallBack requestCallBack;
    private Context context;
    @Override
    protected void initFragment() {
        context = getContext();
        tabNewsAdapter = new TabNewsAdapter(getContext());
        tabNewsAdapter.setOnItemClickListener(this);
        tab_recycle_news.setLayoutManager(new GridLayoutManager(getContext(), 1));
        tab_recycle_news.setAdapter(tabNewsAdapter);
    }

    @Override
    protected int getResId() {
        return R.layout.module_fragment_main_tab_news;
    }

    @Override
    public void itemClick(int pos) {
        dialog= DialogUtil.initLoadDialog(context, Values.HINT_DIALOG_LOAD);
        switch (pos){
            case 0:
                initPager(Urls.URL_EDU_NEWS,ShareKey.KEY_NEWS_PAGE_EDU_NEWS);
                break;
            case 1:
                initPager(Urls.URL_EDU_INFO,ShareKey.KEY_NEWS_PAGE_EDU_INFO);
                break;
            case 2:
                initPager(Urls.URL_LIB_NEWS,ShareKey.KEY_NEWS_PAGE_LIB_NEWS);
                break;
        }
    }

    private void initPager(String url, String key){
        try {
            requestCallBack = new RequestCallBack(key, context);
            requestCallBack.setOnFinishRequestListener(this);
            OkHttpUtil.getResponseFromGET(url, requestCallBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterRequest(String sign) {
        Intent intent = new Intent();
        intent.setClass(context, News_List.class);
        if (sign.equals(ShareKey.KEY_NEWS_PAGE_EDU_NEWS)) {
            intent.putExtra(Values.NEWS_INTENT_SIGN,0);
        }else if (sign.equals(ShareKey.KEY_NEWS_PAGE_EDU_INFO)){
            intent.putExtra(Values.NEWS_INTENT_SIGN,1);
        }else {
            intent.putExtra(Values.NEWS_INTENT_SIGN,2);
        }
        startActivity(intent);
        dialog.dismiss();
    }
}
