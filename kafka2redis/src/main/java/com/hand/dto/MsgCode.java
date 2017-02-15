package com.hand.dto;

/**
 * Created by DongFan on 2016/12/28.
 */
public interface MsgCode {
    /**
     * UR
     */
    public static final String UR_PASSWORD_02 = "UR_PASSWORD_02";
    public static final String UR_PASSWORD_02_VALUE = "两次密码输入不一致";

    public static final String UR_PASSWORD_03 = "UR_PASSWORD_03";
    public static final String UR_PASSWORD_03_VALUE = "密码错误";

    public static final String UR_PASSWORD_04 = "UR_PASSWORD_04";
    public static final String UR_PASSWORD_04_VALUE = "密码输入错误次数大于3次";

    public static final String UR_PASSWORD_05 = "UR_PASSWORD_05";
    public static final String UR_PASSWORD_05_VALUE = "密码修改成功";

    public static final String UR_PASSWORD_06 = "UR_PASSWORD_06";
    public static final String UR_PASSWORD_06_VALUE = "用户名有误";

    public static final String UR_PASSWORD_07 = "UR_PASSWORD_07";
    public static final String UR_PASSWORD_07_VALUE = "密码不能为空";

    public static final String UR_PASSWORD_08 = "UR_PASSWORD_08";
    public static final String UR_PASSWORD_08_VALUE = "确认密码不能为空";

    public static final String UR_INFO_001 = "UR_INFO_001";
    public static final String UR_INFO_001_VALUE = "id对应的用户信息为空";

    public static final String UR_INFO_002 = "UR_INFO_002";
    public static final String UR_INFO_002_VALUE = "用户信息必填字段为空";

    public static final String UR_INFO_003 = "UR_INFO_003";
    public static final String UR_INFO_003_VALUE = "输入的性别不合法";

    public static final String UR_INFO_004 = "UR_INFO_004";
    public static final String UR_INFO_004_VALUE = "用户性别和生日只能修改一次";

    public static final String UR_SCY_001 = "UR_SCY_001";
    public static final String UR_SCY_001_VALUE = "id对应的用户为空";

    public static final String UR_SCY_002 = "UR_SCY_002";
    public static final String UR_SCY_002_VALUE = "两次输入的密码不一致";

    public static final String UR_SCY_003 = "UR_SCY_003";
    public static final String UR_SCY_003_VALUE = "输入的密码为空";

    public static final String UR_ADD_001 = "UR_ADD_001";
    public static final String UR_ADD_001_VALUE = "id对应的地址为空";

    public static final String UR_ADD_002 = "UR_ADD_002";
    public static final String UR_ADD_002_VALUE = "地址信息必填字段为空";

    public static final String UR_MOBILE_01 = "UR_MOBILE_01";
    public static final String UR_MOBILE_01_VALUE = "手机号合法且未被使用";

    public static final String UR_MOBILE_02 = "UR_MOBILE_02";
    public static final String UR_MOBILE_02_VALUE = "手机不合法";

    public static final String UR_MOBILE_03 = "UR_MOBILE_03";
    public static final String UR_MOBILE_03_VALUE = "手机已存在";

    public static final String UR_REGISTER_01 = "UR_REGISTER_01";
    public static final String UR_REGISTER_01_VALUE = "注册成功";

    public static final String UR_REGISTER_02 = "UR_REGISTER_02";
    public static final String UR_REGISTER_02_VALUE = "两次密码输入不一致";

    public static final String UR_REGISTER_03 = "UR_REGISTER_03";
    public static final String UR_REGISTER_03_VALUE = "密码错误";

    public static final String UR_REGISTER_04 = "UR_REGISTER_04";
    public static final String UR_REGISTER_04_VALUE = "密码输入错误次数大于3次";

    public static final String UR_REGISTER_05 = "UR_REGISTER_05";
    public static final String UR_REGISTER_05_VALUE = "密码修改成功";

    public static final String UR_REGISTER_06 = "UR_REGISTER_06";
    public static final String UR_REGISTER_06_VALUE = "修改密码时所传用户Id有误";

    public static final String UR_REGISTER_07 = "UR_REGISTER_07";
    public static final String UR_REGISTER_07_VALUE = "密码不能为空";

