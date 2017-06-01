package com.zihai.shop.callback;

import com.zihai.shop.common.Address;
import com.zihai.shop.fragment.AddressEditFragment;
import com.zihai.shop.fragment.dialog.AddressDialogFragment;

/**
 * AddrEditAreaSelectedListener
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 14:56
 */
public class AddrEditAreaSelectedListener implements AddressDialogFragment.OnAreaSelectedListener{
    private AddressEditFragment mAddressEditFragment;

    public AddrEditAreaSelectedListener(AddressEditFragment addressEditFragment) {
        mAddressEditFragment = addressEditFragment;
    }

    @Override
    public void onAreaSelected(Address address) {
        AddressEditFragment.setProvinceName(mAddressEditFragment, address.getProvinceName());
        AddressEditFragment.setProvinceId(mAddressEditFragment, address.getProvinceId());
        AddressEditFragment.setCityName(mAddressEditFragment, address.getCityName());
        AddressEditFragment.setCityId(mAddressEditFragment, address.getCityId());
        AddressEditFragment.setDistrictName(mAddressEditFragment, address.getDistrictName());
        AddressEditFragment.setDistrictId(mAddressEditFragment, address.getDistrictId());
        AddressEditFragment.getAreaInfo(mAddressEditFragment).setLength(0);
        String str = address.getProvinceName()+" " +address.getCityName()+" " +address.getDistrictName();
        AddressEditFragment.getArea(mAddressEditFragment).setText(str);
    }
}
