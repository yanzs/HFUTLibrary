package yanzs.hfutlibrary.activity.inform;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.adapter.AppSortAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Inform_Sort extends BaseActivity implements OnItemClickListener,OnFinishRequestListener{
    @BindView(R.id.sort_recycle)
    RecyclerView sort_recycle;
    @BindView(R.id.sort_img_back)
    ImageView sort_img_back;
    @BindView(R.id.sort_img_inform)
    ImageView sort_img_inform;
    private AppSortAdapter appSortAdapter;
    private int sortKind;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;
    private int pos;
    @Override
    protected void initActivity() {
        initKind();
        sort_recycle.setLayoutManager(new GridLayoutManager(this,3));
        sort_recycle.setAdapter(appSortAdapter);
        sort_recycle.addItemDecoration(new GridItemDecoration(20));
        appSortAdapter.setOnItemClickListener(this);

    }

    private void initKind(){
        sortKind=getIntent().getIntExtra(Values.SORT_INTENT_SIGN,0);
        if (sortKind==0){
            Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            if (sortKind==Values.SORT_INTENT_SIGN_NEWBOOK){
                appSortAdapter =new AppSortAdapter(Values.SORT_NEWBOOK_ITEM);
            }else if (sortKind==Values.SORT_INTENT_SIGN_GOODBOOK){
                appSortAdapter =new AppSortAdapter(Values.SORT_GOODBOOK_ITEM);
            }
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_sort;
    }

    @Override
    public void itemClick(int pos) {
        dialog=DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
        dialog.show();
        initPager(pos);

    }


    private void initPager(int pos) {
        String url="";
        this.pos=pos;
        if (sortKind==Values.SORT_INTENT_SIGN_GOODBOOK){
            url=Urls.URL_INFORM_PAGER_GOODBOOK+Values.INFORM_GOODBOOK_URL_ITEM[pos];
            callBack=new RequestCallBack(ShareKey.KEY_INFORM_PAGE_GOOD_BOOK,this);
        }else {
            String key=Values.SORT_NEWBOOK_KEY[pos];
            String value= URLEncoder.encode(Values.SORT_NEWBOOK_ITEM[pos]);
            url=Urls.URL_INFORM_PAGER_NEWBOOK+"&cls="+key+"&clsname="+value;
            callBack=new RequestCallBack(ShareKey.KEY_INFORM_PAGE_NEW_BOOK,this);
        }
        callBack.setOnFinishRequestListener(this);
        try {
            OkHttpUtil.getResponseFromGET(url,callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.sort_img_back,R.id.sort_img_inform})
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.sort_img_back:
                finish();
                break;
            case R.id.sort_img_inform:
                DialogUtil.showInformDialog(this,Values.DETAIL_DIALOG_SORT_TITLE,Values.DETAIL_DIALOG_SORT_CONTANT);
                break;
        }

    }

    @Override
    public void afterRequest(String sign) {
        Intent intent=new Intent();
        if (sign.equals(ShareKey.KEY_INFORM_PAGE_GOOD_BOOK)){
            intent.setClass(this, Inform_GoodBook.class);
        }else if (sign.equals(ShareKey.KEY_INFORM_PAGE_NEW_BOOK)){
            intent.setClass(this, Inform_NewBook.class);
        }
        intent.putExtra(Values.SORT_INTENT_POS_SIGN,pos);
        startActivity(intent);
        dialog.dismiss();
    }
}
