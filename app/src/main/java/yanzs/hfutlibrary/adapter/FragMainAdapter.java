package yanzs.hfutlibrary.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import yanzs.hfutlibrary.base.BaseFragAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.fragment.Frag_Index;
import yanzs.hfutlibrary.fragment.Frag_Inform;
import yanzs.hfutlibrary.fragment.Frag_News;
import yanzs.hfutlibrary.R;

public class FragMainAdapter extends BaseFragAdapter {

    public FragMainAdapter(FragmentManager fragManager) {
        super(fragManager);
    }

    @Override
    public BaseFragment getFragment(int pos){
        if (fragmentArray==null){
            fragmentArray=new SparseArray<>();
        }
        BaseFragment fragment=fragmentArray.get(pos);
        switch (pos){
            case 0:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_Index();
                }
                break;
            case 1:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_Inform();
                }
                break;
            case 2:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_News();
                }
                break;
            default:
                return null;
        }
        fragmentArray.put(pos,fragment);
        return fragment;
    }

    @Override
    public void loadFragment(int pos){
        FragmentTransaction transaction=fragManager.beginTransaction();
        currFragment=getFragment(pos);
        if (currFragment!=null){
            if (lastFragment==null){
                transaction.replace(R.id.main_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
            }else {
                if (!currFragment.isAdded()){
                    if (currFragment==lastFragment){
                        transaction.replace(R.id.main_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
                    }else {
                        transaction.hide(lastFragment).add(R.id.main_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
                    }
                }else {
                    transaction.hide(lastFragment).show(currFragment).commitAllowingStateLoss();
                }
            }
            lastFragment=currFragment;
        }
    }

}
