package com.zihai.shop.callback;

import com.zihai.shop.activity.UpdateDeliveryActivity;
import com.zihai.shop.common.Address;
import com.zihai.shop.fragment.dialog.AddressDialogFragment;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-10 12:46
 */
public class DeliveryAreaSelectedListener implements AddressDialogFragment.OnAreaSelectedListener{
    private UpdateDeliveryActivity mActivity;

    public DeliveryAreaSelectedListener(UpdateDeliveryActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onAreaSelected(Address address) {
        UpdateDeliveryActivity.onAreaSelected(mActivity,address);
    }
}
