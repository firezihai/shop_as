package com.fengbeibei.shop.fragment;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.CartAdapter;
import com.fengbeibei.shop.bean.cart;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CartFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.btn_edit)
    Button mEditBtn;
    @BindView(R.id.lv_cart)
    ExpandableListView mListView;
    @BindView(R.id.ll_cart_footer)
    LinearLayout mCartFooter;
    @BindView(R.id.rl_cart_settle)
    RelativeLayout mCartSettleLayout;
    LinearLayout mAllCheckLayout;
    @BindView(R.id.cb_cart_settle)
    CheckBox mCheckboxSettle;
    @BindView(R.id.tv_cart_amount)
    TextView mCartAmount;
    @BindView(R.id.tv_ship_fee)
    TextView mShipFee;
    @BindView(R.id.tv_cart_settle)
    TextView mCartSettleBtn;
    @BindView(R.id.rl_cart_delete)
    RelativeLayout mCartDeleteLayout;
    @BindView(R.id.cb_cart_delete)
    CheckBox mCheckboxDelete;
    @BindView(R.id.tv_cart_del_num)
    TextView mDelNum;
    @BindView(R.id.tv_cart_delete)
    TextView mCartDeleteBtn;

    private CartAdapter mCartAdapter;
    private List<cart> mCartList;
    private boolean mEditStatus= false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        mEditBtn.setOnClickListener(this);
        mCartSettleBtn.setOnClickListener(this);
        mCartDeleteBtn.setOnClickListener(this);
        mCheckboxSettle.setOnClickListener(this);
        mCheckboxSettle.setOnClickListener(this);
        mCartFooter.setVisibility(View.GONE);
        mCartList = new ArrayList<cart>();
        mCartAdapter = new CartAdapter(getActivity(),mCartList);
        mCartAdapter.setCartFragment(this);
        mCheckboxSettle.setChecked(true);
        mCheckboxDelete.setChecked(true);
        mCheckboxSettle.setOnClickListener(this);
        mListView.setAdapter(mCartAdapter);
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        initData();
    }
    @Override
    public void initData() {
        String key = MyApplication.getInstance().getLoginKey();
        HttpClientHelper.asynGet(Constants.CART_LIST_URL + "&key=" + key, new HttpClientHelper.CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        if (obj.has("cart_list")) {
                            List<cart> cartList = cart.arrayCartFromData(obj.getString("cart_list"));
                            if (obj.has("sum")) {
                                mCartAmount.setText(String.format(getResources().getString(R.string.cart_amount), obj.getString("sum")));
                            }
                            int goodsNum = 0;

                            if (cartList.size() > 0) {
                                mCartFooter.setVisibility(View.VISIBLE);
                            }
                            mCartList.addAll(cartList);
                            for(int i=0;i<mCartList.size();i++){
                                mListView.expandGroup(i);
                                goodsNum += cartList.get(i).getGoods().size();
                            }
                            mCartSettleBtn.setText(String.format(getResources().getString(R.string.goto_settle), goodsNum));
                            mDelNum.setText(String.format(getString(R.string.choose_goods),goodsNum));
                            mCartAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
            case R.id.btn_edit:

                if(mEditStatus){
                    mEditStatus = false;
                }else{
                    mEditStatus = true;
                }
                if(mEditStatus){
                    Log.i("CartFragment", "onclick edit true");
                    mCartSettleLayout.setVisibility(View.GONE);
                    mCartDeleteLayout.setVisibility(View.VISIBLE);
                }else{
                    Log.i("CartFragment", "onclick edit false");
                    mCartSettleLayout.setVisibility(View.VISIBLE);
                    mCartDeleteLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_cart_settle:

                break;
            case R.id.tv_cart_delete:

                break;
            case R.id.cb_cart_settle:
                mCartAdapter.setChecked(mCheckboxSettle.isChecked());
                onSettleCheckboxListener();
                break;
            case R.id.cb_cart_delete:
                mCartAdapter.setChecked(mCheckboxDelete.isChecked());
                onDeleteCheckboxListener();
                break;
        }
    }

  /*  @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(MyApplication.getInstance().isLogin()){
            mCartList.clear();
			initData();
		}
		
	}*/
    private void onSettleCheckboxListener(){
        List<cart> cartList = mCartAdapter.getCartList();
        int groupChild  = cartList.size();
        int checkNum = 0;
        float goodsAmount = 0;
        float shipFee = 0;
        for(int i=0;i<groupChild;i++){
            int child = cartList.get(i).getGoods().size();
            for (int k=0;k<child;k++){
                cart.Goods goods = cartList.get(i).getGoods().get(k);
                if(goods.isChecked()){
                    checkNum +=1;
                    goodsAmount +=Float.parseFloat(goods.getGoodsPrice());
                    shipFee += Float.parseFloat(goods.getGoodsFreight());
                }
            }
        }
        if(checkNum<1){
            mCartSettleBtn.setEnabled(false);
        }else{
            mCartSettleBtn.setEnabled(true);
        }
        mCartSettleBtn.setText(String.format(getResources().getString(R.string.goto_settle), checkNum));
        mCartAmount.setText(String.format(getString(R.string.cart_amount),goodsAmount));
        mShipFee.setText(String.format(getString(R.string.ship_fee_format),shipFee));
    }

    public void onDeleteCheckboxListener(){
        List<cart> cartList = mCartAdapter.getCartList();
        int groupChild  = cartList.size();
        int checkNum = 0;
        for(int i=0;i<groupChild;i++){
            int child = cartList.get(i).getGoods().size();
            for (int k=0;k<child;k++){
                cart.Goods goods = cartList.get(i).getGoods().get(k);
                if(goods.isChecked()){
                    checkNum +=1;
                }
            }
        }
        if(checkNum<1){
            mCartDeleteBtn.setEnabled(false);
        }else{
            mCartDeleteBtn.setEnabled(true);
        }
       // mCartSettleBtn.setText(String.format(getResources().getString(R.string.goto_settle), checkNum));
        mDelNum.setText(String.format(getString(R.string.choose_goods),checkNum));
       // mCartAmount.setText(String.format(getString(R.string.cart_amount),goodsAmount));
      //  mShipFee.setText(String.format(getString(R.string.ship_fee_format),shipFee));
    }
    public static void onCheckChangeCallback(CartFragment fragment,boolean checked){
        fragment.onCheckChangeCallbackPrivate(checked);
    }

    private  void onCheckChangeCallbackPrivate(boolean checked){
        mCheckboxDelete.setChecked(checked);
        mCheckboxSettle.setChecked(checked);
        onSettleCheckboxListener();
        onDeleteCheckboxListener();
    }
}
