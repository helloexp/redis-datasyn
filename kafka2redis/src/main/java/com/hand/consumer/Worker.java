package com.hand.consumer;

/**
 * Created by vivianus on 17-2-8.
 */

import com.hand.producer.DoKfk;
import com.hand.redis.*;
import com.hand.util.redis.dao.BaseDao;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Worker implements Runnable {

    private ConsumerRecord<String, String> consumerRecord;

    public Worker(ConsumerRecord record) {
        this.consumerRecord = record;
    }

    com.hand.consumer.SpringUtils SpringUtils;
//
//    String[] nameDao = {
//            "com.hand.redis.AlipayDao", "com.hand.redis.AttributionOnlineDao", "com.hand.redis.AttributionRelationOnlineDao", "com.hand.redis.AttributionRelationStagedDao", "com.hand.redis.AttributionStagedDao",
//            "com.hand.redis.BaseStoreDao", "com.hand.redis.CartDao", "com.hand.redis.CategoryOnlineDao", "com.hand.redis.CategoryRelationOnlineDao", "com.hand.redis.CategoryRelationStagedDao",
//            "com.hand.redis.CategoryStagedDao", "com.hand.redis.CMSSiteDao", "com.hand.redis.CouponDao", "com.hand.redis.CustomerCouponDao", "com.hand.redis.DeliveryTemplateDao",
//            "com.hand.redis.EmailPathDao", "com.hand.redis.ErrorDao", "com.hand.redis.EvaluationDao", "com.hand.redis.InStockStatusDao", "com.hand.redis.LoginDao",
//            "com.hand.redis.LogisticsCompaniesDao", "com.hand.redis.MobileDao", "com.hand.redis.OrderDao", "com.hand.redis.OrderExpressDao", "com.hand.redis.OrderNodeDao",
//            "com.hand.redis.OrderPickupCodeDao", "com.hand.redis.OrderPickupDao", "com.hand.redis.OrderPriceDao", "com.hand.redis.OrderProductDao", "com.hand.redis.OrderStatusDao",
//            "com.hand.redis.OrderTempDao", "com.hand.redis.PaymentModeDao", "com.hand.redis.PointOfServiceDao", "com.hand.redis.ProductDetailOnlineDao", "com.hand.redis.ProductDetailStagedDao",
//            "com.hand.redis.ProductRecommendOnlineDao", "com.hand.redis.ProductRecommendStagedDao", "com.hand.redis.ProductResourceOnlineDao", "com.hand.redis.ProductResourceStagedDao", "com.hand.redis.ProductSummaryOnlineDao",
//            "com.hand.redis.ProductSummaryStagedDao", "com.hand.redis.PromptDao", "com.hand.redis.PurchaseAdviceDao", "com.hand.redis.PwdErrorDao", "com.hand.redis.QQDao",
//            "com.hand.redis.QuickLoginDao", "com.hand.redis.RefundDao", "com.hand.redis.RulesActionDao", "com.hand.redis.RulesConditionDao", "com.hand.redis.RulesModelDao",
//            "com.hand.redis.RulesTmplDao", "com.hand.redis.SaleActivityDao", "com.hand.redis.SaleDiscountCouponDao", "com.hand.redis.SaleTemplateDao", "com.hand.redis.ShippingReplacementDao",
//            "com.hand.redis.SiteMsgDao", "com.hand.redis.SiteMsgReadDao", "com.hand.redis.SiteMsgTypeDao", "com.hand.redis.SmsDao", "com.hand.redis.StaffDao",
//            "com.hand.redis.StaffMobileDao", "com.hand.redis.StockCenterDao", "com.hand.redis.StockDao", "com.hand.redis.StockStoreDao", "com.hand.redis.TaoAreaDao",
//            "com.hand.redis.TaobaoDao", "com.hand.redis.TestDao", "com.hand.redis.ThirdPartyDao", "com.hand.redis.UserAddressDao", "com.hand.redis.UserDao",
//            "com.hand.redis.UserFavoriteDao", "com.hand.redis.UserInfoDao", "com.hand.redis.WeiboDao", "com.hand.redis.WeixiDao", "com.hand.redis.ZoneDeliveryModeDao",
//            "com.hand.redis.ZoneDeliveryModeValueDao"};
//
//    String[] tableName = {
//            "hmall:cache:{thirdParty}thirdParty:alipay", "hmall:online:{product}Attr", "hmall:online:{product}Attr:product", "hmall:staged:{product}Attr:product", "hmall:staged:{product}Attr",
//            "hmall:cache:{Base}Base:baseStore", "hmall:cache:{cart}Cart", "hmall:online:{product}Category", "hmall:online:{product}Category:product", "hmall:staged:{product}Category:product",
//            "hmall:staged:{product}Category", "hmall:cache:{Base}Base:cMSSite", "hmall:cache:{promotion}coupon", "hmall:cache:{promotion}coupon:customerCoupon", "hmall:cache:{base}Base:deliveryTemplate",
//            "hmall:cache:{user}User:emailpath", "hmall:cache:{sms}Sms:error", "hmall:cache:{comment}Comment:evaluation", "hmall:cache:{Base}Base:inStockStatus", "hmall:cache:{user}User:login",
//            "hmall:cache:{base}Base:logisticsCompaniesDao", "hmall:cache:{customer}Customer:mobile", "hmall:cache:{order}Order", "hmall:cache:{order}Express", "hmall:cache:{order}Order:node",
//            "hmall:cache:{order}Pickup:code", "hmall:cache:{order}Pickup", "hmall:cache:{order}Order:price", "hmall:cache:{order}Order:details", "hmall:cache:{order}Order:status",
//            "hmall:cache:{order}Order:temp", "hmall:cache:{base}Base:paymentMode", "hmall:cache:{base}Base:pointOfService", "hmall:online:{product}Product", "hmall:staged:{product}Product",
//            "hmall:online:{product}Product:recommend", "hmall:staged:{product}Product:recommend", "hmall:online:{product}Product:resource", "hmall:staged:{product}Product:resource", "hmall:online:{product}Product:summary",
//            "hmall:staged:{product}Product:summary", "hmall:cache:{prompt}Prompt:zh", "hmall:cache:{comment}Comment:purchaseAdvice", "hmall:cache:{user}User:pwderror", "hmall:cache:{thirdParty}thirdParty:qq",
//            "hmall:cache:{user}User:quicklogin", "hmall:cache:{refund}Refund", "hmall:cache:{rules}Rules:action", "hmall:cache:{rules}Rules:condition", "hmall:cache:{rules}Rules:models",
//            "hmall:cache:{rules}Rules:temp", "hmall:cache:{SaleActivity}SaleActivity", "hmall:cache:{SaleDiscountCoupon}SaleDiscountCoupon", "hmall:cache:{SaleTemplate}SaleTemplate", "hmall:cache:{replace}Replace:shippingReplacement",
//            "hmall:cache:{siteMessage}siteMessage", "hmall:cache:{siteMessage}siteMessage:read", "hmall:cache:{siteMessage}siteMessage:type", "hmall:cache:{sms}Sms", "hmall:cache:{base}Base:staff",
//            "hmall:cache:{user}User:staffmobile", "hmall:cache:{stock}Center", "hmall:cache:{stock}stock", "hmall:cache:{stock}Store", "hmall:cache:{base}Base:area",
//            "hmall:cache:{thirdParty}thirdParty:taobao", "hmall:cache:{test}Test", "hmall:cache:{thirdParty}thirdParty", "hmall:cache:{user}User:address", "hmall:cache:{user}User",
//            "hmall:cache:{user}User:favorite", "hmall:cache:{user}User:info", "hmall:cache:{thirdParty}thirdParty:weibo", "hmall:cache:{thirdParty}thirdParty:weixin", "hmall:cache:{base}Base:zoneDeliveryMode",
//            "hmall:cache:{base}Base:zoneDeliveryModeValue"};
//
//    String[] nameId = {
//            "alipayOpenId", "attrId", "uid", "uid", "attrId",
//            "storeId", "cartId", "uid", "uid", "uid",
//            "uid", "cmmsId", "couponCode", "couponId", "deliveryTemplateId",
//            "uid", "uid", "uid", "inStockCode", "customerId",
//            "code", "mobileNumber", "orderId", "expressId", "uid",
//            "uid", "pickupId", "orderId", "uid", "orderId",
//            "tempId", "paymentModeId", "code", "productId", "productId",
//            "uid", "uid", "productCode", "productCode", "productCode",
//            "productCode", "msgCode", "uid", "uid", "qqOpenId",
//            "uid", "refundId", "actionId", "conditionId", "modelId",
//            "tmplId", "id", "id", "id", "replaceId",
//            "uid", "uid", "msgCode", "uid", "employeeId",
//            "mobileNumber", "uid", "stockId", "uid", "id",
//            "taobaoOpenId", "testId", "uid", "addressId", "userId",
//            "uid", "userId", "weiboOpenId", "weixinOpenId", "code",
//            "zoneDeliveryModeValueId"};
//
//    AlipayDao alipayDao = (AlipayDao) SpringUtils.getBean("alipayDao");
//    //    AreaDao areaDao = (AreaDao) SpringUtils.getBean("areaDao");
//    AttributionOnlineDao attributionOnlineDao = (AttributionOnlineDao) SpringUtils.getBean("attributionOnlineDao");
//    AttributionRelationOnlineDao attributionRelationOnlineDao = (AttributionRelationOnlineDao) SpringUtils.getBean("attributionRelationOnlineDao");
//    AttributionRelationStagedDao attributionRelationStagedDao = (AttributionRelationStagedDao) SpringUtils.getBean("attributionRelationStagedDao");
//    AttributionStagedDao attributionStagedDao = (AttributionStagedDao) SpringUtils.getBean("attributionStagedDao");
//    BaseStoreDao baseStoreDao = (BaseStoreDao) SpringUtils.getBean("baseStoreDao");
//    //    CaptchaDao captchaDao = (CaptchaDao) SpringUtils.getBean("captchaDao");
//    CartDao cartDao = (CartDao) SpringUtils.getBean("cartDao");
//    CategoryOnlineDao categoryOnlineDao = (CategoryOnlineDao) SpringUtils.getBean("categoryOnlineDao");
//    CategoryRelationOnlineDao categoryRelationOnlineDao = (CategoryRelationOnlineDao) SpringUtils.getBean("categoryRelationOnlineDao");
//    CategoryRelationStagedDao categoryRelationStagedDao = (CategoryRelationStagedDao) SpringUtils.getBean("categoryRelationStagedDao");
//    CategoryStagedDao categoryStagedDao = (CategoryStagedDao) SpringUtils.getBean("categoryStagedDao");
//    CMSSiteDao cmsSiteDao = (CMSSiteDao) SpringUtils.getBean("cmsSiteDao");
//    CouponDao couponDao = (CouponDao) SpringUtils.getBean("couponDao");
//    CustomerCouponDao customerCouponDao = (CustomerCouponDao) SpringUtils.getBean("customerCouponDao");
//    DeliveryTemplateDao deliveryTemplateDao = (DeliveryTemplateDao) SpringUtils.getBean("deliveryTemplateDao");
//    EmailPathDao emailPathDao = (EmailPathDao) SpringUtils.getBean("emailPathDao");
//    ErrorDao errorDao = (ErrorDao) SpringUtils.getBean("errorDao");
//    EvaluationDao evaluationDao = (EvaluationDao) SpringUtils.getBean("evaluationDao");
//    InStockStatusDao inStockStatusDao = (InStockStatusDao) SpringUtils.getBean("inStockStatusDao");
//    LoginDao loginDao = (LoginDao) SpringUtils.getBean("loginDao");
//    LogisticsCompaniesDao logisticsCompaniesDao = (LogisticsCompaniesDao) SpringUtils.getBean("logisticsCompaniesDao");
//    MobileDao mobileDao = (MobileDao) SpringUtils.getBean("mobileDao");
//    OrderDao orderDao = (OrderDao) SpringUtils.getBean("orderDao");
//    OrderExpressDao orderExpressDao = (OrderExpressDao) SpringUtils.getBean("orderExpressDao");
//    OrderNodeDao orderNodeDao = (OrderNodeDao) SpringUtils.getBean("orderNodeDao");
//    OrderPickupCodeDao orderPickupCodeDao = (OrderPickupCodeDao) SpringUtils.getBean("orderPickupCodeDao");
//    OrderPickupDao orderPickupDao = (OrderPickupDao) SpringUtils.getBean("orderPickupDao");
//    OrderPriceDao orderPriceDao = (OrderPriceDao) SpringUtils.getBean("orderPriceDao");
//    OrderProductDao orderProductDao = (OrderProductDao) SpringUtils.getBean("orderProductDao");
//    OrderStatusDao orderStatusDao = (OrderStatusDao) SpringUtils.getBean("orderStatusDao");
//    OrderTempDao orderTempDao = (OrderTempDao) SpringUtils.getBean("orderTempDao");
//    PaymentModeDao paymentModeDao = (PaymentModeDao) SpringUtils.getBean("paymentModeDao");
//    PointOfServiceDao pointOfServiceDao = (PointOfServiceDao) SpringUtils.getBean("pointOfServiceDao");
//    ProductDetailOnlineDao productDetailOnlineDao = (ProductDetailOnlineDao) SpringUtils.getBean("productDetailOnlineDao");
//    ProductDetailStagedDao productDetailStagedDao = (ProductDetailStagedDao) SpringUtils.getBean("productDetailStagedDao");
//    ProductRecommendOnlineDao productRecommendOnlineDao = (ProductRecommendOnlineDao) SpringUtils.getBean("productRecommendOnlineDao");
//    ProductRecommendStagedDao productRecommendStagedDao = (ProductRecommendStagedDao) SpringUtils.getBean("productRecommendStagedDao");
//    ProductResourceOnlineDao productResourceOnlineDao = (ProductResourceOnlineDao) SpringUtils.getBean("productResourceOnlineDao");
//    ProductResourceStagedDao productResourceStagedDao = (ProductResourceStagedDao) SpringUtils.getBean("productResourceStagedDao");
//    ProductSummaryOnlineDao productSummaryOnlineDao = (ProductSummaryOnlineDao) SpringUtils.getBean("productSummaryOnlineDao");
//    ProductSummaryStagedDao productSummaryStagedDao = (ProductSummaryStagedDao) SpringUtils.getBean("productSummaryStagedDao");
//    PromptDao promptDao = (PromptDao) SpringUtils.getBean("promptDao");
//    PurchaseAdviceDao purchaseAdviceDao = (PurchaseAdviceDao) SpringUtils.getBean("purchaseAdviceDao");
//    PwdErrorDao pwdErrorDao = (PwdErrorDao) SpringUtils.getBean("pwdErrorDao");
//    QQDao qqDao = (QQDao) SpringUtils.getBean("qqDao");
//    QuickLoginDao quickLoginDao = (QuickLoginDao) SpringUtils.getBean("quickLoginDao");
//    RefundDao refundDao = (RefundDao) SpringUtils.getBean("refundDao");
//    RulesActionDao rulesActionDao = (RulesActionDao) SpringUtils.getBean("rulesActionDao");
//    RulesConditionDao rulesConditionDao = (RulesConditionDao) SpringUtils.getBean("rulesConditionDao");
//    RulesModelDao rulesModelDao = (RulesModelDao) SpringUtils.getBean("rulesModelDao");
//    RulesTmplDao rulesTmplDao = (RulesTmplDao) SpringUtils.getBean("rulesTmplDao");
//    SaleActivityDao saleActivityDao = (SaleActivityDao) SpringUtils.getBean("saleActivityDao");
//    SaleDiscountCouponDao saleDiscountCouponDao = (SaleDiscountCouponDao) SpringUtils.getBean("saleDiscountCouponDao");
//    SaleTemplateDao saleTemplateDao = (SaleTemplateDao) SpringUtils.getBean("saleTemplateDao");
//    ShippingReplacementDao shippingReplacementDao = (ShippingReplacementDao) SpringUtils.getBean("shippingReplacementDao");
//    SiteMsgDao siteMsgDao = (SiteMsgDao) SpringUtils.getBean("siteMsgDao");
//    SiteMsgReadDao siteMsgReadDao = (SiteMsgReadDao) SpringUtils.getBean("siteMsgReadDao");
//    SiteMsgTypeDao siteMsgTypeDao = (SiteMsgTypeDao) SpringUtils.getBean("siteMsgTypeDao");
//    //    SliderCaptchaDao sliderCaptchaDao = (SliderCaptchaDao) SpringUtils.getBean("sliderCaptchaDao");
//    SmsDao smsDao = (SmsDao) SpringUtils.getBean("smsDao");
//    StaffDao staffDao = (StaffDao) SpringUtils.getBean("staffDao");
//    StaffMobileDao staffMobileDao = (StaffMobileDao) SpringUtils.getBean("staffMobileDao");
//    StockCenterDao stockCenterDao = (StockCenterDao) SpringUtils.getBean("stockCenterDao");
//    StockDao stockDao = (StockDao) SpringUtils.getBean("stockDao");
//    StockStoreDao stockStoreDao = (StockStoreDao) SpringUtils.getBean("stockStoreDao");
//    TaoAreaDao taoAreaDao = (TaoAreaDao) SpringUtils.getBean("taoAreaDao");
//    TaobaoDao taobaoDao = (TaobaoDao) SpringUtils.getBean("taobaoDao");
//    TestDao testDao = (TestDao) SpringUtils.getBean("testDao");
//    ThirdPartyDao thirdPartyDao = (ThirdPartyDao) SpringUtils.getBean("thirdPartyDao");
//    UserAddressDao userAddressDao = (UserAddressDao) SpringUtils.getBean("userAddressDao");
//    UserDao userDao = (UserDao) SpringUtils.getBean("userDao");
//    UserFavoriteDao userFavoriteDao = (UserFavoriteDao) SpringUtils.getBean("userFavoriteDao");
//    UserInfoDao userInfoDao = (UserInfoDao) SpringUtils.getBean("userInfoDao");
//    WeiboDao weiboDao = (WeiboDao) SpringUtils.getBean("weiboDao");
//    WeixiDao weixiDao = (WeixiDao) SpringUtils.getBean("weixiDao");
//    ZoneDeliveryModeDao zoneDeliveryModeDao = (ZoneDeliveryModeDao) SpringUtils.getBean("zoneDeliveryModeDao");
//    ZoneDeliveryModeValueDao zoneDeliveryModeValueDao = (ZoneDeliveryModeValueDao) SpringUtils.getBean("zoneDeliveryModeValueDao");
//
//    BaseDao[] name = {
//            alipayDao, attributionOnlineDao, attributionRelationOnlineDao, attributionRelationStagedDao, attributionStagedDao,
//            baseStoreDao, cartDao, categoryOnlineDao, categoryRelationOnlineDao, categoryRelationStagedDao,
//            categoryStagedDao, cmsSiteDao, couponDao, customerCouponDao, deliveryTemplateDao,
//            emailPathDao, errorDao, evaluationDao, inStockStatusDao, loginDao,
//            logisticsCompaniesDao, mobileDao, orderDao, orderExpressDao, orderNodeDao,
//            orderPickupCodeDao, orderPickupDao, orderPriceDao, orderProductDao, orderStatusDao,
//            orderTempDao, paymentModeDao, pointOfServiceDao, productDetailOnlineDao, productDetailStagedDao,
//            productRecommendOnlineDao, productRecommendStagedDao, productResourceOnlineDao, productResourceStagedDao, productSummaryOnlineDao,
//            productSummaryStagedDao, promptDao, purchaseAdviceDao, pwdErrorDao, qqDao,
//            quickLoginDao, refundDao, rulesActionDao, rulesConditionDao, rulesModelDao,
//            rulesTmplDao, saleActivityDao, saleDiscountCouponDao, saleTemplateDao, shippingReplacementDao,
//            siteMsgDao, siteMsgReadDao, siteMsgTypeDao, smsDao, staffDao,
//            staffMobileDao, stockCenterDao, stockDao, stockStoreDao, taoAreaDao,
//            taobaoDao, testDao, thirdPartyDao, userAddressDao, userDao,
//            userFavoriteDao, userInfoDao, weiboDao, weixiDao, zoneDeliveryModeDao,
//            zoneDeliveryModeValueDao};

    DoKfk doKfk = (DoKfk) SpringUtils.getBean("doKfk");
//    kafkaProducer kafkaproduce = new kafkaProducer();

    @Override
    public void run() {
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        if (key != null && value != null) {
            String[] names = value.split("\\|");
            for (int i = 0; i < names.length; i++) ;
            if (names[0].equals("add")) {
                HashMap<String, String> map = new HashMap<String, String>();
                // 将json字符串转换成jsonObject
                JSONObject jsonObject = JSONObject.fromObject(names[2]);
                Iterator it = jsonObject.keys();
                // 遍历jsonObject数据，添加到Map对象
                while (it.hasNext()) {
                    String key1 = String.valueOf(it.next());
                    String value1 = jsonObject.get(key1).toString();
                    map.put(key1, value1);
                }

                CommonDao commonDao = new CommonDao(names[1]);
                try {
                    Map<String, Objects> recmap = (Map<String, Objects>) commonDao.selectRecycle(map.get(commonDao.index));
                    Map<String, Objects> commap = (Map<String, Objects>) commonDao.select(map.get(commonDao.index));
                    if (recmap == null) {
                        if (commap == null) {
                            try {
                                commonDao.add(map);
                                System.out.println("操作成功");
                            } catch (Exception e) {
                                doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map));
                                System.out.println("数据库操作失败，转发kafka");
                            }
                        } else {
                            System.out.println("过时数据，不进行操作。");
                        }
                    } else {
                        if (Integer.parseInt(String.valueOf(recmap.get("version"))) > Integer.parseInt(map.get("version"))) {
                            System.out.println("过时数据，不进行操作。");
                        } else {
                            try {
                                commonDao.add(map);
                            } catch (Exception e) {
                                doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map));
                                System.out.println("数据库操作失败，转发kafka");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (names[0].equals("del")) {
                HashMap<String, String> map2 = new HashMap<String, String>();
                // 灏唈son瀛楃涓茶浆鎹㈡垚jsonObject
                JSONObject jsonObject = JSONObject.fromObject(names[3]);
                Iterator it = jsonObject.keys();
                // 閬嶅巻jsonObject鏁版嵁锛屾坊鍔犲埌Map瀵硅薄
                while (it.hasNext()) {
                    String key1 = String.valueOf(it.next());
                    String value1 = jsonObject.get(key1).toString();
                    map2.put(key1, value1);
                }
                CommonDao commonDao1 = new CommonDao(names[1]);
                try {
                    try {
                        commonDao1.addRecycle(map2);
                    } catch (Exception e) {
                        doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map2));
                        System.out.println("数据库操作失败，转发kafka");
                    }
                    Map<String, Objects> commap2 = (Map<String, Objects>) commonDao1.select(names[2]);
                    if (commap2 == null) {
                        System.out.println("操作过时");
                    } else {
                        if (Integer.parseInt(String.valueOf(commap2.get("version"))) <= Integer.parseInt(map2.get("version"))) {
                            try {
                                commonDao1.delete(names[2]);
                            } catch (Exception e) {
                                doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map2));
                                System.out.println("数据库操作失败，转发kafka");
                            }
                        } else {
                            System.out.println("操作过时");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (names[0].equals("up")) {
                HashMap<String, String> map1 = new HashMap<String, String>();
                // 灏唈son瀛楃涓茶浆鎹㈡垚jsonObject
                JSONObject jsonObject = JSONObject.fromObject(names[2]);
                Iterator it = jsonObject.keys();
                // 閬嶅巻jsonObject鏁版嵁锛屾坊鍔犲埌Map瀵硅薄
                while (it.hasNext()) {
                    String key1 = String.valueOf(it.next());
                    String value1 = jsonObject.get(key1).toString();
                    map1.put(key1, value1);
                }
                CommonDao commonDao2 = new CommonDao(names[1]);

                try {
                    Map<String, Objects> remap1 = (Map<String, Objects>) commonDao2.selectRecycle(map1.get(commonDao2.index));
                    Map<String, Objects> commap1 = (Map<String, Objects>) commonDao2.select(map1.get(commonDao2.index));
                    if (remap1 == null) {
                        if (commap1 == null) {
                            try {
                                commonDao2.add(map1);
                            } catch (Exception e) {
                                doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map1));
                                System.out.println("操作失败，转发kafka");
                            }
                        } else {
                            if (Integer.parseInt(String.valueOf(commap1.get("version"))) < Integer.parseInt(map1.get("version"))) {
                                try {
                                    commonDao2.update(map1);
                                } catch (Exception e) {
                                    doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map1));
                                    System.out.println("操作失败，转发kafka");
                                }
                            } else {
                                System.out.println("过时数据，不进行操作。");
                            }
                        }
                    } else {
                        if (Integer.parseInt(String.valueOf(remap1.get("version"))) > Integer.parseInt(map1.get("version"))) {
                            System.out.println("过时数据，不进行操作。");
                        } else {
                            try {
                                commonDao2.add(map1);
                            } catch (Exception e) {
                                doKfk.onMessage("数据库操作失败，转发kafka" + String.valueOf(map1));
                                System.out.println("数据库操作失败，转发kafka");
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}