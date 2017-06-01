package com.zihai.shop.activity;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.bean.Address;
import com.zihai.shop.bean.Cart;
import com.zihai.shop.bean.CartInfo;
import com.zihai.shop.bean.Invoice;
import com.zihai.shop.bean.Voucher;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.IntentHelper;
import com.zihai.shop.fragment.dialog.PaymentTypeDialog;
import com.zihai.shop.widget.CartOrderProductView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-07 20:14
 */
public class BuyActivity extends BaseActivity implements PaymentTypeDialog.OnSelectedListener{
    public static final int REQUEST_UPDATE_DELIVERY_ADDR = 0x111;
    public static final int REQUEST_ADD_DELIVERY_ADDR = 0x112;
    public static final int REQUEST_SELECT_DELIVERY_ADDR = 0x113;
    public static final int REQUEST_INVOICE_SETTING = 0x114;
    private static final String TAG = "BuyActivity";
    @BindView(R.id.product_view)
    CartOrderProductView mOrderProductView;
    @BindView(R.id.rl_address_layout)
    RelativeLayout mAddressWrapper;
    @BindView(R.id.ll_default_address)
    LinearLayout mAddressLayout;
    @BindView(R.id.tv_no_addr_prompt)
    TextView mAddrPrompt;
    @BindView(R.id.tv_user_name)
    TextView mAddrUserName;
    @BindView(R.id.tv_phone)
    TextView mAddrUserPhone;
    @BindView(R.id.tv_address_info)
    TextView mAddressInfo;
    @BindView(R.id.rl_payment_type)
    RelativeLayout mPaymentLayout;
    @BindView(R.id.tv_payment_type)
    TextView mPaymentType;
    @BindView(R.id.rl_invoice_layout)
    RelativeLayout mInvoiceLayout;
    @BindView(R.id.tv_invoice_info)
    TextView mInvoiceInfo;
    @BindView(R.id.tv_goods_amount)
    TextView mGoodsAmountView;
    @BindView(R.id.tv_ship_fee)
    TextView mShipFee;
    @BindView(R.id.tv_discount)
    TextView mDiscount;
    @BindView(R.id.tv_order_amount)
    TextView mOrderAmountView;
    @BindView(R.id.btn_submit_order)
    Button mSubmitOrder;
    private CartInfo mCartInfo;
    private String mAllowOffPay;
    private String mOffPayHash;
    private String mOffPayHashBatch;
    private double mGoodsAmount = 0.00;
    private double mGoodsFreight = 0.00;
    private double mDiscountAmount = 0.00;
    private double mOrderAmount = 0.00;
    private String mIsCart;
    private String mCartId;
    private String mIsFCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy;
    }
    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.confirm_order);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    public void initView() {
        mIsCart = getIntent().getStringExtra("isCart");
        mCartId = getIntent().getStringExtra("cartId");
        mIsFCode = getIntent().getStringExtra("isFCode");
        mAddressWrapper.setOnClickListener(this);
        mInvoiceLayout.setOnClickListener(this);
        mPaymentLayout.setOnClickListener(this);
        initData();
    }
    @Override
    public void initData() {
        showLoadingDialog("",R.layout.view_dialog_loading,R.style.Dialog);
        String url = Constants.APP_URL+"c=buy&a=buy_step1&key="+mKey;
        HashMap<String,String> hashMap = new HashMap<>();
        if(mIsFCode != null && !mIsFCode.equals("")){
            hashMap.put("is_fcode",mIsFCode);
        }
        hashMap.put("ifcart", mIsCart);
        hashMap.put("cart_id", mCartId);
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                hideLoadingDialog();
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    Log.i(TAG, json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        mCartInfo = new CartInfo();
                        mCartInfo.setAddress(Address.objectFromData(obj.optString("address_info")));
                        mCartInfo.setAddressApi(obj.optString("address_api"));
                        mCartInfo.setFreightHash(obj.optString("freight_hash"));
                        mCartInfo.setPredeposit(obj.optString("available_predeposit"));
                        mCartInfo.setRCBalance(obj.optString("available_rc_balance"));
                        mCartInfo.setInvoiceInfo(Invoice.objectFromData(obj.optString("inv_info")));
                        mCartInfo.setOrderAmount(obj.optString("order_amount"));
                        mCartInfo.setVatHash(obj.optString("vat_hash"));
                        JSONObject storeCartInfoObj = obj.optJSONObject("store_cart_list");
                        Iterator<?> iterator = storeCartInfoObj.keys();
                        List<Cart> cartList = new ArrayList<>();
                        while (iterator.hasNext()) {
                            String storeId = iterator.next().toString();
                            String value = storeCartInfoObj.getString(storeId);
                            JSONObject infoObj = new JSONObject(value);
                            List<Cart.Goods> goodsList = Cart.Goods.arrayGoodsFromData(infoObj.optString("goods_list"));
                            String freight = infoObj.optString("freight");
                            Cart cart = new Cart();
                            cart.setStoreId(storeId);
                            cart.setStoreName(infoObj.optString("store_name"));
                            cart.setFreight(infoObj.optString("freight"));
                            cart.setGoods(goodsList);
                            cart.setStoreVoucherList(Voucher.arrayVoucherFromData(infoObj.optString("store_voucher_list")));
                            if (storeCartInfoObj.optString("store_voucher_info").equals("[]")) {
                                cart.setStoreVoucherInfo(infoObj.optString("store_voucher_info"));
                            }
                            cartList.add(cart);
                        }
                        mCartInfo.setCartList(cartList);
                        //运费，是否支持货到付款相关信息
                        addressApi(mCartInfo.getAddressApi());
                        //地址
                        addressInfo(mCartInfo.getAddress());
                        //商品信息
                        if (mCartInfo.getCartList().size() < 2 && mCartInfo.getCartList().get(0).getGoods().size() < 2) {
                            mOrderProductView.initView(mCartInfo.getCartList().get(0).getGoods().get(0));
                        } else {
                            mOrderProductView.initView(mCartInfo.getCartList());
                        }
                        //发票
                        invoiceInfo();
                        mGoodsAmount = Double.parseDouble(mCartInfo.getOrderAmount());
                        //订单费用
                        updateAmount();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_address_layout:
                if(mCartInfo.getAddress() != null){
                    IntentHelper.selectDeliveryAddr(BuyActivity.this,mCartInfo.getAddress());
                }else{

                }
                break;
            case R.id.rl_invoice_layout:
                if(mCartInfo.getInvoiceInfo() != null) {
                    IntentHelper.invoice(this, true, mCartInfo.getInvoiceInfo());
                }else{
                    IntentHelper.invoice(this,false,null);
                }
                break;
            case R.id.rl_payment_type:
                paymentTypeDialog();
                break;
        }
    }


    public void addressInfo(Address address){
        Log.i(TAG, address.toString());
        if(address != null) {
            mAddrUserName.setText(address.getTrueName());
            mAddrUserPhone.setText(address.getMobPhone());
            mAddressInfo.setText(address.getAreaInfo()+address.getAddress());
            mAddressLayout.setVisibility(View.VISIBLE);
            mAddrPrompt.setVisibility(View.GONE);
        }else{
            mAddrPrompt.setVisibility(View.VISIBLE);
            mAddressLayout.setVisibility(View.GONE);
        }
    }

    public void addressApi(String addrApi){
        if(addrApi.equals("")) {
            return ;
        }
        mGoodsFreight = 0.00;
        try {
            JSONObject addressApi = new JSONObject(addrApi);
            mAllowOffPay = addressApi.optString("allow_offpay");
            mOffPayHash = addressApi.optString("offpay_hash");
            mOffPayHashBatch = addressApi.optString("offpay_hash_batch");
            JSONObject freight = addressApi.optJSONObject("content");
            Iterator<?> iterator = freight.keys();
            while (iterator.hasNext()){
                String storeId = iterator.next().toString();
                String value = freight.getString(storeId);
                mGoodsFreight += Double.parseDouble(value);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public void invoiceInfo(){
        String invTitle = mCartInfo.getInvoiceInfo().getInvTitle();
        if(mCartInfo.getInvoiceInfo() != null && invTitle != null){
            System.out.println(mCartInfo.getInvoiceInfo() );
            mInvoiceInfo.setText(mCartInfo.getInvoiceInfo().getInvTitle()+mCartInfo.getInvoiceInfo().getInvContent());
        }else{
            mInvoiceInfo.setText("不开发票");
        }
    }

    public void updateAmount(){
        mGoodsAmountView.setText(String.format(getString(R.string.goods_amount_format),mGoodsAmount+""));
        mShipFee.setText(String.format(getString(R.string.ship_fee_format), mGoodsFreight + ""));
        mDiscount.setText(String.format(getString(R.string.discout_format), mDiscountAmount + ""));
        mOrderAmount = mGoodsAmount+mGoodsFreight-mDiscountAmount;
        mOrderAmountView.setText(mOrderAmount + "");
    }

    private void updateAddress(){
        String url = Constants.APP_URL+"c=buy&a=change_address&key="+mKey;
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("area_id", mCartInfo.getAddress().getAreaId());
        hashMap.put("city_id", mCartInfo.getAddress().getCityId());
        hashMap.put("freight_hash",mCartInfo.getFreightHash());
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    mCartInfo.setAddressApi(json);
                    addressApi(mCartInfo.getAddressApi());
                    addressInfo(mCartInfo.getAddress());
                    updateAmount();

                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BuyActivity.REQUEST_SELECT_DELIVERY_ADDR:
                    mCartInfo.setAddress((Address) data.getParcelableExtra("address"));
                    updateAddress();
                    break;
                case BuyActivity.REQUEST_UPDATE_DELIVERY_ADDR:
                    mCartInfo.setAddress((Address) data.getParcelableExtra("address"));
                    updateAddress();
                    break;
                case BuyActivity.REQUEST_ADD_DELIVERY_ADDR:
                    mCartInfo.setAddress((Address) data.getParcelableExtra(AddDeliveryActivity.EXTRA_ADDRESS));
                    updateAddress();
                    break;
                case BuyActivity.REQUEST_INVOICE_SETTING:
                    //     if (resultCode == InvoiceActivity.RESULT_INVOICE) {
                    if (data != null) {
                        mCartInfo.setInvoiceInfo((Invoice) data.getParcelableExtra(InvoiceActivity.EXTRA_INVOICE));
                    }
                    invoiceInfo();
                    //   }
                    break;
            }
        }
    }

    @Override
    public void onSelectedPayment(int type) {
        if(type == 1){
            mPaymentType.setText("在线支付");
        }else if(type == 2){
            mPaymentType.setText("货到付款");
        }else if(type == 3){
            mPaymentType.setText("门店支付");
        }
    }

    public void paymentTypeDialog(){
        String showOffPay = mCartInfo.getShowOffPay() != null ? mCartInfo.getShowOffPay() : "0";
        PaymentTypeDialog dialog = new PaymentTypeDialog(this,R.style.dialog_float_up,showOffPay,"0");
        dialog.setOnSelectedListener(this);
        dialog.show();
    }
}
