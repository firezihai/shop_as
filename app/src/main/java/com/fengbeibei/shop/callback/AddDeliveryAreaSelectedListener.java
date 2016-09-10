package com.fengbeibei.shop.callback;

import com.fengbeibei.shop.activity.AddDeliveryActivity;
import com.fengbeibei.shop.common.Address;
import com.fengbeibei.shop.fragment.dialog.AddressDialogFragment;

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
