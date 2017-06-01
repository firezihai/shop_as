package com.zihai.shop.activity;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.zihai.shop.R;
import com.zihai.shop.common.Constants;
import com.zihai.shop.common.HttpClientHelper;
import com.zihai.shop.common.HttpClientHelper.CallBack;
import com.zihai.shop.common.MyApplication;

import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;

public class LoginActivity extends BaseActivity{
    @BindView(R.id.account_edit)
	AutoCompleteTextView mAccountEdit;
    @BindView(R.id.password_edit)
	EditText mPasswordEdit;
    @BindView(R.id.submit_btn)
	Button mSubmitBtn;
	//private Button mCancelLoginBtn;
	private String mAccountStr = "";
	private String mPasswordStr ="";

    @Override
    protected void onBeforeSetContentLayout() {
        createContentView(true);
        setHeadTitle(R.string.login);
        setHeadBackBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        String key =  MyApplication.getInstance().getLoginKey();
        System.out.println("key="+key);
        if(key != null && !key.equals("")){
            finish();
        }
        mAccountEdit = (AutoCompleteTextView)findViewById(R.id.account_edit);
        mPasswordEdit =(EditText)findViewById(R.id.password_edit);
        mSubmitBtn = (Button)findViewById(R.id.submit_btn);
        mSubmitBtn.setEnabled(false);
    //    mCancelLoginBtn = (Button) findViewById(R.id.cancel_login);
     //   mCancelLoginBtn.setOnClickListener(this);
        mAccountEdit.addTextChangedListener(new AccountTextChangedListener());


        mPasswordEdit.addTextChangedListener(new PasswordTextChangedListener());

        mSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                sendLogin();
                break;
        }
    }
    public void sendLogin(){
        if(!mSubmitBtn.isEnabled()){
            return;
        }
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("username", mAccountStr);
        params.put("password", mPasswordStr);
        params.put("client","android");
        HttpClientHelper.asynPost(Constants.LOGIN_URL,params,new CallBack(){

            @Override
            public void onFinish(Message response) {
                // TODO Auto-generated method stub
                if (response.what == HttpStatus.SC_OK){
                    String json = (String)response.obj;
                    try{
                        JSONObject obj = new JSONObject(json);

                        if (obj.has("error")){
                            String error = obj.getString("error");
                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MyApplication.getInstance().setLoginKey("user.key", obj.getString("key"));
                        Intent mIntent = new Intent("0011");
                        sendBroadcast(mIntent);
                        finish();
                    } catch (JSONException e){
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

    class PasswordTextChangedListener implements TextWatcher{
        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() >= 6 && mAccountStr.length() >= 2){
                mSubmitBtn.setEnabled(true);
            }else{
                mSubmitBtn.setEnabled(false);
            }

            mPasswordStr = mPasswordEdit.getText().toString();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    class AccountTextChangedListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            if(s.length() >=2 && mPasswordStr.length() >= 6){
                mSubmitBtn.setEnabled(true);
            }else{
                mSubmitBtn.setEnabled(false);
            }
            mAccountStr= mAccountEdit.getText().toString();
        }

    }
	
}
