package yanzs.hfutlibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import yanzs.hfutlibrary.activity.inform.Inform_Sort;
import yanzs.hfutlibrary.activity.inform.Inform_HotTop;
import yanzs.hfutlibrary.activity.inform.Inform_WillBuy;
import yanzs.hfutlibrary.adapter.TabInformAdapter;
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

public class Frag_Inform extends BaseFragment implements OnItemClickListener, OnFinishRequestListener {
    @BindView(R.id.tab_recycle_inform)
    RecyclerView tab_recycle_inform;
    private ZLoadingDialog dialog;
    private TabInformAdapter tabInformAdapter;
    private RequestCallBack requestCallBack;
    private Context context;

    @Override
    protected void initFragment() {
        context = getContext();
        tabInformAdapter = new TabInformAdapter(getContext());
        tabInformAdapter.setOnItemClickListener(this);
        tab_recycle_inform.setLayoutManager(new GridLayoutManager(getContext(), 1));
        tab_recycle_inform.setAdapter(tabInformAdapter);
    }

    @Override
    protected int getResId() {
        return R.layout.module_fragment_main_tab_inform;
    }

    @Override
    public void itemClick(int pos) {
        Intent intent = new Intent();
        switch (pos) {
            case 0:
            case 2:
                dialog=DialogUtil.initLoadDialog(context, Values.HINT_DIALOG_LOAD);
                initPager(pos);
                break;
            case 1:
            case 3:
                intent.setClass(context, Inform_Sort.class);
                intent.putExtra(Values.SORT_INTENT_SIGN, pos);
                startActivity(intent);
                break;
        }

    }

    private void initPager(int pos) {
        String url = null, key = null;
        if (pos == 0) {
            url = Urls.URL_INFORM_PAGER_TOPHOT;
            key = ShareKey.KEY_INFORM_PAGE_TOP_HOT;
        }else if (pos == 2){
            url=Urls.URL_INFORM_PAGER_WILLBUY;
            key=ShareKey.KEY_INFORM_PAGE_WILL_BUY;
        }
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
        if (sign.equals(ShareKey.KEY_INFORM_PAGE_TOP_HOT)) {
            intent.setClass(context, Inform_HotTop.class);
        }else if (sign.equals(ShareKey.KEY_INFORM_PAGE_WILL_BUY)){
            intent.setClass(context, Inform_WillBuy.class);
        }
        startActivity(intent);
        dialog.dismiss();
    }
}
