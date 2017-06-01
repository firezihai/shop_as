package com.zihai.shop.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.zihai.shop.R;
import com.zihai.shop.fragment.CartFragment;
import com.zihai.shop.fragment.CategoryFragment;
import com.zihai.shop.fragment.HomeFragment;
import com.zihai.shop.fragment.UcenterFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity{
    @BindView(R.id.homeBtn)
	RadioButton mHomeBtn;
    @BindView(R.id.categoryBtn)
	RadioButton mCategoryBtn;
    @BindView(R.id.cartBtn)
	RadioButton mCartBtn;
    @BindView(R.id.ucenterBtn)
	RadioButton mUcenterBtn;

	private FragmentManager mFragmentManager;
	private int mFragmentContainer;
	private HomeFragment mHomeFragment;
	private CartFragment mCartFragment;
	private CategoryFragment mCategoryFragment;
	private UcenterFragment mUcenterFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentContainer = R.id.fragmentContainer;

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
        mHomeBtn.setOnClickListener(this);
        mCategoryBtn.setOnClickListener(this);
        mCartBtn.setOnClickListener(this);
        mUcenterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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

	
	private  void intoHome(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mHomeFragment == null) {
			mHomeFragment = new HomeFragment();
			transaction.add(mFragmentContainer, mHomeFragment);
		} else {
			transaction.show(mHomeFragment);
		}
		mHomeBtn.setChecked(true);
		transaction.commitAllowingStateLoss();
	}
	public void intoCategory(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mCategoryFragment == null) {
			mCategoryFragment = new CategoryFragment();
			transaction.add(mFragmentContainer, mCategoryFragment);
		} else {
			transaction.show(mCategoryFragment);
		}
		mCategoryBtn.setChecked(true);
		transaction.commitAllowingStateLoss();
	}
	public void intoCart(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mCartFragment == null) {
			mCartFragment = new CartFragment();
			transaction.add(mFragmentContainer, mCartFragment);
		} else {
			transaction.show(mCartFragment);
		}
		mCartBtn.setChecked(true);
		transaction.commitAllowingStateLoss();
	}
	public void intoUcenter(){
		FragmentTransaction transaction =mFragmentManager.beginTransaction();
		hideFragments(transaction);
		if (mUcenterFragment == null) {
			mUcenterFragment = new UcenterFragment();
			transaction.add(mFragmentContainer, mUcenterFragment);
		} else {
			transaction.show(mUcenterFragment);
		}
		mUcenterBtn.setChecked(true);
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
