package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Invoice;
import com.fengbeibei.shop.interf.OnInvoiceDeleteListener;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-11 15:03
 */
public class InvoiceAdapter extends BaseAdapter{
    private Context mContext;
    private List<Invoice> mInvoiceList;
    private OnInvoiceDeleteListener mOnInvoiceDeleteListener;
    private String mSelected;
    public InvoiceAdapter(Context context) {
        mContext = context;
    }
    public void setData(List<Invoice> invoiceList){
        if(mInvoiceList!= null && !invoiceList.isEmpty()) {
            mInvoiceList.addAll(invoiceList);
        }else{
            mInvoiceList = invoiceList;
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mInvoiceList != null ? mInvoiceList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mInvoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_invoice,null);
            viewHolder = new ViewHolder();
            viewHolder.mCheckBox = (TextView)convertView.findViewById(R.id.tv_invoice);
            viewHolder.mInvoiceContent = (TextView) convertView.findViewById(R.id.tv_invoice_content);
            viewHolder.mDelete = (ImageView) convertView.findViewById(R.id.iv_invoice_del);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final Invoice invoice = mInvoiceList.get(position);
        if(getSelected().equals(invoice.getInvId())){
            invoice.setChecked(true);
        }
        viewHolder.mInvoiceContent.setText(invoice.getInvTitle()+" "+invoice.getInvContent());
        if(mOnInvoiceDeleteListener != null) {
            viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInvoiceDeleteListener.onDeleteInvoice(invoice.getInvId());
                }
            });
        }
        if(invoice.isChecked()){
            viewHolder.mCheckBox.setSelected(true);
        }else{
            viewHolder.mCheckBox.setSelected(false);
        }

      //  viewHolder.mCheckBox.setOnClickListener();
        return convertView;
    }

    public void setOnInvoiceDeleteListener(OnInvoiceDeleteListener onInvoiceDeleteListener) {
        mOnInvoiceDeleteListener = onInvoiceDeleteListener;
    }

    public void setSelected(String selected) {
        mSelected = selected;
    }

    public String getSelected() {
        return mSelected;
    }

    public class ViewHolder{
        TextView mCheckBox;
        TextView mInvoiceContent;
        ImageView mDelete;
    }


}
