package com.fengbeibei.shop.callback;

import com.fengbeibei.shop.common.Address;
import com.fengbeibei.shop.fragment.AddressAddFragment;
import com.fengbeibei.shop.fragment.dialog.AddressDialogFragment;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-02 20:49
 */
public class AddrAddAreaSelectedListener implements AddressDialogFragment.OnAreaSelectedListener{
    private AddressAddFragment mAddressAddFragment;

    public AddrAddAreaSelectedListener(AddressAddFragment addressAddFragment) {
        mAddressAddFragment = addressAddFragment;
    }

    @Override
    public void onAreaSelected(Address address) {
        AddressAddFragment.selectedAreaCallback(mAddressAddFragment,address);
    }
}
