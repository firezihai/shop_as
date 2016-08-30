package com.fengbeibei.shop.fragment.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.fengbeibei.shop.common.Address;

/**
 * AreaDialogBuilder
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-30 14:13
 */
public class AreaDialogBuilder {
    private Bundle mBundle = new Bundle();
    private AddressDialogFragment.OnAreaSelectedListener mAreaSelectedListener;

    public AreaDialogBuilder setAreaSelectedListener(AddressDialogFragment.OnAreaSelectedListener areaSelectedListener) {
        mAreaSelectedListener = areaSelectedListener;
        return this;
    }
    public AddressDialogFragment create(){
        AddressDialogFragment dialogFragment = new AddressDialogFragment();
        dialogFragment.setArguments(mBundle);
        dialogFragment.setOnAreaSelectedListener(mAreaSelectedListener);

        return dialogFragment;
    }
    public AreaDialogBuilder setAreaType(int type){
        mBundle.putInt("area_type", type);
        return this;
    }

    public AreaDialogBuilder setAddress(Address address){
        mBundle.putParcelable("address", address);
        return this;
    }

    public void show(FragmentManager fragmentManager){
        if(fragmentManager == null){
            Log.e("SelectAreaDialog","show error : fragment manage is null");
            return;
        }
        AddressDialogFragment dialogFragment = create();
        dialogFragment.show(fragmentManager,dialogFragment.getName());
    }
}