    public static final String UR_REGISTER_08 = "UR_REGISTER_08";
    public static final String UR_REGISTER_08_VALUE = "确认密码不能为空";

    public static final String UR_EMAIL_01 = "UR_EMAIL_01";
    public static final String UR_EMAIL_01_VALUE = "邮箱未绑定用户";

    public static final String UR_EMAIL_02 = "UR_EMAIL_02";
    public static final String UR_EMAIL_02_VALUE = "链接只能成功使用一次";

    public static final String UR_EMAIL_03 = "UR_EMAIL_03";
    public static final String UR_EMAIL_03_VALUE = "链接已失效";

    public static final String UR_EMAIL_04 = "UR_EMAIL_04";
    public static final String UR_EMAIL_04_VALUE = "链接有效且未被使用";

    public static final String UR_EMAIL_05 = "UR_EMAIL_05";
    public static final String UR_EMAIL_05_VALUE = "无效链接";

    public static final String UR_EMAIL_06 = "UR_EMAIL_06";
    public static final String UR_EMAIL_06_VALUE = "邮箱格式不合法";

    public static final String UR_EMAIL_07 = "UR_EMAIL_07";
    public static final String UR_EMAIL_07_VALUE = "邮箱已被注册";

    public static final String UR_VERIF_01 = "UR_VERIF_01";
    public static final String UR_VERIF_01_VALUE = "滑块验证不通过";

    public static final String UR_LOGIN_01 = "UR_LOGIN_01";
    public static final String UR_LOGIN_01_VALUE = "用户名不存在";

    public static final String UR_LOGIN_02 = "UR_LOGIN_02";
    public static final String UR_LOGIN_02_VALUE = "用户名存在";

    public static final String UR_STAFF_01 = "UR_STAFF_01";
    public static final String UR_STAFF_01_VALUE = "员工号不存在";

    public static final String UR_STAFF_02 = "UR_STAFF_02";
    public static final String UR_STAFF_02_VALUE = "员工号已被注册";

    public static final String UR_STAFF_04 = "UR_STAFF_04";
    public static final String UR_STAFF_04_VALUE = "身份信息格式不正确或与员工ID不匹配";
    /**
     * MSG
     */
    public static final String MSG_01 = "MSG_01";
    public static final String MSG_01_VALUE = "验证码输入错误次数大于3次";

    public static final String MSG_03 = "MSG_03";
    public static final String MSG_03_VALUE = "验证码错误";

    public static final String MSG_04 = "MSG_04";
    public static final String MSG_04_VALUE = "验证码正确";

    /**
     * COMMENT
     */
    public static final String COMMENT_EVALUATION_01 = "COMMENT_EVALUATION_01";
    public static final String COMMENT_EVALUATION_01_VALUE = "尚无评价";

    public static final String COMMENT_PURCHASEADVICE_01 = "COMMENT_PURCHASEADVICE_01";
    public static final String COMMENT_PURCHASEADVICE_01_VALUE = "尚无购买咨询";

    public static final String COMMENT_01 = "COMMENT_01";
    public static final String COMMENT_01_VALUE = "评价中存在敏感词";

    public static final String COMMENT_02 = "COMMENT_02";
    public static final String COMMENT_02_VALUE = "身高必须是两位或三位正整数";

    public static final String COMMENT_03 = "COMMENT_03";
    public static final String COMMENT_03_VALUE = "体重必须是两位或三位正整数";
    
    /**
	 * STOCK
	 */
	public static final String PLEASE_SELECT_EXPRESS_OR_PICKUP = "PLEASE_SELECT_EXPRESS_OR_PICKUP";
	public static final String PLEASE_SELECT_EXPRESS_OR_PICKUP_VALUE = "请选择快递或门店自提";
	
	public static final String PLEASE_SELECT_PRODUCT = "PLEASE_SELECT_PRODUCT";
	public static final String PLEASE_SELECT_PRODUCT_VALUE = "请选择商品";
	
	public static final String EXPRESSREPOSITORY_NOT_EXIST = "EXPRESSREPOSITORY_NOT_EXIST";
	public static final String EXPRESSREPOSITORY_NOT_EXIST_VALUE = "快递仓不存在";
	
