package com.fengbeibei.shop.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-11 22:50
 */
public class PaymentTypeDialog extends Dialog implements View.OnClickListener{
    @BindView(R.id.btn_close)
    Button mCloseBtn;
    @BindView(R.id.iv_chain)
    ImageView mChainSelectView;
    @BindView(R.id.tv_chain)
    TextView mChainTextView;
    @BindView(R.id.ll_chain)
    RelativeLayout mChainLayout;
    @BindView(R.id.iv_online)
    ImageView mOnlineSelectView;
    @BindView(R.id.tv_online)
    TextView mOnlineTextView;
    @BindView(R.id.ll_online)
    RelativeLayout mOnlineLayout;
    @BindView(R.id.iv_offline)
    ImageView mOfflineSelectView;
    @BindView(R.id.tv_offline)
    TextView mOfflineTextView;
    @BindView(R.id.ll_offline)
    RelativeLayout mOfflineLayout;
    private String mIsOffPay;
    private String mIsChain;
    private OnSelectedListener mOnSelectedListener;
    public interface OnSelectedListener{
        void onSelectedPayment(int type);
    }
    public PaymentTypeDialog(Context context, int themeResId,String isOffPay,String isChain) {
        super(context, themeResId);
        mIsOffPay = isOffPay;
        mIsChain = isChain;
    }

    public void onSelectedListener(){

    }
/*  @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME, R.style.dialog_float_up);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_buy_select_payment,null,false);
        ButterKnife.bind(this,view);
        mCloseBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window  = getDialog().getWindow();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        window.getAttributes().width = dm.widthPixels;
        window.getAttributes().height = dm.;
        window.setGravity(Gravity.BOTTOM);
    }


    public String getName(){
        return "paymentTypeDialog";
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_buy_select_payment, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
        mCloseBtn.setOnClickListener(this);
        mChainLayout.setOnClickListener(this);
        mOnlineLayout.setOnClickListener(this);
        mOfflineLayout.setOnClickListener(this);

        if(mIsChain.equals("1")){
            mChainLayout.setEnabled(true);
            mChainSelectView.setEnabled(true);
            mChainTextView.setTextColor(getContext().getResources().getColor(R.color.text_color));
        }else {
            mChainSelectView.setEnabled(false);
            mChainLayout.setEnabled(false);
            mChainTextView.setTextColor(getContext().getResources().getColor(R.color.color_999999));
        }
        if(mIsOffPay.equals("1")){
            mOfflineLayout.setEnabled(true);
            mOfflineTextView.setTextColor(getContext().getResources().getColor(R.color.text_color));
            mOfflineSelectView.setEnabled(true);
        }else{
            mOfflineLayout.setEnabled(false);
            mOfflineSelectView.setEnabled(false);
            mOfflineTextView.setTextColor(getContext().getResources().getColor(R.color.color_999999));
        }

        Window window  = getWindow();
        window.getAttributes().gravity = Gravity.BOTTOM;
        window.getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.ll_online:
                selectPayment(1);
                break;
            case R.id.ll_offline:
                selectPayment(2);
                break;
            case R.id.ll_chain:
                selectPayment(3);
                break;
        }
    }

    public void selectPayment(int type){
        if(type == 1){
            mOnlineSelectView.setSelected(true);
            mOfflineSelectView.setSelected(false);
            mChainSelectView.setSelected(false);
        }else if(type == 2){
            mOnlineSelectView.setSelected(false);
            mOfflineSelectView.setSelected(true);
            mChainTextView.setSelected(false);
        }else if(type == 3){
            mOnlineSelectView.setSelected(false);
            mOfflineSelectView.setSelected(false);
            mChainSelectView.setSelected(true);
        }
        mOnSelectedListener.onSelectedPayment(type);
        dismiss();
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }
}
