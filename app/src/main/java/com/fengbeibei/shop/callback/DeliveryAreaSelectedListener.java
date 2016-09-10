package com.fengbeibei.shop.callback;

import com.fengbeibei.shop.activity.UpdateDeliveryActivity;
import com.fengbeibei.shop.common.Address;
import com.fengbeibei.shop.fragment.dialog.AddressDialogFragment;

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
