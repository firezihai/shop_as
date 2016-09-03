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
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.common.MyApplication;
import com.fengbeibei.shop.fragment.Base.BaseFragment;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

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
    CheckBox mCheckBoxSettle;
    @BindView(R.id.tv_cart_amount)
    TextView mCartAmount;
    @BindView(R.id.tv_ship_fee)
    TextView mShipFee;
    @BindView(R.id.tv_cart_settle)
    TextView mCartSettleBtn;
    @BindView(R.id.rl_cart_delete)
    RelativeLayout mCartDeleteLayout;
    @BindView(R.id.cb_cart_delete)
    CheckBox mCheckBoxDelete;
    @BindView(R.id.tv_cart_del_num)
    TextView mDelNum;
    @BindView(R.id.tv_cart_delete)
    TextView mCartDeleteBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initData() {
        String key = MyApplication.getInstance().getLoginKey();
        HttpClientHelper.asynGet(Constants.CART_LIST_URL+"&key="+key, new CallBack() {

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                if(response.what == HttpStatus.SC_OK){
                    String json = (String) response.obj;
                    try {
                        JSONObject obj = new JSONObject(json);
                        if(obj.has("cart_list")){
                            List<cart> cartList = cart.arrayCartFromData(obj.getString("cart_list"));
                            if(obj.has("sum")){
                                mCartAmount.setText(String.format(getResources().getString(R.string.cart_amount),obj.getString("sum")));
                            }
                            int goodsNum =0;
                            for (int i=0;i<cartList.size();i++){
                                goodsNum += cartList.get(i).getGoods().size();
                            }
                            mCartSettleBtn.setText(String.format(getResources().getString(R.string.goto_settle), goodsNum));
                            CartAdapter adapter = new CartAdapter(getActivity(),cartList);

                            mListView.setAdapter(adapter);
                            mListView.expandGroup(0);
                            if(cartList.size()>0) {
                                mCartFooter.setVisibility(View.VISIBLE);
                            }
                        }
                    }catch (JSONException e){
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
    public void initView() {
        mEditBtn.setOnClickListener(this);
        mCartSettleBtn.setOnClickListener(this);
        mCartDeleteBtn.setOnClickListener(this);
        mCheckBoxSettle.setOnClickListener(this);
        mCheckBoxSettle.setOnClickListener(this);
        mCartFooter.setVisibility(View.GONE);
        String key = MyApplication.getInstance().getLoginKey();
        Log.i("CartFragment",key);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:

                break;
            case R.id.tv_cart_settle:

                break;
            case R.id.tv_cart_delete:

                break;
            case R.id.cb_cart_settle:

                break;
            case R.id.cb_cart_delete:

                break;
        }
    }

    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(MyApplication.getInstance().isLogin()){

			initData();
		}
		
	}
}
