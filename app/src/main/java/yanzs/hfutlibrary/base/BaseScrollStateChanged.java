package yanzs.hfutlibrary.base;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import yanzs.hfutlibrary.listener.OnRefreshListener;

public class BaseScrollStateChanged extends RecyclerView.OnScrollListener {
    private int lastVisibleItem;
    private BaseRefreshListAdapter adapter;
    private Handler handler=new Handler(Looper.getMainLooper());
    private GridLayoutManager layoutManager;
    private OnRefreshListener onRefreshListener;


    public BaseScrollStateChanged(BaseRefreshListAdapter adapter, GridLayoutManager layoutManager){
        this.adapter=adapter;
        this.layoutManager=layoutManager;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem=layoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState==RecyclerView.SCROLL_STATE_IDLE){
            if (adapter.isShow()&&lastVisibleItem+1==adapter.getItemCount()||
                    !adapter.isShow()&&lastVisibleItem+2==adapter.getItemCount()){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onRefreshListener!=null){
                            onRefreshListener.refreshView();
                        }
                    }
                },500);
            }

        }
    }
}
