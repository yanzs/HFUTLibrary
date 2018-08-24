package yanzs.hfutlibrary.activity.inform;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.InformHotTopAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_Inform_HotTop;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class Inform_HotTop extends BaseActivity implements View.OnClickListener,OnItemClickListener,OnFinishRequestListener{
    @BindView(R.id.inform_hottop_recycle)
    RecyclerView hottop_recycle;
    @BindView(R.id.inform_hottop_img_back)
    ImageView hottop_img_back;
    @BindView(R.id.inform_hottop_img_inform)
    ImageView hottop_img_inform;
    private InformHotTopAdapter informHotTopAdapter;
    private List<Bean_Inform_HotTop> dataList;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;

    @Override
    protected void initActivity() {
        dataList=JsoupUtil.getInformHotTop(this);
        informHotTopAdapter=new InformHotTopAdapter(dataList,this);
        hottop_recycle.setLayoutManager(new GridLayoutManager(this,1));
        informHotTopAdapter.setOnItemClickListener(this);
        hottop_recycle.setAdapter(informHotTopAdapter);
        hottop_img_back.setOnClickListener(this);
        hottop_img_inform.setOnClickListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_hottop;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.inform_hottop_img_back:
                finish();
                break;
            case R.id.inform_hottop_img_inform:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_INFORM_HOTTOP_TITLE,Values.DETAIL_DIALOG_INFORM_HOTTOP_CONTANT);
                break;
        }
    }

    @Override
    public void itemClick(int pos) {
        dialog= DialogUtil.initLoadDialog(this,Values.HINT_DIALOG_LOAD);
        String s=dataList.get(pos).getValue();
        callBack=new RequestCallBack(ShareKey.KEY_SEARCH_PAGE_SIMPLE,this);
        initSimpleSearch(s,"ALL");
    }

    private void initSimpleSearch(String s,String local){
        ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_LOCAL,local);
        ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_SKEY,s);
        String url= Urls.URL_SEARCH_SIMPLE+"&location="+local+"&strText="+s;
        callBack.setOnFinishRequestListener(this);
        try {
            OkHttpUtil.getResponseFromGET(url, callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterRequest(String sign) {
        Intent intent=new Intent();
        intent.setClass(this, Inform_Search.class);
        intent.putExtra(Values.SEARCH_INTENT_SIGN,sign);
        startActivity(intent);
        dialog.dismiss();
    }
}
