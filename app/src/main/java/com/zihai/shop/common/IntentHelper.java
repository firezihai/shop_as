package com.zihai.shop.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.zihai.shop.activity.AccountManageActivity;
import com.zihai.shop.activity.AddDeliveryActivity;
import com.zihai.shop.activity.BuyActivity;
import com.zihai.shop.activity.GoodsDetailActivity;
import com.zihai.shop.activity.HomeActivity;
import com.zihai.shop.activity.InvoiceActivity;
import com.zihai.shop.activity.LoginActivity;
import com.zihai.shop.activity.OrderDetailActivity;
import com.zihai.shop.activity.OrderLogisticsActivity;
import com.zihai.shop.activity.SearchActivity;
import com.zihai.shop.activity.SearchResultActivity;
import com.zihai.shop.activity.SelectDeliveryActivity;
import com.zihai.shop.activity.SettingActivity;
import com.zihai.shop.activity.SubjectWebActivity;
import com.zihai.shop.activity.UpdateDeliveryActivity;
import com.zihai.shop.bean.Invoice;
import com.zihai.shop.fragment.AddressEditFragment;

public class IntentHelper {

	public static void home(Context context,int position){
		Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        intent.putExtra("type",position);
		context.startActivity(intent);
	}
	public static void login(Context context){
		Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
	}
	public static void goodsDetail(Context context,String goods_id){
		Intent intent  = new Intent(context,GoodsDetailActivity.class);
		intent.putExtra("goods_id", goods_id);
		context.startActivity(intent);
	}
	public static void subjectWeb(Context context,String url){
		Intent intent  = new Intent(context,SubjectWebActivity.class);
		intent.putExtra("data", url);
		context.startActivity(intent);
	}

	public static void filter(Context context,String type ,final String data){
		if(type.equals("keyword")){
			
		}else if(type.equals("special")){//专题编号
            IntentHelper.subjectWeb(context, data);
		} else if (type.equals("url")){
			Uri uri = Uri.parse(data);
			if(uri.getQueryParameter("gc_id") != null){
				String gcId = uri.getQueryParameter("gc_id");
				Intent intent = new Intent(context,SearchResultActivity.class);
				intent.putExtra("gcId", gcId);
				context.startActivity(intent);
			}else {
				IntentHelper.subjectWeb(context, data);
			}
		} else if (type.equals("goods")){
			IntentHelper.goodsDetail(context,data);
		}
	}

    public static void search(Context context,String keyword){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("keyword",keyword);
        context.startActivity(intent);
    }
    public static void searchResult(Context context,String keyword){
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("keyword",keyword);
        context.startActivity(intent);
    }

    public static void appSetting(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void orderDetail(Context context,String orderId){
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId",orderId);
        context.startActivity(intent);
    }

    public static void logistics(Context context,String orderId){
        Intent intent = new Intent(context, OrderLogisticsActivity.class);
        intent.putExtra("orderId",orderId);
        context.startActivity(intent);
    }

	public static void accountManage(Context context,int fragmentType){
		Intent intent = new Intent(context, AccountManageActivity.class);
		intent.putExtra(AccountManageActivity.DISPLAY_FRAGMENT_TYPE,fragmentType);
		context.startActivity(intent);
	}

	public static void editAddress(Context context, com.zihai.shop.bean.Address address){
		Intent intent = new Intent(context,AccountManageActivity.class);
		intent.putExtra(AccountManageActivity.DISPLAY_FRAGMENT_TYPE,AccountManageActivity.ADDRESS_EDIT);
		intent.putExtra(AddressEditFragment.EXTRA_ADDRESS,address);
		context.startActivity(intent);
	}

	public static void buy(Context context,String isFCode,String isCart,String cartId){
		Intent intent = new Intent(context, BuyActivity.class);
		intent.putExtra("isFCode",isFCode);
		intent.putExtra("isCart",isCart);
		intent.putExtra("cartId",cartId);
		context.startActivity(intent);
	}

	/**
	 * 打开地址选择activity
	 * @param activity
	 * @param address
	 */
	public static void selectDeliveryAddr(Activity activity, com.zihai.shop.bean.Address address){
		Intent intent = new Intent(activity,SelectDeliveryActivity.class);
		intent.putExtra("address",address);
		activity.startActivityForResult(intent, BuyActivity.REQUEST_SELECT_DELIVERY_ADDR);
	}

	public static void editDeliveryAddr(Activity activity,com.zihai.shop.bean.Address address){
		Intent intent = new Intent(activity, UpdateDeliveryActivity.class);
		intent.putExtra(UpdateDeliveryActivity.EXTRA_ADDRESS,address);
		activity.startActivityForResult(intent,BuyActivity.REQUEST_UPDATE_DELIVERY_ADDR);
	}

	public static void addDeliveryAddr(Activity activity,com.zihai.shop.bean.Address address){
		Intent intent = new Intent(activity, AddDeliveryActivity.class);
		intent.putExtra(AddDeliveryActivity.EXTRA_ADDRESS,address);
		activity.startActivityForResult(intent,BuyActivity.REQUEST_ADD_DELIVERY_ADDR);
	}

	public static void invoice(Activity activity,boolean isOpenInv,Invoice invoice){
		Intent intent = new Intent(activity, InvoiceActivity.class);
		intent.putExtra(InvoiceActivity.EXTRA_IS_WRITE_INV,isOpenInv);
		intent.putExtra(InvoiceActivity.EXTRA_INVOICE,invoice);
		activity.startActivityForResult(intent,BuyActivity.REQUEST_INVOICE_SETTING);
	}
}
