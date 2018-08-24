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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.InformNewBookAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_Inform_NewBook;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.listener.OnRefreshListener;
import yanzs.hfutlibrary.base.BaseScrollStateChanged;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Inform_NewBook extends BaseActivity implements OnRefreshListener,OnFinishRequestListener,OnItemClickListener,View.OnClickListener {
    @BindView(R.id.inform_newbook_refreshlayout)
    SwipeRefreshLayout newbook_refreshlayout;
    @BindView(R.id.inform_newbook_recycle)
    RecyclerView newbook_recycle;
    @BindView(R.id.inform_newbook_text_title)
    TextView newbook_text_title;
    @BindView(R.id.inform_newbook_img_back)
    ImageView newbook_img_back;
    @BindView(R.id.inform_newbook_img_inform)
    ImageView newbook_img_inform;

    private int pos;
    private GridLayoutManager layoutManager;
    private int currPager=1;
    private int pagerNum=1;
    private boolean isDoing;
    private InformNewBookAdapter informNewBookAdapter;
    private BaseScrollStateChanged stateChanged;
    private List<Bean_Inform_NewBook> dataList;
    private String baseUrl="";
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;
    private boolean isMore;

    private void initPos() {
        pos = getIntent().getIntExtra(Values.SORT_INTENT_POS_SIGN, -1);
        if (pos < 0) {
            Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            newbook_text_title.setText(Values.SORT_NEWBOOK_ITEM[pos]);
        }
        String key=Values.SORT_NEWBOOK_KEY[pos];
        String value= URLEncoder.encode(Values.SORT_NEWBOOK_ITEM[pos]);
        baseUrl= Urls.URL_INFORM_PAGER_NEWBOOK+"&cls="+key+"&clsname="+value;
    }

    @Override
    protected void initActivity() {
        initPos();
        pagerNum=JsoupUtil.getInformNewBookPager(this);
        dataList = JsoupUtil.getInformNewBook(this);
        isMore=dataList.size()>7;
        newbook_refreshlayout.setEnabled(false);
        informNewBookAdapter = new InformNewBookAdapter(dataList,isMore);
        informNewBookAdapter.setOnItemClickListener(this);
        layoutManager=new GridLayoutManager(this, 1);
        newbook_recycle.setLayoutManager(layoutManager);
        newbook_recycle.addItemDecoration(new GridItemDecoration(16));
        newbook_recycle.setAdapter(informNewBookAdapter);
        stateChanged=new BaseScrollStateChanged(informNewBookAdapter,layoutManager);
        stateChanged.setOnRefreshListener(this);
        newbook_recycle.addOnScrollListener(stateChanged);
        if (pagerNum>1){
            initNextPager();
        }
        newbook_img_back.setOnClickListener(this);
        newbook_img_inform.setOnClickListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_newbook;
    }

    @Override
    public void refreshView() {
        List<Bean_Inform_NewBook> newData = getNextPagerData();
        if (newData.size() > 0) {
            informNewBookAdapter.updataList(newData, true);
        } else {
            informNewBookAdapter.updataList(null, false);
        }
    }

    private List<Bean_Inform_NewBook> getNextPagerData() {
        if (pagerNum>=currPager&&!isDoing){
            List<Bean_Inform_NewBook> newData;
            newData=JsoupUtil.getInformNewBook(this);
            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,ShareKey.KEY_INFORM_PAGE_NEW_BOOK,"");
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
        ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY,ShareKey.KEY_INFORM_PAGE_NEW_BOOK,"");
        String url=baseUrl+"&page="+currPager;
        if (!isDoing){
            isDoing=true;
            callBack=new RequestCallBack(ShareKey.KEY_INFORM_PAGE_NEW_BOOK,this);
            callBack.setOnFinishRequestListener(this);
            try {
                OkHttpUtil.getResponseFromGET(url,callBack);
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
        dialog=DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
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
            case R.id.inform_newbook_img_back:
                finish();
                break;
            case R.id.inform_newbook_img_inform:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_INFORM_NEWBOOK_TITLE, Values.DETAIL_DIALOG_INFORM_NEWBOOK_CONTANT);
                break;
        }
    }
}
