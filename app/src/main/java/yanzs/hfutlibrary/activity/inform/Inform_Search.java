package yanzs.hfutlibrary.activity.inform;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.InformSearchAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.base.BaseScrollStateChanged;
import yanzs.hfutlibrary.bean.Bean_Inform_Search;
import yanzs.hfutlibrary.bean.post.RootPost;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.listener.OnRefreshListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.GsonUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Inform_Search extends BaseActivity implements OnRefreshListener,OnFinishRequestListener,OnItemClickListener ,View.OnClickListener{
    @BindView(R.id.inform_search_refreshlayout)
    SwipeRefreshLayout search_refreshlayout;
    @BindView(R.id.inform_search_recycle)
    RecyclerView search_recycle;
    @BindView(R.id.inform_search_text_title)
    TextView search_text_title;
    @BindView(R.id.inform_search_img_back)
    ImageView search_img_back;
    @BindView(R.id.inform_search_img_inform)
    ImageView search_img_inform;

    private String signs;
    private GridLayoutManager layoutManager;
    private int currPager=1;
    private int pagerNum=1;
    private boolean isDoing;
    private InformSearchAdapter informSearchAdapter;
    private BaseScrollStateChanged stateChanged;
    private List<Bean_Inform_Search> dataList;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;
    private boolean isMore;

    private void initKind() {
        signs = getIntent().getStringExtra(Values.SEARCH_INTENT_SIGN);
        if (signs.length() == 0) {
            Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (signs.equals(ShareKey.KEY_SEARCH_PAGE_LIB)){
                search_text_title.setText(Values.SEARCH_WAY_ITEM[1]);
            }else {
                search_text_title.setText(Values.SEARCH_WAY_ITEM[0]);
            }
        }
    }

    @Override
    protected void initActivity() {
        initKind();
        if (signs.equals(ShareKey.KEY_SEARCH_PAGE_LIB)){
            pagerNum= JsoupUtil.getLibSearchPager(this);
            dataList = JsoupUtil.getInformLibSearch(this);
        }else {
            pagerNum = JsoupUtil.getSimpleSearchPager(this);
            dataList = JsoupUtil.getInformSimpleSearch(this);
        }
        isMore=pagerNum>currPager;
        search_refreshlayout.setEnabled(false);
        informSearchAdapter = new InformSearchAdapter(dataList,isMore);
        informSearchAdapter.setOnItemClickListener(this);
        layoutManager=new GridLayoutManager(this, 1);
        search_recycle.setLayoutManager(layoutManager);
        search_recycle.addItemDecoration(new GridItemDecoration(16));
        search_recycle.setAdapter(informSearchAdapter);
        stateChanged=new BaseScrollStateChanged(informSearchAdapter,layoutManager);
        stateChanged.setOnRefreshListener(this);
        search_recycle.addOnScrollListener(stateChanged);
        if (pagerNum>1){
            initNextPager();
        }else {
            currPager++;
        }
        search_img_back.setOnClickListener(this);
        search_img_inform.setOnClickListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_search;
    }

    @Override
    public void refreshView() {
        List<Bean_Inform_Search> newData = getNextPagerData();
        if (newData.size() > 0) {
            informSearchAdapter.updataList(newData, true);
        } else {
            informSearchAdapter.updataList(null, false);
        }
    }

    private List<Bean_Inform_Search> getNextPagerData() {
        if (pagerNum>=currPager&&!isDoing){
            List<Bean_Inform_Search> newData;
            if (signs.equals(ShareKey.KEY_SEARCH_PAGE_LIB)){
                newData = JsoupUtil.getInformLibSearch(this);
            }else {
                newData = JsoupUtil.getInformSimpleSearch(this);
            }
            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,signs,"");
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
        ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,signs,"");
        if (!isDoing){
            isDoing=true;
            callBack=new RequestCallBack(signs,this);
            callBack.setOnFinishRequestListener(this);
            if (signs.equals(ShareKey.KEY_SEARCH_PAGE_LIB)){
                initLibSearch();
            }else {
                initSimpleSearch();
            }
        }
    }

    private void initSimpleSearch(){
        String local=ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_LOCAL);
        String s=ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_SKEY);
        String url= Urls.URL_SEARCH_SIMPLE+"&doctype="+local+"&strText="+s+"&page="+currPager;
        try {
            OkHttpUtil.getResponseFromGET(url, callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLibSearch(){
        String local=ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_LOCAL);
        String s=ShareUtil.loadStringData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_SKEY);
        RootPost rootPost=new RootPost(s,currPager,local);
        String json= GsonUtil.getPostJson(rootPost);
        OkHttpUtil.getResponseFromPOST(callBack,json);
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
            case R.id.inform_search_img_back:
                finish();
                break;
            case R.id.inform_search_img_inform:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_INFORM_SEARCH_TITLE, Values.DETAIL_DIALOG_INFORM_SEARCH_CONTANT);
                break;
        }
    }
}
