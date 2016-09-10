package com.fengbeibei.shop.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.fengbeibei.shop.activity.AccountManageActivity;
import com.fengbeibei.shop.activity.AddDeliveryActivity;
import com.fengbeibei.shop.activity.BuyActivity;
import com.fengbeibei.shop.activity.GoodsDetailActivity;
import com.fengbeibei.shop.activity.HomeActivity;
import com.fengbeibei.shop.activity.LoginActivity;
import com.fengbeibei.shop.activity.OrderDetailActivity;
import com.fengbeibei.shop.activity.OrderLogisticsActivity;
import com.fengbeibei.shop.activity.SearchActivity;
import com.fengbeibei.shop.activity.SearchResultActivity;
import com.fengbeibei.shop.activity.SelectDeliveryActivity;
import com.fengbeibei.shop.activity.SettingActivity;
import com.fengbeibei.shop.activity.SubjectWebActivity;
import com.fengbeibei.shop.activity.UpdateDeliveryActivity;
import com.fengbeibei.shop.fragment.AddressEditFragment;

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

	public static void editAddress(Context context, com.fengbeibei.shop.bean.Address address){
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

	public static void selectDeliveryAddr(Activity activity, com.fengbeibei.shop.bean.Address address){
		Intent intent = new Intent(activity,SelectDeliveryActivity.class);
		intent.putExtra("address",address);
		activity.startActivityForResult(intent, BuyActivity.REQUEST_SELECT_DELIVERY_ADDR);
	}

	public static void editDeliveryAddr(Activity activity,com.fengbeibei.shop.bean.Address address){
		Intent intent = new Intent(activity, UpdateDeliveryActivity.class);
		intent.putExtra(UpdateDeliveryActivity.EXTRA_ADDRESS,address);
		activity.startActivityForResult(intent,BuyActivity.REQUEST_UPDATE_DELIVERY_ADDR);
	}

	public static void addDeliveryAddr(Activity activity,com.fengbeibei.shop.bean.Address address){
		Intent intent = new Intent(activity, AddDeliveryActivity.class);
		intent.putExtra(AddDeliveryActivity.EXTRA_ADDRESS,address);
		activity.startActivityForResult(intent,BuyActivity.REQUEST_ADD_DELIVERY_ADDR);
	}
}
