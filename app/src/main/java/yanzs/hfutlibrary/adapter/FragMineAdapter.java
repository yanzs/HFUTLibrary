package yanzs.hfutlibrary.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.base.BaseFragAdapter;
import yanzs.hfutlibrary.base.BaseFragment;
import yanzs.hfutlibrary.fragment.Frag_Column;
import yanzs.hfutlibrary.fragment.Frag_Line;
import yanzs.hfutlibrary.fragment.Frag_Pie;

public class FragMineAdapter extends BaseFragAdapter{

    public FragMineAdapter(FragmentManager fragManager) {
        super(fragManager);
    }

    @Override
    public BaseFragment getFragment(int pos) {
        if (fragmentArray==null){
            fragmentArray=new SparseArray<>();
        }
        BaseFragment fragment=fragmentArray.get(pos);
        switch (pos){
            case 0:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_Column();
                }
                break;
            case 1:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_Line();
                }
                break;
            case 2:
                if (checkFragmentNull(fragment)) {
                    fragment = new Frag_Pie();
                }
                break;
            default:
                return null;
        }
        fragmentArray.put(pos,fragment);
        return fragment;
    }

    @Override
    public void loadFragment(int pos) {
        FragmentTransaction transaction=fragManager.beginTransaction();
        currFragment=getFragment(pos);
        if (currFragment!=null){
            if (lastFragment==null){
                transaction.replace(R.id.mine_lend_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
            }else {
                if (!currFragment.isAdded()){
                    if (currFragment==lastFragment){
                        transaction.replace(R.id.mine_lend_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
                    }else {
                        transaction.hide(lastFragment).add(R.id.mine_lend_frame_pager,currFragment,String.valueOf(pos)).commitAllowingStateLoss();
                    }
                }else {
                    transaction.hide(lastFragment).show(currFragment).commitAllowingStateLoss();
                }
            }
            lastFragment=currFragment;
        }
    }
}
