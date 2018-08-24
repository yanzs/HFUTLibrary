package yanzs.hfutlibrary.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import yanzs.hfutlibrary.activity.inform.Inform_Search;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.post.RootPost;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.GsonUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class App_Search extends BaseActivity implements View.OnClickListener,OnFinishRequestListener {

    @BindView(R.id.search_edit_value)
    EditText edit_value;
    @BindView(R.id.search_img_act)
    ImageView img_act;
    @BindView(R.id.search_img_back)
    ImageView img_back;
    @BindView(R.id.search_spinner_local)
    AppCompatSpinner spinner_local;
    @BindView(R.id.search_spinner_way)
    AppCompatSpinner spinner_way;
    private RequestCallBack callBack;
    private ZLoadingDialog dialog;


    @Override
    protected void initActivity() {
        ArrayAdapter<String> spinnerLocalAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Values.SEARCH_LOCAL_ITEM);
        ArrayAdapter<String> spinnerWayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Values.SEARCH_WAY_ITEM);
        spinner_local.setAdapter(spinnerLocalAdapter);
        spinner_way.setAdapter(spinnerWayAdapter);
        img_act.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_search;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.search_img_act:
                dialog= DialogUtil.initLoadDialog(this,Values.HINT_DIALOG_LOAD);
                int local= (int) spinner_local.getSelectedItemId();
                int way= (int) spinner_way.getSelectedItemId();
                String s=edit_value.getText().toString();
                if (s.length()==0){
                    Toast.makeText(this, Values.HINT_INPUT_ERROR, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (way==0){
                    callBack=new RequestCallBack(ShareKey.KEY_SEARCH_PAGE_SIMPLE,this);
                    initSimpleSearch(s,Values.SEARCH_LOCAL_KEY_ITEM[local]);
                }else if (way==1){
                    callBack=new RequestCallBack(ShareKey.KEY_SEARCH_PAGE_LIB,this);
                    initLibSearch(s,Values.SEARCH_LOCAL_KEY_ITEM[local]);
                }
                break;
            case R.id.search_img_back:
                finish();
                break;
        }
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

    private void initLibSearch(String s,String local){
        ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_LOCAL,local);
        ShareUtil.storeLocalData(this,ShareKey.SHARED_KEY,ShareKey.KEY_SEARCH_KEY_SKEY,s);
        callBack.setOnFinishRequestListener(this);
        RootPost rootPost=new RootPost(s,1,local);
        String json= GsonUtil.getPostJson(rootPost);
        OkHttpUtil.getResponseFromPOST(callBack,json);
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
