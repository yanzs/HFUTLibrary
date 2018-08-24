package yanzs.hfutlibrary.activity.inform;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.InformShowAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.view.GridItemDecoration;

public class Inform_Show extends BaseActivity{

    @BindView(R.id.inform_show_recycler)
    RecyclerView inform_show_recycler;
    @BindView(R.id.inform_show_img_back)
    ImageView inform_show_img_back;
    @BindView(R.id.inform_show_rela_back)
    RelativeLayout inform_show_rela_back;
    @BindView(R.id.inform_show_text_download)
    TextView inform_show_text_download;
    private InformShowAdapter informShowAdapter;

    @Override
    protected void initActivity() {
        inform_show_recycler.setLayoutManager(new GridLayoutManager(this,1));
        inform_show_recycler.addItemDecoration(new GridItemDecoration(80));
        informShowAdapter=new InformShowAdapter(this,inform_show_rela_back,this,inform_show_text_download);
        inform_show_recycler.setAdapter(informShowAdapter);
        inform_show_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_inform_show;
    }

}
