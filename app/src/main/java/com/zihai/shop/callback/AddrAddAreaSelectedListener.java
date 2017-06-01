package com.zihai.shop.callback;

import com.zihai.shop.common.Address;
import com.zihai.shop.fragment.AddressAddFragment;
import com.zihai.shop.fragment.dialog.AddressDialogFragment;

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
