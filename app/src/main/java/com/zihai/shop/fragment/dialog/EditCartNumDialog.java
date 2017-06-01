package com.zihai.shop.fragment.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zihai.shop.R;
import com.zihai.shop.interf.EditCartNumInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-06 17:22
 */
public class EditCartNumDialog extends DialogFragment implements View.OnClickListener{
    @BindView(R.id.iv_edit_add)
    ImageView mAddBtn;
    @BindView(R.id.iv_edit_sub)
    ImageView mSubBtn;
    @BindView(R.id.et_edit_product_num)
    EditText mEditNum;
    @BindView(R.id.dialog_confirm_btn)
    Button mConfirmBtn;
    @BindView(R.id.dialog_cancel_btn)
    Button mCancelBtn;
    private String mGoodsNum;
    private EditCartNumInterface mEditCartNumInterface;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME, R.style.customDialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_cart_edit_num, container, false);
        ButterKnife.bind(this, layout);
        mCancelBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        mSubBtn.setOnClickListener(this);
        mEditNum.setText(getArguments().getString("num"));
        return layout;

    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams layoutParams= window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int num = !mEditNum.getText().toString().equals("") ? Integer.parseInt(mEditNum.getText().toString()) : 1;
        switch (v.getId()){
            case R.id.dialog_cancel_btn:
                dismiss();
                break;
            case R.id.dialog_confirm_btn:
                dismiss();
                mEditCartNumInterface.onConfirm(num);
                break;
            case R.id.iv_edit_add:
                ++num;
                if(num>100){
                    num = 99;
                }
                mEditNum.setText(num+"");
                break;
            case R.id.iv_edit_sub:
                --num;
                if(num<1){
                    num = 1;
                }
                mEditNum.setText(num+"");
                break;
        }
    }

    public void setEditCartNumListener( EditCartNumInterface editCartNumListener){
        mEditCartNumInterface = editCartNumListener;
    }
}
