package com.fengbeibei.shop.interf;

import com.fengbeibei.shop.bean.Address;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-28 11:37
 */
public abstract interface AddressItemEditInterface {
    public abstract void setDefault(String addressId);
    public abstract void editAddress(Address address);
    public abstract void delAddress(String addressId);
}
