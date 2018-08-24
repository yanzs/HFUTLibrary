package yanzs.hfutlibrary.activity.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.activity.inform.Inform_Detail;
import yanzs.hfutlibrary.adapter.MineNowAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_Mine_Now;
import yanzs.hfutlibrary.callBack.RequestCallBack;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnFinishRequestListener;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.OkHttpUtil;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Mine_Now extends BaseActivity implements OnItemClickListener,OnFinishRequestListener{
    @BindView(R.id.mine_now_recycle)
    RecyclerView mine_now_recycle;
    @BindView(R.id.mine_now_text_hint)
    TextView mine_now_text_hint;
    @BindView(R.id.mine_now_img_back)
    ImageView mine_now_img_back;
    private List<Bean_Mine_Now> dataList;
    private MineNowAdapter mineNowAdapter;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;

    @Override
    protected void initActivity() {
        dataList = JsoupUtil.getMineNow(this);
        if (dataList.size()>0){
            mine_now_text_hint.setVisibility(View.INVISIBLE);
            mineNowAdapter=new MineNowAdapter(dataList);
            mine_now_recycle.setLayoutManager(new LinearLayoutManager(this));
            mine_now_recycle.addItemDecoration(new GridItemDecoration(20));
            mine_now_recycle.setAdapter(mineNowAdapter);
            mineNowAdapter.setOnItemClickListener(this);
        }else {
            mine_now_text_hint.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.mine_now_img_back)
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mine_now_img_back:
                finish();
                break;
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_mine_now;
    }

    @Override
    public void itemClick(int pos) {
        dialog= DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
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
    public void afterRequest(String sign) {
        if (sign.equals(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL)){
            Intent intent=new Intent();
            intent.setClass(this,Inform_Detail.class);
            startActivity(intent);
            dialog.dismiss();
        }
    }
}
