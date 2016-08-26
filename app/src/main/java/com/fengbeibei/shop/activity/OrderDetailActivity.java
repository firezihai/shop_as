package com.fengbeibei.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.IntentHelper;

import butterknife.BindView;

/**
 * OrderDetailActivity
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-25 11:38
 */
public class OrderDetailActivity extends BaseActivity{
    @BindView(R.id.tv_order_sn)
    TextView mOrderSn;
    @BindView(R.id.tv_order_state)
    TextView mOrderState;
    @BindView(R.id.rl_see_deliver)
    RelativeLayout mSeeDeliver;
    @BindView(R.id.rl_shop_layout)
    RelativeLayout mShopLayout;
    @BindView(R.id.tv_store_name)
    TextView mStoreName;
    @BindView(R.id.ll_order_goods_list)
    LinearLayout mGoodsListLayout;
    @BindView(R.id.btn_online_service)
    Button mOnlineServiceBtn;
    @BindView(R.id.tv_payment_type)
    TextView mPaymentType;
    @BindView(R.id.tv_deliver_type)
    TextView mDeliverType;
    @BindView(R.id.tv_invoice_type)
    TextView mInvoiceType;
    @BindView(R.id.tv_invoice_title)
    TextView mInvoiceTitle;
    @BindView(R.id.tv_invoice_content)
    TextView mInvoiceContent;
    @BindView(R.id.tv_goods_amount)
    TextView mGoodsAmount;
    @BindView(R.id.tv_ship_amount)
    TextView mShipAmount;
    @BindView(R.id.tv_add_order_date)
    TextView mOrderDate;
    @BindView(R.id.btn_delete_order)
    Button mDeleteOrderBtn;
    @BindView(R.id.btn_evaluate)
    Button mEvaluateBtn;
    @BindView(R.id.btn_again_buy)
    Button mAgainBuyBtn;
    @BindView(R.id.btn_order_pay)
    Button mOrderPayBtn;
    private String mOrderId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        mOrderId = getIntent().getStringExtra("orderId");
        mSeeDeliver.setOnClickListener(this);
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.order_detail);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.rl_see_deliver:
               IntentHelper.logistics(OrderDetailActivity.this,mOrderId);
               break;
           case R.id.rl_shop_layout:

               break;
           case R.id.btn_online_service:

               break;
           case R.id.btn_order_pay:

               break;
           case R.id.btn_again_buy:

               break;
           case R.id.btn_delete_order:

               break;
           case R.id.btn_evaluate:

               break;

       }
    }
}
