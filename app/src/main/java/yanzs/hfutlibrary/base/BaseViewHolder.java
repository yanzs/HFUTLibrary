package yanzs.hfutlibrary.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> viewArray;

    public BaseViewHolder(View itemView) {
        super(itemView);
        viewArray=new SparseArray<>();
    }

    public BaseViewHolder(ViewGroup parent, @LayoutRes int resId ) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId,parent,false));
        viewArray=new SparseArray<>();
    }

    /**
     * 获取布局中的相关控件
     * @param viewId 传入的控件的id
     * @param <T> 控件View的类型
     * @return
     */
    public  <T extends View>T getView(@IdRes int viewId){
        View view=viewArray.get(viewId);
        if(view==null){
            view=itemView.findViewById(viewId);
            viewArray.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 获取Context实例
     * @return context
     */
    public Context getContext() {
        return itemView.getContext();
    }
}
