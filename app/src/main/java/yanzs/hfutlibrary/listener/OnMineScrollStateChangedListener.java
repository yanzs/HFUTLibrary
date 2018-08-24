package yanzs.hfutlibrary.listener;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OnMineScrollStateChangedListener extends RecyclerView.OnScrollListener {

    private ImageView img_inform;
    private TextView text_title;
    private RelativeLayout rela_toolbar;

    public OnMineScrollStateChangedListener(ImageView img_inform, TextView text_title, RelativeLayout rela_toolbar) {
        this.img_inform=img_inform;
        this.text_title=text_title;
        this.rela_toolbar=rela_toolbar;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        View headerView = null;
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItem = manager.findFirstVisibleItemPosition();
        if (firstVisibleItem == 0) {
            headerView = recyclerView.getChildAt(firstVisibleItem);
        }
        float alpha = 0;
        if (headerView == null) {
            alpha = 1;
        } else {
            alpha = Math.abs(headerView.getTop() * 1.0f / headerView.getHeight());
        }
        Drawable drawable=rela_toolbar.getBackground();
        if (alpha>0.2&&alpha<0.25){
            float a= (float) ((alpha-0.2)*20);
            drawable.mutate().setAlpha((int) (a * 255));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rela_toolbar.setBackground(drawable);
            }
            img_inform.setVisibility(View.VISIBLE);
            text_title.setVisibility(View.VISIBLE);
        }else if (alpha<0.2){
            drawable.mutate().setAlpha(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rela_toolbar.setBackground(drawable);
            }
            img_inform.setVisibility(View.INVISIBLE);
            text_title.setVisibility(View.INVISIBLE);
        }else if (alpha>0.25){
            drawable.mutate().setAlpha(255);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rela_toolbar.setBackground(drawable);
            }
            img_inform.setVisibility(View.VISIBLE);
            text_title.setVisibility(View.VISIBLE);
        }
    }
}
