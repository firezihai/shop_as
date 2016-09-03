package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.cart;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-09-03 21:57
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private List<cart> mCartList;
    private Context mContext;
    private LayoutInflater mInflater;
    private CompoundButton.OnCheckedChangeListener mProductCheckboxListener;
    private CompoundButton.OnCheckedChangeListener mStoreCheckboxListener;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    private View.OnClickListener mEditNumListener;
    public CartAdapter(Context context, List<cart> cartList) {
        mContext = context;
        mCartList = cartList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCartList.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return mCartList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCartList.get(groupPosition).getGoods().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCartList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ShopViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_cart_shop,null);
            viewHolder = new ShopViewHolder();
            viewHolder.mCheckBox = (CheckBox)convertView.findViewById(R.id.cb_cart_settle);
            viewHolder.mStoreName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            viewHolder.mStorePrompt = (TextView) convertView.findViewById(R.id.tv_cart_shop_prompt);
            viewHolder.mShipFee = (TextView) convertView.findViewById(R.id.tv_ship_fee);
            viewHolder.mToCoupon = (TextView) convertView.findViewById(R.id.tv_to_coupon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ShopViewHolder) convertView.getTag();
        }
        cart cart = (com.fengbeibei.shop.bean.cart)getGroup(groupPosition);
        if(mStoreCheckboxListener != null) {
            viewHolder.mCheckBox.setOnCheckedChangeListener(mStoreCheckboxListener);
        }
        viewHolder.mStoreName.setText(cart.getStoreName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ProductViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_cart_product,null);
            viewHolder = new ProductViewHolder();
            viewHolder.mPromotionLayout = (RelativeLayout) convertView.findViewById(R.id.rl_cart_promotion);
            viewHolder.mPromotion = (TextView) convertView.findViewById(R.id.tv_cart_promotion);
            viewHolder.mPromotionDetail = (TextView) convertView.findViewById(R.id.tv_cart_promotion_detail);
            viewHolder.mChooseBuy = (TextView) convertView.findViewById(R.id.tv_cart_choose_buy);
            viewHolder.mCheckBoxProduct = (CheckBox)convertView.findViewById(R.id.cb_product);
            viewHolder.mProductImage = (ImageView) convertView.findViewById(R.id.iv_product_image);
            viewHolder.mProductName = (TextView) convertView.findViewById(R.id.tv_product_name);
            viewHolder.mProductNum = (TextView) convertView.findViewById(R.id.tv_product_num);
            viewHolder.mProductDescribe = (TextView)convertView.findViewById(R.id.tv_product_describe);
            viewHolder.mProductSpecial = (TextView) convertView.findViewById(R.id.tv_product_special);
            viewHolder.mProductPrice = (TextView) convertView.findViewById(R.id.tv_product_price);
            viewHolder.mEditNunLayout = (LinearLayout) convertView.findViewById(R.id.ll_edit_product_num);
            viewHolder.mEditNum = (TextView) convertView.findViewById(R.id.tv_edit_product_num);
            viewHolder.mEditAdd = (ImageView) convertView.findViewById(R.id.iv_edit_sub);
            viewHolder.mEditSub = (ImageView) convertView.findViewById(R.id.iv_edit_sub);
            viewHolder.mAllGitLayout = (LinearLayout) convertView.findViewById(R.id.ll_all_git_layout);
            viewHolder.mGitLayout = (LinearLayout) convertView.findViewById(R.id.ll_git_layout);
            viewHolder.mLine = convertView.findViewById(R.id.view_line);
            viewHolder.mAccessoryChildLayout = (LinearLayout) convertView.findViewById(R.id.ll_accessorychild);
            viewHolder.mAccessoryChild = (TextView) convertView.findViewById(R.id.tv_accessorychild);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ProductViewHolder) convertView.getTag();
        }
        cart.Goods goods = (cart.Goods)getChild(groupPosition, childPosition);
        mImageLoader.displayImage(goods.getGoodsImageUrl(), viewHolder.mProductImage, mOptions, mAnimateFirstListener);
        if(mProductCheckboxListener != null) {
            viewHolder.mCheckBoxProduct.setOnCheckedChangeListener(mProductCheckboxListener);
        }
        viewHolder.mProductName.setText(goods.getGoodsName());
        //viewHolder.mProductDescribe.setText(goods.getGoodsDecs);
        //viewHolder.mProductSpecial.setText();
        viewHolder.mProductPrice.setText(goods.getGoodsPrice());
        if(goods.getGoodsStorage().equals(goods.getGoodsStorageAlarm()) || Integer.parseInt(goods.getGoodsStorage()) <= 1){
            viewHolder.mProductNum.setVisibility(View.VISIBLE);
            viewHolder.mEditNunLayout.setVisibility(View.GONE);
        }else{
            viewHolder.mProductNum.setVisibility(View.GONE);
            viewHolder.mEditNunLayout.setVisibility(View.VISIBLE);
            viewHolder.mEditNum.setText(goods.getGoodsNum());
            if(mEditNumListener != null) {
                viewHolder.mEditNum.setOnClickListener(mEditNumListener);
                viewHolder.mEditAdd.setOnClickListener(mEditNumListener);
                viewHolder.mEditSub.setOnClickListener(mEditNumListener);
            }
        }
      //  viewHolder.mAccessoryChild.setText();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setStoreCheckboxListener(CompoundButton.OnCheckedChangeListener storeCheckboxListener) {
        mStoreCheckboxListener = storeCheckboxListener;
    }

    public void setProductCheckboxListener(CompoundButton.OnCheckedChangeListener productCheckboxListener) {
        mProductCheckboxListener = productCheckboxListener;
    }

    public void setEditNumListener(View.OnClickListener editNumListener) {
        mEditNumListener = editNumListener;
    }

    class ShopViewHolder{
        CheckBox mCheckBox;
        TextView mStoreName;
        TextView mStorePrompt;
        TextView mShipFee;
        TextView mToCoupon;

    }

    class ProductViewHolder{
        RelativeLayout mPromotionLayout;
        TextView mPromotion;
        TextView mPromotionDetail;
        TextView mChooseBuy;
        CheckBox mCheckBoxProduct;
        ImageView mProductImage;
        TextView mProductName;
        TextView mProductDescribe;
        TextView mProductSpecial;
        TextView mProductPrice;
        TextView mProductNum;
        LinearLayout mEditNunLayout;
        TextView mEditNum;
        ImageView mEditSub;
        ImageView mEditAdd;
        LinearLayout mAllGitLayout;
        LinearLayout mGitLayout;
        View mLine;
        LinearLayout mAccessoryChildLayout;
        TextView mAccessoryChild;
    }
}
