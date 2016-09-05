package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.cart;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.SystemHelper;
import com.fengbeibei.shop.fragment.CartFragment;
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
    private CartFragment mCartFragment;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    private boolean mShopChecked = false;
    private boolean mProductChecked = false;
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
            viewHolder.mCheckBox = (CheckBox)convertView.findViewById(R.id.cb_cart_shop);
            viewHolder.mStoreName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            viewHolder.mStorePrompt = (TextView) convertView.findViewById(R.id.tv_cart_shop_prompt);
            viewHolder.mShipFee = (TextView) convertView.findViewById(R.id.tv_ship_fee);
            viewHolder.mToCoupon = (TextView) convertView.findViewById(R.id.tv_to_coupon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ShopViewHolder) convertView.getTag();
        }
        cart cart = (com.fengbeibei.shop.bean.cart)getGroup(groupPosition);

        if(!mShopChecked) {
            viewHolder.mCheckBox.setChecked(cart.isChecked());
        }else{
            viewHolder.mCheckBox.setChecked(mShopChecked);
        }
        viewHolder.mCheckBox.setOnClickListener(new StoreCheckboxListener(this, groupPosition));
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
            viewHolder.mEditAdd = (ImageView) convertView.findViewById(R.id.iv_edit_add);
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
        cart cart = (com.fengbeibei.shop.bean.cart)getGroup(groupPosition);
        com.fengbeibei.shop.bean.cart.Goods goods = (com.fengbeibei.shop.bean.cart.Goods)getChild(groupPosition, childPosition);
        mImageLoader.displayImage(goods.getGoodsImageUrl(), viewHolder.mProductImage, mOptions, mAnimateFirstListener);


        if(!mProductChecked){
            if(!cart.isChecked()) {
                viewHolder.mCheckBoxProduct.setChecked(goods.isChecked());
            }else{
                viewHolder.mCheckBoxProduct.setChecked(cart.isChecked());
            }
        }else{
            viewHolder.mCheckBoxProduct.setChecked(mProductChecked);
        }
        viewHolder.mCheckBoxProduct.setOnClickListener(new ProductCheckboxListener(this,groupPosition,childPosition));
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

           // viewHolder.mEditNum.setOnClickListener();
            viewHolder.mEditAdd.setOnClickListener(new EditNumListener(this,convertView,groupPosition,childPosition));
            viewHolder.mEditSub.setOnClickListener(new EditNumListener(this,convertView,groupPosition,childPosition));

        }
      //  viewHolder.mAccessoryChild.setText();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setCartFragment(CartFragment cartFragment) {
        mCartFragment = cartFragment;
    }

    public CartFragment getCartFragment() {
        return mCartFragment;
    }



    public void setChecked(boolean checked){
        mShopChecked = checked;
        mProductChecked = checked;
        int groupSize = mCartList.size();
        for(int i=0;i<groupSize;i++){
            mCartList.get(i).setChecked(checked);
            int childSize = mCartList.get(i).getGoods().size();
            for(int k=0;k<childSize;k++){
                mCartList.get(i).getGoods().get(k).setChecked(checked);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnStoreChecked(int groupPosition,boolean checked){
         ;
        int child = mCartList.get(groupPosition).getGoods().size();
        for(int i=0;i<child;i++){
            mCartList.get(groupPosition).getGoods().get(i).setChecked(checked);
        }
      //  if(!checked) {
        mCartList.get(groupPosition).setChecked(checked);

        notifyDataSetChanged();
    }

    public List<cart> getCartList() {
        return mCartList;
    }

    public void setOnProductChecked(int groupPosition,int childPosition,boolean checked){

       // if(!checked){
        mCartList.get(groupPosition).getGoods().get(childPosition).setChecked(checked);
        int child = mCartList.get(groupPosition).getGoods().size();
        boolean allChecked = true;
        for(int i=0;i<child;i++){
            if(!mCartList.get(groupPosition).getGoods().get(i).isChecked()){
                allChecked = false;
            }
        }

        mCartList.get(groupPosition).setChecked(allChecked);
        if(!checked){
            mShopChecked = false;
            mProductChecked = false;
        }
        //}

        notifyDataSetChanged();
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

    class StoreCheckboxListener implements View.OnClickListener{
        private int mGroupPosition;
        private CartAdapter mCartAdapter;

        public StoreCheckboxListener(CartAdapter cartAdapter, int groupPosition) {
            mCartAdapter = cartAdapter;
            mGroupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox)v;
            boolean isChecked = checkBox.isChecked() ? true : false;
            mCartAdapter.setOnStoreChecked(mGroupPosition,isChecked);
            CartFragment.onCheckChangeCallback(mCartAdapter.getCartFragment(),isChecked);
        }
    }

    class ProductCheckboxListener implements View.OnClickListener{
        private int mGroupPosition;
        private int mChildPosition;
        private CartAdapter mCartAdapter;

        public ProductCheckboxListener(CartAdapter cartAdapter, int groupPosition, int childPosition) {
            mCartAdapter = cartAdapter;
            mGroupPosition = groupPosition;
            mChildPosition = childPosition;
        }

        @Override
        public void onClick(View v) {

            CheckBox checkBox = (CheckBox)v;
            boolean isChecked = checkBox.isChecked() ? true : false;
            mCartAdapter.setOnProductChecked(mGroupPosition,mChildPosition,isChecked);
            CartFragment.onCheckChangeCallback(mCartAdapter.getCartFragment(), isChecked);
        }
    }

    class EditNumListener implements View.OnClickListener{
        private CartAdapter mCartAdapter;
        private int mGroupPosition;
        private int mChildPosition;
        private View mView;
        public EditNumListener(CartAdapter cartAdapter,View view,int groupPosition, int childPosition) {
            mCartAdapter = cartAdapter;
            mGroupPosition = groupPosition;
            mChildPosition = childPosition;
            mView = view;
        }

        @Override
        public void onClick(View v) {
            ProductViewHolder viewHolder = (ProductViewHolder)mView.getTag();
            List<cart> cartList = mCartAdapter.getCartList();
            int num = Integer.parseInt(viewHolder.mEditNum.getText().toString());
            switch (v.getId()){
                case R.id.iv_edit_sub :
                    Log.i("CartFragment",num+" sub");
                    num = num-1;
                    if(num<1){
                        num = 1;
                    }
                    viewHolder.mEditNum.setText(num + "");
                    break;
                case R.id.iv_edit_add:
                    Log.i("CartFragment",num+" add");
                    num = num+1;
                    if(num>100){
                        num = 99;
                    }
                    viewHolder.mEditNum.setText(num + "");
                    break;
            }
        }
    }
}
