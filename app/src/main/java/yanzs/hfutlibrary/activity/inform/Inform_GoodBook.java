package yanzs.hfutlibrary.activity.inform;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.InformGoodBookAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.bean.Bean_Inform_GoodBook;
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
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Inform_GoodBook extends BaseActivity implements View.OnClickListener, OnItemClickListener, OnFinishRequestListener {
    @BindView(R.id.inform_goodbook_img_back)
    ImageView goodbook_img_back;
    @BindView(R.id.inform_goodbook_img_inform)
    ImageView goodbook_img_inform;
    @BindView(R.id.inform_goodbook_text_title)
    TextView goodbook_text_title;
    @BindView(R.id.inform_goodbook_recycle)
    RecyclerView goodbook_recycle;

    private int pos;
    private List<Bean_Inform_GoodBook> dataList;
    private InformGoodBookAdapter informGoodBookAdapter;
    private ZLoadingDialog dialog;
    private RequestCallBack callBack;

    @Override
    protected void initActivity() {
        initPos();
        dataList = JsoupUtil.getInformGoodBook(this);
        informGoodBookAdapter = new InformGoodBookAdapter(dataList);
        informGoodBookAdapter.setOnItemClickListener(this);
        goodbook_recycle.setLayoutManager(new GridLayoutManager(this, 1));
        goodbook_recycle.addItemDecoration(new GridItemDecoration(16));
        goodbook_recycle.setAdapter(informGoodBookAdapter);
        goodbook_img_back.setOnClickListener(this);
        goodbook_img_inform.setOnClickListener(this);

    }

    private void initPos() {
        pos = getIntent().getIntExtra(Values.SORT_INTENT_POS_SIGN, -1);
        if (pos < 0) {
            Toast.makeText(this, Values.HINT_SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            goodbook_text_title.setText(Values.SORT_GOODBOOK_ITEM[pos]);
        }
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_goodbook;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.inform_goodbook_img_back:
                finish();
                break;
            case R.id.inform_goodbook_img_inform:
                DialogUtil.showInformDialog(this, Values.DETAIL_DIALOG_INFORM_GOODBOOK_TITLE, Values.DETAIL_DIALOG_INFORM_GOODBOOK_CONTANT);
                break;
        }
    }

    @Override
    public void itemClick(int pos) {
        dialog = DialogUtil.initLoadDialog(this, Values.HINT_DIALOG_LOAD);
        dialog.show();
        initPager(pos);
    }

    private void initPager(int pos) {
        String url = dataList.get(pos).getUrl();
        url= Urls.URL_ORIGIN+url;
        callBack = new RequestCallBack(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL, this);
        callBack.setOnFinishRequestListener(this);
        try {
            OkHttpUtil.getResponseFromGET(url, callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterRequest(String sign) {
        if (sign.equals(ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL)) {
            Intent intent = new Intent();
            intent.setClass(this, Inform_Detail.class);
            startActivity(intent);
            dialog.dismiss();
        }
    }
}
