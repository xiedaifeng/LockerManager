package com.yidao.module_lib.base;




import com.yidao.module_lib.base.ibase.IBasePress;

import androidx.fragment.app.FragmentTransaction;

/**
 * Created by xiaochan on 2017/7/26.
 */

public abstract class BaseFragmentView<T extends IBasePress> extends BaseView {


    @Override
    public void init() {
        initFragments();
    }




    public void changeFragment(int position) {
        BaseFragment[] fragments = getFragments();
        changeFragment(fragments[position]);
    }

    public void changeFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    private void initFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment[] fragments = getFragments();
        for (BaseFragment fragment : fragments) {
            transaction.add(getItemView(), fragment);
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        BaseFragment[] fragments = getFragments();
        for (BaseFragment fragment : fragments) {
            transaction.hide(fragment);
        }
    }

    public abstract BaseFragment[] getFragments();

    public abstract int getItemView();
}
