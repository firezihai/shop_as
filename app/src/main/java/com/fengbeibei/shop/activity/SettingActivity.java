package com.fengbeibei.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.MyApplication;

import butterknife.BindView;

/**
 * SettingActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-23 14:59
 */
public class SettingActivity extends BaseActivity{
    @BindView(R.id.exit_account)
    Button mExitAccount;
    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.setting);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mExitAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_account:
                MyApplication application = MyApplication.getInstance();
                application.setProperty("user.login_state","0");
                application.setProperty("user.key","");
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
}
