package com.fengbeibei.shop.interf;

/**
 * SearchTabInterface
 *
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-10 17:47
 */
public abstract interface SearchTabInterface {
    /**
     * 商品综合排序
     */
    public abstract void mixSort();

    /**
     * 商品价格升序
     */

    /**
     * 商品价格降序
      */
    public abstract void priceDown();
    /**
     * 商品评价升序
     */
    public abstract void evalUp();

    /**
     * 商品评价降序
     */
    public abstract void evalDown();

    /**
     * 时间排序(最新)
     */
    public abstract void dateSort();

    /**
     * 是否为自营商品
     */
    public abstract void isSelfShop();

    /**
     * 是否为促销商品
     */
    public abstract void isPromotion();

    /**
     * 销量排序
     */
    public abstract void SalesSort();
}
