package com.zihai.shop.activity;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.adapter.InvoiceAdapter;
import com.zihai.shop.bean.Invoice;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.interf.OnInvoiceDeleteListener;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-11 14:23
 */
public class InvoiceActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,OnInvoiceDeleteListener,AdapterView.OnItemClickListener {

    public static final String EXTRA_IS_WRITE_INV = "is_write_inv";
    public static final String EXTRA_INVOICE = "invoice";
    @BindView(R.id.tv_no_invoice)
    TextView mNoInvoiceView;
    @BindView(R.id.tv_open_invoice)
    TextView mOpenInvoiceView;
    @BindView(R.id.ll_invoice_content)
    LinearLayout mInvContentLayout;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.cb_show_invoice_setting)
    CheckBox mShowInvoiceSetting;
    @BindView(R.id.ll_invoice_setting)
    LinearLayout mInvoiceSettingLayout;
    @BindView(R.id.tv_me)
    TextView mInvTypeMeView;
    @BindView(R.id.tv_company)
    TextView mInvTypeCompanyView;
    @BindView(R.id.ll_invoice_title)
    LinearLayout mInvoiceTitleLayout;
    @BindView(R.id.et_invoice_title)
    EditText mInvoiceTitleView;
    @BindView(R.id.invoice_spinner)
    Spinner mInvoiceSpinner;
    @BindView(R.id.btn_confirm)
    Button mConfirmBtn;

    private InvoiceAdapter mInvoiceAdapter;
    private List<Invoice> mInvoiceList;
    private Invoice mInvoice;
    private boolean mWriteInv;
    private String mInvoiceContent;
    private String mInvId;
    private String mInvTitle;
    private String[] mInvContentArr ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.invoice_info);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        mInvContentArr = getResources().getStringArray(R.array.invoice_type);
        mWriteInv = getIntent().getBooleanExtra(EXTRA_IS_WRITE_INV, false);
        mInvoice = getIntent().getParcelableExtra(EXTRA_INVOICE);
        mNoInvoiceView.setOnClickListener(this);
        mOpenInvoiceView.setOnClickListener(this);
        mShowInvoiceSetting.setOnClickListener(this);
        mInvTypeCompanyView.setOnClickListener(this);
        mInvTypeMeView.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        mNoInvoiceView.setSelected(true);
        setInvoiceTypeTextColor();

        mListView.setOnItemClickListener(this);
        mInvoiceAdapter = new InvoiceAdapter(this);
        mInvoiceAdapter.setOnInvoiceDeleteListener(this);
        if(mInvoice != null) {
            mInvoiceAdapter.setSelected(mInvoice.getInvId());
        }
        mListView.setAdapter(mInvoiceAdapter);


        mInvoiceSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mInvContentArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInvoiceSpinner.setAdapter(adapter);

        initData();
    }

    @Override
    public void initData() {
        String url = Constants.APP_URL+"c=invoice&a=invoice_list&key="+mKey;
        HttpClientHelper.asynGet(url, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    Log.i("BuyActivity","invoice initData="+json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        mInvoiceList = Invoice.arrayInvoiceFromData(obj.optString("invoice_list"));
                        mInvoiceAdapter.setData(mInvoiceList);
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
            case R.id.tv_no_invoice:
                mNoInvoiceView.setSelected(true);
                mOpenInvoiceView.setSelected(false);
                setInvoiceTypeTextColor();
                toggleInvoiceContent();
                break;
            case R.id.tv_open_invoice:
                mNoInvoiceView.setSelected(false);
                mOpenInvoiceView.setSelected(true);
                setInvoiceTypeTextColor();
                toggleInvoiceContent();
                break;
            case R.id.cb_show_invoice_setting:
                mShowInvoiceSetting.setChecked(true);
                toggleInvoiceSetting();
                break;
            case R.id.tv_me:
                mInvTypeMeView.setSelected(true);
                mInvTypeCompanyView.setSelected(false);
                toggleInvoiceTitle();
                break;
            case R.id.tv_company:
                toggleInvoiceTitle();
                mInvTypeMeView.setSelected(false);
                mInvTypeCompanyView.setSelected(true);
                break;
            case R.id.btn_confirm:
                if(mOpenInvoiceView.isSelected() && mShowInvoiceSetting.isChecked()){
                    addInvoice();
                }else{
                //    Intent intent = new Intent();
                 ///   intent.putExtra(EXTRA_INVOICE, -1);
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    public void toggleInvoiceContent(){
        if(mOpenInvoiceView.isSelected()){
            mInvContentLayout.setVisibility(View.VISIBLE);
        }else{
            mInvContentLayout.setVisibility(View.GONE);
        }
    }

    public void toggleInvoiceSetting(){
        if(mShowInvoiceSetting.isChecked()){
            mInvoiceSettingLayout.setVisibility(View.VISIBLE);
        }else{
            mInvoiceSettingLayout.setVisibility(View.GONE);
        }

    }

    public void toggleInvoiceTitle(){
        if(mInvTypeCompanyView.isSelected()){
            mInvoiceTitleLayout.setVisibility(View.GONE);
        }else{
            mInvoiceTitleLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setInvoiceTypeTextColor(){
        if(mNoInvoiceView.isSelected()) {
            mNoInvoiceView.setTextColor(getResources().getColor(R.color.orange));
        }else{
            mNoInvoiceView.setTextColor(getResources().getColor(R.color.text_color));
        }
        if (mOpenInvoiceView.isSelected()){
            mOpenInvoiceView.setTextColor(getResources().getColor(R.color.orange));
        }else{
            mOpenInvoiceView.setTextColor(getResources().getColor(R.color.text_color));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mInvoiceContent = mInvContentArr[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDeleteInvoice(String invoiceId) {
        String url = Constants.APP_URL+"act=invoice&op=invoice_del";
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("inv_id",invoiceId);
        hashMap.put("key",mKey);
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if(response.what == HttpStatus.SC_OK){
                    String json = (String)response.obj;
                    if(json.equals("1")){
                        MyApplication.showToast("删除成功");
                        initData();
                    }else{
                        try{
                            JSONObject obj = new JSONObject(json);
                            MyApplication.showToast(obj.getString("error"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    MyApplication.showToast("删除失改！");
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_INVOICE, mInvoiceList.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }


    public void addInvoice(){
        String url = Constants.APP_URL+"act=invoice&op=invoice_add";
        HashMap<String,String> hashMap = new HashMap<>();
        String title = mInvoiceTitleView.getText().toString();
        if(title.equals("") && mInvTypeMeView.isSelected()){
            hashMap.put("inv_title_select","person");
            mInvTitle = "个人";
        }
        if( mInvTypeCompanyView.isSelected()){
            if(!title.equals("")) {
                hashMap.put("inv_title", title);
                mInvTitle = title;
            }else{
                MyApplication.showToast("请填写发票抬头！");
                return;
            }
        }
        hashMap.put("inv_content", mInvoiceContent);
        hashMap.put("key",mKey);
        HttpClientHelper.asynPost(url, hashMap, new HttpClientHelper.CallBack() {
            @Override
            public void onFinish(Message response) {
                if (response.what == HttpStatus.SC_OK) {
                    String json = (String) response.obj;
                    Log.i("BuyActivity","invoice add="+json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        if (obj.has("inv_id")) {

                            backBuy(obj.getString("inv_id"));
                        }
                        if (obj.has("error")) {
                            MyApplication.showToast(obj.getString("error"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    MyApplication.showToast("保存失败！");
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void backBuy(String invId){
        Intent intent = new Intent();
        Invoice invoice = new Invoice();
        invoice.setInvId(invId);
        invoice.setContent(mInvTitle + " " + mInvoiceContent);
        invoice.setInvContent(mInvoiceContent);
        invoice.setInvTitle(mInvTitle);
        intent.putExtra(EXTRA_INVOICE, invoice);
        setResult(RESULT_OK,intent);
        finish();
    }

}
