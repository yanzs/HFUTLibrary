package yanzs.hfutlibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.fragment.Frag_Index;
import yanzs.hfutlibrary.fragment.Frag_Inform;
import yanzs.hfutlibrary.fragment.Frag_News;

public abstract class BaseFragAdapter {
    protected static SparseArray<BaseFragment> fragmentArray;
    protected BaseFragment currFragment=null;
    protected FragmentManager fragManager;
    protected Fragment lastFragment=null;

    public BaseFragAdapter(FragmentManager fragManager){
        this.fragManager=fragManager;
        clearFragment();
    }

    public void clearFragment() {
        if (fragmentArray!=null){
            fragmentArray.clear();
        }
    }

    protected boolean checkFragmentNull(Fragment fragment){
        return fragment==null||fragment.isRemoving();
    }


    public abstract BaseFragment getFragment(int pos);

    public abstract void loadFragment(int pos);


    public BaseFragment getCurrFragment() {
        return currFragment;
    }
}
