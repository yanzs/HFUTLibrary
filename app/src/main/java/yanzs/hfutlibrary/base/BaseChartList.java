package yanzs.hfutlibrary.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.activity.inform.Inform_Show;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.util.ColorUtil;

public abstract class BaseChartList<M> extends RecyclerView.Adapter<BaseViewHolder>{
    private List<M> data;
    private Context context;

    public List<M> getData() {
        return data;
    }

    public Context getContext() {
        return context;
    }

    public BaseChartList(List<M> data, Context context){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Values.VIEW_TYPE_NORMAL) {
            return new BaseViewHolder(parent, getNormalResId());
        } else {
            return new BaseViewHolder(parent, getHeadResId());
        }
    }

    protected abstract int getHeadResId();

    protected abstract int getNormalResId();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        if (getItemViewType(pos) == Values.VIEW_TYPE_NORMAL) {
            initItemView(holder, position);

        } else if (getItemViewType(pos) == Values.VIEW_TYPE_HEADER) {
            initHeadView(holder);
        }
    }

    protected abstract void initHeadView(BaseViewHolder holder);

    protected abstract void initItemView(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return data.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Values.VIEW_TYPE_HEADER;
        } else {
            return Values.VIEW_TYPE_NORMAL;
        }

    }
}
