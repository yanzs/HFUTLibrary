package yanzs.hfutlibrary.base;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnItemClickListener;
import yanzs.hfutlibrary.R;

public abstract class BaseRefreshListAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<M> dataList;
    private Handler handler = new Handler(Looper.getMainLooper());
    private OnItemClickListener onItemClickListener;
    private boolean isMore = true;  //用于表示是否加载完全数据
    private boolean isShow = true; //用于表示是否展开footView

    public BaseRefreshListAdapter(List<M> dataList, boolean isMore) {
        this.dataList = dataList;
        this.isMore = isMore;
    }

    public List<M> getDataList() {
        return dataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Values.VIEW_TYPE_NORMAL) {
            return new BaseViewHolder(parent, getResId());
        } else {
            return new BaseViewHolder(parent, R.layout.module_viewholder_foot);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        if (getItemViewType(pos) == Values.VIEW_TYPE_NORMAL) {
            initItemView(holder, position);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.itemClick(pos);
                    }
                });
            }
        } else {
            if (isMore) {
                isShow = true;
                holder.getView(R.id.module_viewholder_foot_pro).setVisibility(View.VISIBLE);
                holder.getView(R.id.module_viewholder_foot_text).setVisibility(View.INVISIBLE);
            } else {
                holder.getView(R.id.module_viewholder_foot_pro).setVisibility(View.INVISIBLE);
                holder.getView(R.id.module_viewholder_foot_text).setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.getView(R.id.module_viewholder_foot_pro).setVisibility(View.GONE);
                        holder.getView(R.id.module_viewholder_foot_text).setVisibility(View.GONE);
                        isShow = false;
                        isMore = true;
                    }
                }, 1500);
            }

        }
    }


    public boolean isShow() {
        return isShow;
    }

    public void updataList(List<M> newData, boolean isMore) {
        if (newData != null) {
            dataList.addAll(newData);
        }
        this.isMore = isMore;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public int getLastPosition() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return Values.VIEW_TYPE_FOOTER;
        } else {
            return Values.VIEW_TYPE_NORMAL;
        }

    }

    protected abstract void initItemView(BaseViewHolder holder, int position);


    protected abstract int getResId();
}
