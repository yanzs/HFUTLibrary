package yanzs.hfutlibrary.listener;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class OnDetailScrollStateChangedListener extends RecyclerView.OnScrollListener{

    private ImageView imageView;

    public OnDetailScrollStateChangedListener(ImageView imageView){
        this.imageView=imageView;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        View headerView=null;
        LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItem=manager.findFirstVisibleItemPosition();
        if (firstVisibleItem==0){
            headerView=recyclerView.getChildAt(firstVisibleItem);
        }
        float alpha=0;
        if (headerView==null){
            alpha=1;
        }else {
            alpha=Math.abs(headerView.getTop()*1.0f/headerView.getHeight());
        }
        Drawable drawable=imageView.getDrawable();
        if (drawable!=null){
            drawable.mutate().setAlpha((int) (alpha*255));
            imageView.setImageDrawable(drawable);
        }
    }
}