	public static final String NOT_SUPPORT_PICKUP = "NOT_SUPPORT_PICKUP";
	public static final String NOT_SUPPORT_PICKUP_VALUE = "不支持自提";
	
	public static final String NO_SKU = "NO_SKU";
	public static final String NO_SKU_VALUE = "没有该SKU";
	
	public static final String NO_SPU = "NO_SPU";
	public static final String NO_SPU_VALUE = "没有该SPU";
	
	public static final String QUERY_UNKNOWN_ERROR = "QUERY_UNKNOWN_ERROR";
	public static final String QUERY_UNKNOWN_ERROR_VALUE = "未知错误";

    /**
     * OD
     */
    public static final String OD_INFO_001 = "OD_INFO_001";
    public static final String OD_INFO_001_VALUE = "必输字段为空";

    public static final String OD_INFO_002 = "OD_INFO_002";
    public static final String OD_INFO_002_VALUE = "orderId对应的订单商品详情信息为空";

    public static final String OD_INFO_003 = "OD_INFO_003";
    public static final String OD_INFO_003_VALUE = "orderId对应的我的订单信息为空";
    
    public static final String OD_INFO_004 = "OD_INFO_004";
    public static final String OD_INFO_004_VALUE = "修改订单商品状态出错";
    
    public static final String OD_INFO_005 = "OD_INFO_005";
    public static final String OD_INFO_005_VALUE = "获取商品库存出错";
    
    public static final String OD_INFO_006 = "OD_INFO_006";
    public static final String OD_INFO_006_VALUE = "获取订单收货地址出错";
    
    public static final String OD_INFO_007 = "OD_INFO_007";
    public static final String OD_INFO_007_VALUE = "退款表存在改退款信息，不能重复退款";
    
    public static final String OD_INFO_008 = "OD_INFO_008";
    public static final String OD_INFO_008_VALUE = "申请退款数据插入退款表出错";
    
    public static final String OD_INFO_009 = "OD_INFO_009";
    public static final String OD_INFO_009_VALUE = "申请售后数据插入退款表出错";
    
    public static final String OD_INFO_010 = "OD_INFO_010";
    public static final String OD_INFO_010_VALUE = "申请售后数据插入换货表出错";
    
    public static final String OD_INFO_011 = "OD_INFO_011";
    public static final String OD_INFO_011_VALUE = "修改退货列表或者换货列表状态出错";
    
    public static final String OD_INFO_012 = "OD_INFO_012";
    public static final String OD_INFO_012_VALUE = "获取订单支付时间出错";
    
    public static final String OD_INFO_013 = "OD_INFO_013";
    public static final String OD_INFO_013_VALUE = "插入换发订单出错";

    public static final String OD_REFUND_001 = "OD_REFUND_001";
    public static final String OD_REFUND_001_VALUE = "退款金额小于0";

    public static final String OD_REFUND_002 = "OD_REFUND_002";
    public static final String OD_REFUND_002_VALUE = "退货换货的数量超过该商品的总数量";

    public static final String OD_REFUND_003 = "OD_REFUND_003";
    public static final String OD_REFUND_003_VALUE = "退款金额大于该商品的总金额";

    public static final String OD_REFUND_004 = "OD_REFUND_004";
    public static final String OD_REFUND_004_VALUE = "没有查到匹配的值";

    public static final String OD_REFUND_005 = "OD_REFUND_005";
    public static final String OD_REFUND_005_VALUE = "refundId对应的退款表信息为空";

    public static final String OD_REFUND_006 = "OD_REFUND_006";
    public static final String OD_REFUND_006_VALUE = "replaceId对应的换货表信息为空";
    
    public static final String OD_REFUND_007 = "OD_REFUND_007";
    public static final String OD_REFUND_007_VALUE = "订单商品行信息为空";
    
    public static final String OD_REFUND_008 = "OD_REFUND_008";
    public static final String OD_REFUND_008_VALUE = "退货件数不符合要求";
    
    public static final String OD_REFUND_009 = "OD_REFUND_009";
    public static final String OD_REFUND_009_VALUE = "退单列表取消操作出错";
    
