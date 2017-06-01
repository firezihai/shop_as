package com.zihai.shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.activity.BuyActivity;
import com.zihai.shop.bean.Cart;
import com.zihai.shop.common.AnimateFirstDisplayListener;
import com.zihai.shop.common.MyApplication;
import com.zihai.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-08 14:55
 */
public class CartOrderProductView extends LinearLayout {
    private BuyActivity mActivity;
    private List<Cart> mCartList;
    private LayoutInflater mInflater;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    public CartOrderProductView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public CartOrderProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
    }

    @SuppressLint("NewApi")
    public CartOrderProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
    }

    public void initView(Cart.Goods goods){
        View view = inflaterView(R.layout.view_cart_order_product,null);
        ((TextView) view.findViewById(R.id.tv_goods_name)).setText(goods.getGoodsName());
        mImageLoader.displayImage(goods.getGoodsImageUrl(), (ImageView)view.findViewById(R.id.iv_goods_image),mOptions,mAnimateFirstListener);
        ((TextView) view.findViewById(R.id.tv_goods_price)).setText(goods.getGoodsPrice());
        ((TextView) view.findViewById(R.id.tv_goods_num)).setText("x"+goods.getGoodsNum());
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        addViewLayout(view);
    }
    public void initView(List<Cart> cartList){
        View view = inflaterView(R.layout.view_cart_order_product_more,null);
        List<Cart.Goods> goodsList = new ArrayList<>();
        int productNum = 0;
        for(int i=0;i<cartList.size();i++){
            goodsList.addAll(cartList.get(i).getGoods());
            int child = cartList.get(i).getGoods().size();
            for(int k=0;k<child;k++) {
                productNum += Integer.parseInt(cartList.get(i).getGoods().get(k).getGoodsNum());
            }
        }
        int goodsSize = goodsList.size();
        ((TextView)view.findViewById(R.id.tv_product_num)).setText(String.format(MyApplication.getContext().getString(R.string.num_item_format),productNum));
        LinearLayout productViewLayout = (LinearLayout) view.findViewById(R.id.ll_goods_image);
        for(int i=0;i<3;i++){
            if(i<goodsSize) {
                View goodsImage = inflaterView(R.layout.view_item_product_goods_image, null);
                mImageLoader.displayImage(goodsList.get(i).getGoodsImageUrl(), (ImageView) goodsImage.findViewById(R.id.tv_goods_image), mOptions, mAnimateFirstListener);
                productViewLayout.addView(goodsImage);
            }
        }
        addViewLayout(view);
    }
    public View inflaterView(int resId,ViewGroup view){
       return mInflater.inflate(resId,view);
    }


    private void addViewLayout(View view){
        addView(view);
    }

    public void init(BuyActivity activity,List<Cart> cartList){
        mActivity = activity;
        mCartList = cartList;
    }
}
