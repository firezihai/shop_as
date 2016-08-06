package com.fengbeibei.shop.fragment;

import org.apache.http.HttpStatus;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.activity.OrderListActivity;
import com.fengbeibei.shop.bean.User;
import com.fengbeibei.shop.common.CircleImageDrawable;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.common.IntentHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

	private static final  String REQUEST_CODE = "0011";
    private Boolean isLogin = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mInflater = inflater;
		// TODO Auto-generated method stub
		View ucenterLayout = inflater.inflate(R.layout.ucenter, container, false);
		return ucenterLayout;
	}
	@Override
	public void initView(){
        mApplication = MyApplication.getInstance();
        String key = mApplication.getLoginKey();
        if(key != null && !"".equals(key)){
            initData();
        }
        isLogin = mApplication.isLogin();
        mLoginBtn.setOnClickListener(this);
	}
	@Override
	public void initData(){
        String key = mApplication.getLoginKey();
        String url = Constants.MEMBER_INFO_URL + "&key="+key;
        HttpClientHelper.asynGet(url, new CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    mUser = User.newInstance(json);

                    if (mUser != null) {
                        System.out.println("load" + json);
                        MyApplication.getInstance().setUserInfo(mUser);
                        mUsername.setText(mUser.getUserName());
                        mWaitPayCount.setText(mUser.getOrderNopayCount());
                        mWaitShipCount.setText(mUser.getOrderNoshipCount());
                        mWaitReceiptCount.setText(mUser.getOrderNoreceiptCount());
                        mWaitCommentCount.setText(mUser.getOrderNocommentCount());
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                // TODO Auto-generated method stub

            }

        });

	}


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    IntentHelper.login(getActivity());
                    break;
                case R.id.seeAllOrder:
                    mSeeAllOrder.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(getActivity(), OrderListActivity.class);
                            startActivity(intent);
                        }

                    });
                    break;

            }
    }

    @Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		registerBroadcastReceiver();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("ucenterFragment", "onResume");
		String key = mApplication.getLoginKey();
		if(key != null && !"".equals(key)){
			initData();
		}
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d("ucenterFragment", "onPause");
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.d("receiver", action);
			if(action == REQUEST_CODE){
				initData();
			}
		}
		
	};
	public void registerBroadcastReceiver(){
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(REQUEST_CODE);
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
}
