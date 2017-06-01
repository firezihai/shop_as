package com.zihai.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.activity.AccountManageActivity;
import com.zihai.shop.activity.OrderListActivity;
import com.zihai.shop.bean.User;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.HttpClientHelper.CallBack;
import com.zihai.shop.common.IntentHelper;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.fragment.Base.BaseFragment;

import org.apache.http.HttpStatus;

import butterknife.BindView;

public class UcenterFragment extends BaseFragment implements OnClickListener{
	private User mUser;
	private MyApplication mApplication;
    @BindView(R.id.setting)
    ImageButton mSetting;
    @BindView(R.id.username)
	TextView mUsername;
    @BindView(R.id.follow_goods)
	TextView mFollowGoods;
    @BindView(R.id.follow_store)
	TextView mFollowStore;
    @BindView(R.id.wait_pay_count)
	TextView mWaitPayCount;
    @BindView(R.id.wait_ship_count)
	TextView mWaitShipCount;
    @BindView(R.id.wait_receipt_count)
	TextView mWaitReceiptCount;
    @BindView(R.id.wait_comment_count)
	TextView mWaitCommentCount;
    @BindView(R.id.account_balance)
	TextView mAccountBalance;
    @BindView(R.id.point)
	TextView mPoint;
    @BindView(R.id.voucher)
	TextView mVoucher;
    @BindView(R.id.seeAllOrder)
	TextView mSeeAllOrder;
    @BindView(R.id.loginBtn)
    Button mLoginBtn;
    @BindView(R.id.account_manage)
    LinearLayout mAccountManage;
    private String mKey;
	//private static final  String REQUEST_CODE = "0011";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mInflater = inflater;
		// TODO Auto-generated method stub
		View ucenterLayout = inflater.inflate(R.layout.fragment_ucenter, container, false);
		return ucenterLayout;
	}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ucenter;
    }

    @Override
	public void initView(){
        mLoadingLayout = R.layout.view_dialog_loading;
        mLoadingStyle = R.style.Dialog;
        mApplication = MyApplication.getInstance();
        mKey = mApplication.getLoginKey();
        if(isLogin()){
            initData();
        }
        mLoginBtn.setOnClickListener(this);
        mSeeAllOrder.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mAccountManage.setOnClickListener(this);
	}
	@Override
	public void initData(){

        if(!mApplication.isLogin()){
            showLoadingDialog();
        }
        String key = mApplication.getLoginKey();
        String url = Constants.MEMBER_INFO_URL + "&key="+key;
        HttpClientHelper.asynGet(url, new CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
                // TODO Auto-generated method stub
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    mUser = User.newInstance(json);

                    if (mUser != null) {
                        MyApplication.getInstance().setUserInfo(mUser);
                        showLoginBtn(false);
                        setData();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                // TODO Auto-generated method stub

            }

        });

	}

    private void setData(){
        mUsername.setText(mUser.getUserName());
        mWaitPayCount.setText(mUser.getOrderNopayCount());
        mWaitShipCount.setText(mUser.getOrderNoshipCount());
        mWaitReceiptCount.setText(mUser.getOrderNoreceiptCount());
        mWaitCommentCount.setText(mUser.getOrderNocommentCount());
    }
    private void showLoginBtn(boolean show){
        if(show){
            mUsername.setVisibility(View.GONE);
            mLoginBtn.setVisibility(View.VISIBLE);
        }else{
            mUsername.setVisibility(View.VISIBLE);
            mLoginBtn.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        if(!isLogin()){
            IntentHelper.login(getActivity());
            return ;
        }
        switch (v.getId()){
            case R.id.seeAllOrder:
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                IntentHelper.appSetting(getActivity());
                break;
            case R.id.account_manage:
                IntentHelper.accountManage(getActivity(), AccountManageActivity.ACCOUNT_MANAGE);
                break;
        }
    }

    public boolean isLogin(){
        if(mKey != null && !mKey.equals("")) {
            return true;
        }
        return false;
    }

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        mKey = MyApplication.getInstance().getLoginKey();
        String userName = MyApplication.getInstance().getProperty("user.name");
        if(isLogin()){
			initData();
		}else if(userName != null && !userName.equals("")){
            mUser = new User();
            mUser.setUserName("");
            mUser.setOrderNopayCount("");
            mUser.setOrderNoshipCount("");
            mUser.setOrderNoreceiptCount("");
            mUser.setOrderNocommentCount("");
            showLoginBtn(true);
            setData();
        }

	}

}
