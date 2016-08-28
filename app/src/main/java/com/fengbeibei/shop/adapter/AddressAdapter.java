package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Address;
import com.fengbeibei.shop.interf.AddressItemEditInterface;
import com.fengbeibei.shop.utils.PhoneUtil;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-28 10:18
 */
public class AddressAdapter extends BaseAdapter{
    private AddressItemEditInterface mEditInterface;
    private List<Address> mAddressList;
    private Context mContext;

    public AddressAdapter(Context context, List<Address> addressList) {
        mContext = context;
        mAddressList = addressList;
    }

    public void setEditListener(AddressItemEditInterface editListener){
        mEditInterface = editListener;
    }

    @Override
    public int getCount() {
        return mAddressList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAddressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_address,null);
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView)convertView.findViewById(R.id.tv_username);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.addressInfo = (TextView) convertView.findViewById(R.id.tv_address_info);
            viewHolder.checkbox = (ImageView) convertView.findViewById(R.id.iv_checkbox_icon);
            viewHolder.setDefault = (LinearLayout) convertView.findViewById(R.id.ll_set_default);
            viewHolder.editAddress = (LinearLayout) convertView.findViewById(R.id.ll_edit_addr);
            viewHolder.deleteAddress = (LinearLayout) convertView.findViewById(R.id.ll_delete_addr);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Address address = mAddressList.get(position);
        viewHolder.username.setText(address.getTrueName());
        viewHolder.phone.setText(PhoneUtil.addStarFormat(address.getMobPhone(),4));
        viewHolder.addressInfo.setText(address.getAreaInfo()+address.getAddress());
        if("1".equals(address.getIsDefault())){
            viewHolder.checkbox.setSelected(true);
        }
        viewHolder.setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEditInterface.setDefault(address.getAddressId());
            }
        });
        viewHolder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditInterface.editAddress(address.getAddressId());
            }
        });
        viewHolder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditInterface.delAddress(address.getAddressId());
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView username;
        TextView phone;
        TextView addressInfo;
        ImageView checkbox;
        LinearLayout setDefault;
        LinearLayout editAddress;
        LinearLayout deleteAddress;
    }

}
