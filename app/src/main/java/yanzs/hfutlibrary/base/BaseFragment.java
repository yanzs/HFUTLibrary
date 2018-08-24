package yanzs.hfutlibrary.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Context context;
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(getResId(),container,false);
        context=view.getContext();
        unbinder= ButterKnife.bind(this,view);
        initFragment();
        return view;
    }

    private Context getTest(){
        return context;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    protected abstract void initFragment();

    protected abstract int getResId();


}