    public static final String OD_REPLACE_01 = "OD_REPLACE_01";
    public static final String OD_REPLACE_01_VALUE = "查询换货单列表为空";
    
    public static final String OD_REPLACE_02 = "OD_REPLACE_02";
    public static final String OD_REPLACE_02_VALUE = "换货单列表取消操作出错";

    public static final String OD_QUERY_01 = "OD_QUERY_01";
    public static final String OD_QUERY_01_VALUE = "查询订单详情内容为空";

    public static final String OD_QUERY_02 = "OD_QUERY_02";
    public static final String OD_QUERY_02_VALUE = "查询用户订单为空";

    public static final String OD_QUERY_03 = "OD_QUERY_03";
    public static final String OD_QUERY_03_VALUE = "根据订单id或自提id查找不到对应数据 ";

    public static final String OD_QUERY_04 = "OD_QUERY_04";
    public static final String OD_QUERY_04_VALUE = "根据用户id查找不到对应数据  ";

    public static final String OD_QUERY_05 = "OD_QUERY_05";
    public static final String OD_QUERY_05_VALUE = "根据返回值为查找到对应的售后id  ";

    public static final String OD_QUERY_06 = "OD_QUERY_06";
    public static final String OD_QUERY_06_VALUE = "没有找到对应的自提信息";

    public static final String OD_QUERY_07 = "OD_QUERY_07";
    public static final String OD_QUERY_07_VALUE = "传入的订单ids为空";

    public static final String OD_CREATE_01 = "OD_CREATE_01";
    public static final String OD_CREATE_01_VALUE = "创建临时订单商品库存不足";

    public static final String OD_CREATE_02 = "OD_CREATE_02";
    public static final String OD_CREATE_02_VALUE = "创建订单商品库存不足";

    public static final String OD_CREATE_03 = "OD_CREATE_03";
    public static final String OD_CREATE_03_VALUE = "删除购物车商品时出错";

    public static final String OD_UPDATE_01 = "OD_UPDATE_01";
    public static final String OD_UPDATE_01_VALUE = "更新自提人手机号码时对应的自提信息不存在";

    public static final String OD_UPDATE_02 = "OD_UPDATE_02";
    public static final String OD_UPDATE_02_VALUE = "创建新的二维码失败";

    public static final String OD_UPDATE_03 = "OD_UPDATE_03";
    public static final String OD_UPDATE_03_VALUE = "根据pickupId查询对应自提信息为空";

    public static final String OD_UPDATE_05 = "OD_UPDATE_05";
    public static final String OD_UPDATE_05_VALUE = "根据订单id或查找不到对应数据";

    public static final String OD_UPDATE_07 = "OD_UPDATE_07";
    public static final String OD_UPDATE_07_VALUE = "查询不到对应的临时订单";

    public static final String OD_UPDATE_08 = "OD_UPDATE_08";
    public static final String OD_UPDATE_08_VALUE = "订单状态不可修改为支付状态";

    public static final String OD_FEIGN_FAILED = "OD_FEIGN_FAILED";
    public static final String OD_FEIGN_FAILED_VALUE = "服务器内部Feign请求错误";

    public static final String OD_ILLEGAL_USER = "OD_ILLEGAL_USER";
    public static final String OD_ILLEGAL_USER_VALUE = "非法操作用户";

    public static final String OD_TIME_OUT = "OD_TIME_OUT";
    public static final String OD_TIME_OUT_VALUE = "验证码发送相隔时间小于24小时";

    public static final String OD_BE_USED = "OD_BE_USED";
    public static final String OD_BE_USED_VALUE = "该订单已经收货，无法发送自提信息";

    public static final String OD_ILLEGAL_PHONE = "OD_ILLEGAL_PHONE";
    public static final String OD_ILLEGAL_PHONE_VALUE = "请使用正确的的手机号码";

    /**
     * PD
     */
    public static final String PD_PRODUCT_01 = "PD_PRODUCT_01";
    public static final String PD_PRODUCT_01_VALUE = "商品不存在";

    public static final String PD_PRODUCT_02 = "PD_PRODUCT_02";
    public static final String PD_PRODUCT_02_VALUE = "商品已下架";

