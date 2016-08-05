package com.fengbeibei.shop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.fragment.CartFragment;
import com.fengbeibei.shop.fragment.CategoryFragment;
import com.fengbeibei.shop.fragment.HomeFragment;
import com.fengbeibei.shop.fragment.UcenterFragment;

public class HomeActivity extends FragmentActivity{
	private RadioButton mHomeBtn;
	private RadioButton mCategoryBtn;
	private RadioButton mCartBtn;
	private RadioButton mUcenterBtn;
	private Button mScanBtn;
	private Fragment mFragment;
	private FragmentManager mFragmentManager;
	private int mFragmentId;
	private HomeFragment mHomeFragment;
	private CartFragment mCartFragment;
	private CategoryFragment mCategoryFragment;
	private UcenterFragment mUcenterFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		mFragmentManager = getSupportFragmentManager();
		mFragmentId = R.id.fragmentContainer;
		setRegisterButtonClick();
		int fragmentNum = getIntent().getIntExtra("type",0);
		switch (fragmentNum){
			case 1:
				intoCategory();
				break;
			case 2:
				intoCart();
				break;
			case 3:
				intoUcenter();
				break;
			default:
				intoHome();
				break;
		}

	}

	protected void setRegisterButtonClick() {
		// TODO Auto-generated method stub
		mHomeBtn = (RadioButton) findViewById(R.id.homeBtn);
		mCategoryBtn = (RadioButton) findViewById(R.id.categoryBtn);
		mCartBtn = (RadioButton) findViewById(R.id.cartBtn);
		mUcenterBtn = (RadioButton) findViewById(R.id.ucenterBtn);
		HomeRadioButtonClickListener listener = new HomeRadioButtonClickListener();
		mHomeBtn.setOnClickListener(listener);
		mCategoryBtn.setOnClickListener(listener);
		mCartBtn.setOnClickListener(listener);
		mUcenterBtn.setOnClickListener(listener);
	}
	


	class HomeRadioButtonClickListener implements View.OnClickListener{
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		@Override
		public void onClick(View v) {
			RadioButton btn = (RadioButton) v;
			switch (btn.getId()){
			case R.id.homeBtn :
				 intoHome();
				break;
			case R.id.categoryBtn :
				 intoCategory();
				 break;
			case R.id.cartBtn :
					intoCart();
				break;
			case R.id.ucenterBtn :
				intoUcenter();
				break;
			}
			
		}
		
	}
	
	private  void intoHome(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mHomeFragment == null) {
			mHomeFragment = new HomeFragment();
			transaction.add(mFragmentId, mHomeFragment);
		} else {
			transaction.show(mHomeFragment);
		}
		transaction.commitAllowingStateLoss();
	}
	public void intoCategory(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mCategoryFragment == null) {
			mCategoryFragment = new CategoryFragment();
			transaction.add(mFragmentId, mCategoryFragment);
		} else {
			transaction.show(mCategoryFragment);
		}
		transaction.commitAllowingStateLoss();
	}
	public void intoCart(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mCartFragment == null) {
			mCartFragment = new CartFragment();
			transaction.add(mFragmentId, mCartFragment);
		} else {
			transaction.show(mCartFragment);
		}
		transaction.commitAllowingStateLoss();
	}
	public void intoUcenter(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mUcenterFragment == null) {
			mUcenterFragment = new UcenterFragment();
			transaction.add(mFragmentId, mUcenterFragment);
		} else {
			transaction.show(mUcenterFragment);
		}
		transaction.commitAllowingStateLoss();
	}
	private void hideFragments(FragmentTransaction transcantion){
		
		if (mHomeFragment != null) {
			transcantion.hide(mHomeFragment);
		} 
		if (mCategoryFragment != null) {
			transcantion.hide(mCategoryFragment);
		} 
		if (mCartFragment != null) {
			transcantion.hide(mCartFragment);
		} 
		if (mUcenterFragment != null) {
			transcantion.hide(mUcenterFragment);
		} 
	}



}
