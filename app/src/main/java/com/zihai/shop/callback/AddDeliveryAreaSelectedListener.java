package com.zihai.shop.callback;

import com.zihai.shop.activity.AddDeliveryActivity;
import com.zihai.shop.common.Address;
import com.zihai.shop.fragment.dialog.AddressDialogFragment;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-10 21:08
 */
public class AddDeliveryAreaSelectedListener implements AddressDialogFragment.OnAreaSelectedListener{
    private AddDeliveryActivity mActivity;

    public AddDeliveryAreaSelectedListener(AddDeliveryActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onAreaSelected(Address address) {
        AddDeliveryActivity.onAreaSelected(mActivity,address);
    }
}
