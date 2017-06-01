package com.zihai.shop.callback;

import com.zihai.shop.adapter.CartAdapter;
import com.zihai.shop.bean.Cart;
import com.zihai.shop.fragment.CartFragment;
import com.zihai.shop.interf.EditCartNumInterface;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-06 18:32
 */
public class EditCartNumListener implements EditCartNumInterface{
    private CartAdapter.ProductViewHolder mProductViewHolder;
    private Cart.Goods mGoods;
    private CartFragment mCartFragment;
    public EditCartNumListener(CartFragment cartFragment,CartAdapter.ProductViewHolder productViewHolder, Cart.Goods goods) {
        mProductViewHolder = productViewHolder;
        mGoods = goods;
        mCartFragment = cartFragment;
    }

    @Override
    public void onConfirm(int num) {
        mProductViewHolder.mEditNum.setText(num + "");
        mGoods.setGoodsNum(num + "");
        mCartFragment.updateGoodsNum(mGoods.getCartId(),mGoods.getGoodsNum());
    }
}
