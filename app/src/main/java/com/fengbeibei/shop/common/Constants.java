package com.fengbeibei.shop.common;

public final class Constants {
	
	/**
	 * 
	 */
	public final static String APP_CODE = "shop.fengbeibei.com";
	/**
	 * 与服务器端连接协义
	 */
	public final static String PROTOCOL = "http://";
	/** 
	 *服务器域名
	 */
	public final static String HOST = "www.zihaidetiandi.com";
	
	/**
	 * 服务器端口
	 */
	public final static String PORT = ":80";
	
	/**
	 * 服务器端应用名称
	 */
	public final static String APP = "/shop/mobile";
	/**
	 * 分页数
	 */
	public final static String PAGESIZE = "20";
	/**
	 * 服务器端请求入口
	 */
	public final static String APP_URL = PROTOCOL + HOST  + APP + "/index.php?";
	
	/**
	 * 首页请求内容请求接口
	 */
	public final static String HOME_URL = APP_URL + "act=index&op=index";
	
	/**
	 * 首页商品列表请求接口
	 */
	 public final static String HOME_GOODS_URL = APP_URL + "act=index&op=pull_load";
	 /**
	  * 顶级商品分类请求接品
	  */
	 public final static String PARENT_CATEGORY_URL = APP_URL+"act=goods_class";
	 /**
	  * 子商品分类请求接口
	  */
	 public final static String CHILD_CATEGORY_URL = APP_URL+"act=goods_class&op=get_child_list";
    /**
     * 商品搜索请求接口
     */
    public final static String SEARCH_GOODS_LIST_URL = APP_URL+"c=goods&a=goods_list";
	 /**
	  * 商品详情请求接品
	  */
	 public final static String GOODS_DETAIL_URL = APP_URL+"act=goods&op=goods_detail";
	/**
	 * 商品图文请求接口
	 */
	public final static String GOODS_DETIAL_GRAPH_URL = APP_URL+"act=goods&op=goods_body";
	/**
	 * 商品评价
	 */
	public final static String GOODS_EVAL_URL = APP_URL+"act=goods&op=goods_evaluate";
	 /**
	  * 购物车商品接口
	  */
	 public final static String CART_LIST_URL = APP_URL+"act=cart&op=cart_list";
	 
	 /**
	  * 会员登陆接口
	  */
	 public final static String LOGIN_URL = APP_URL + "act=login&op=index";
	 /**
	  * 会员信息接口
	  */
	 public final static String MEMBER_INFO_URL = APP_URL +"act=member_index&op=index";
	 
	 /**
	  * 会员订单
	  */
	 public static final String ORDER_LIST_URL = APP_URL + "act=member_order&op=order_list";

    /**
     * 删除订单
     */
    public static final String ORDER_DELETER_URL = APP_URL + "act=member_order&op=order_delete";
    /**
     * 取消订单
     */
    public static final String ORDER_CANCEL_URL = APP_URL + "act=member_order&op=order_cancel";
}