    /**
     * CT
     */
    public static final String CT_INSERT_01 = "CT_INSERT_01";
    public static final String CT_INSERT_01_VALUE = "数量不能为小于等于零的数";

    public static final String CT_INSERT_02 = "CT_INSERT_02";
    public static final String CT_INSERT_02_VALUE = "存在参数为空";

    public static final String CT_QUERY_01 = "CT_QUERY_01";
    public static final String CT_QUERY_01_VALUE = "商品信息为空";

    public static final String CT_CLIENT_01 = "CT_CLIENT_01";
    public static final String CT_CLIENT_01_VALUE = "库存接口访问超时";

    public static final String CT_CLIENT_02 = "CT_CLIENT_02";
    public static final String CT_CLIENT_02_VALUE = "库存不足";

    public static final String CT_CLIENT_03 = "CT_CLIENT_03";
    public static final String CT_CLIENT_03_VALUE = "库存不存在";
    /**
     * PROMOTE
     */
    public static final String PROMOTE_COUPON_001 = "PROMOTE_COUPON_001";
    public static final String PROMOTE_COUPON_001_VALUE = "兑换码有误";

    public static final String PROMOTE_COUPON_002 = "PROMOTE_COUPON_002";
    public static final String PROMOTE_COUPON_002_VALUE = "优惠券被抢光了";

    public static final String PROMOTE_COUPON_003 = "PROMOTE_COUPON_003";
    public static final String PROMOTE_COUPON_003_VALUE = "您的兑换次数已达到上限";

    /**
     * UPLOAD
     */
    public static final String UPLOAD_01 = "UPLOAD_01";
    public static final String UPLOAD_01_VALUE = "上传文件为空";

    public static final String UPLOAD_02 = "UPLOAD_02";
    public static final String UPLOAD_02_VALUE = "上传文件数量超长";

    public static final String UPLOAD_03 = "UPLOAD_03";
    public static final String UPLOAD_03_VALUE = "上传文件类型不匹配";

    public static final String UPLOAD_04 = "UPLOAD_04";
    public static final String UPLOAD_04_VALUE = "单个文件大小不能超过5MB";

    public static final String UPLOAD_05 = "UPLOAD_05";
    public static final String UPLOAD_05_VALUE = "上传文件总大小不能超过15MB";

    /**
     * THIRD
     */
    // THIRD_F_01
    // THRID_MSG_F_01
    public static final String THIRD_01 = "THIRD_01";
	public static final String THIRD_01_VALUE = "网络连接异常";

	public static final String THIRD_02 = "THIRD_02";
	public static final String THIRD_02_VALUE = "授权码code无效";

	public static final String THIRD_03 = "THIRD_03";
	public static final String THIRD_03_VALUE = "您还没有绑定手机号码";

	public static final String THIRD_04 = "THIRD_04";
	public static final String THIRD_04_VALUE = "数据异常，请联系管理员";

	public static final String THIRD_05 = "THIRD_05";
	public static final String THIRD_05_VALUE = "请求参数错误";

	public static final String THIRD_06 = "THIRD_06";
	public static final String THIRD_06_VALUE = "手机号码格式错误";

	public static final String THIRD_07 = "THIRD_07";
	public static final String THIRD_07_VALUE = "滑块验证失败";

	public static final String THIRD_08 = "THIRD_08";
	public static final String THIRD_08_VALUE = "验证码输入错误次数大于3次";

	public static final String THIRD_09 = "THIRD_09";
	public static final String THIRD_09_VALUE = "验证码错误";

	public static final String THIRD_10 = "THIRD_10";
	public static final String THIRD_10_VALUE = "两次输入密码不一致";

	public static final String THIRD_11 = "THIRD_11";
	public static final String THIRD_11_VALUE = "openId无效";

	public static final String THIRD_12 = "THIRD_12";
	public static final String THIRD_12_VALUE = "授权失效，请重新授权";

	//
	public static final String THIRD_13 = "THIRD_13";
	public static final String THIRD_13_VALUE = "手机号已绑定账户";
	
	public static final String THIRD_14 = "THIRD_14";
	public static final String THIRD_14_VALUE = "type类型错误";
}