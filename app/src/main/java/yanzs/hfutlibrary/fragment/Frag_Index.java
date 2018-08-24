package yanzs.hfutlibrary.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import yanzs.hfutlibrary.adapter.TabIndexAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.DialogUtil;

public class Frag_Index extends BaseFragment implements OnItemClickListener{
    @BindView(R.id.tab_recycle_index)
    RecyclerView tab_recycle_index;
    private TabIndexAdapter tabIndexAdapter;
    @Override
    protected void initFragment() {
        tabIndexAdapter = new TabIndexAdapter();
        tab_recycle_index.setLayoutManager(new GridLayoutManager(getContext(), 1));
        tabIndexAdapter.setOnItemClickListener(this);
        tab_recycle_index.setAdapter(tabIndexAdapter);
    }


    @Override
    protected int getResId() {
        return R.layout.module_fragment_main_tab_index;
    }

    @Override
    public void itemClick(int pos) {
        DialogUtil.showInformDialog(getContext(), Values.INDEX_ITEM[pos][0],Values.INDEX_ITEM[pos][4]);
    }
}
