package yanzs.hfutlibrary.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import yanzs.hfutlibrary.listener.OnItemClickListener;

public abstract class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(parent,getResId());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        final int pos=holder.getAdapterPosition();
        initItemView(holder,position);
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(pos);
                }
            });
        }
    }

    protected abstract void initItemView(BaseViewHolder holder, int position);


    protected abstract int getResId();
}
