package yanzs.hfutlibrary.base;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseEventActivity extends BaseActivity {
    @Override
    protected void initActivity() {
        EventBus.getDefault().register(this);
        initEventActivity();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected abstract int getResId();

    protected abstract void initEventActivity();
}
