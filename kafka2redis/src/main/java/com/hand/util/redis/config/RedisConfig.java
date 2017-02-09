package com.hand.util.redis.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.config.TestConfig;
import com.hand.producer.DoKfk;
import com.hand.redis.*;
import com.hand.util.redis.Field.PubClient;
import com.hand.util.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by Hand on 2016/11/11.
 */
@Configuration
public class RedisConfig {


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean(name="stringRedisTemplate")
    public StringRedisTemplate makeStringRedisTemplate(){
        StringRedisTemplate redisTemplate =  new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean(name="objectMapper")
    public ObjectMapper makeObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        return  mapper;
    }
    
    @Bean(name="jsonMapper")
    public JsonMapper  makeJsonMapper(){
    	return new JsonMapper();
    }

    @Bean(name="pubClient")
    public PubClient makePubCliet(){
        return new PubClient(jedisConnectionFactory.getHostName(),jedisConnectionFactory.getPort());
    }

    @Bean(name="alipayDao")
    public AlipayDao alipayDao(){
        return new AlipayDao();
    }

//    @Bean(name="areaDao")
//    public AreaDao areaDaoreaD(){
//        return new AreaDao();
//    }

//    public AreaDao areaDaoreaD(){
//        return new AreaDao();
//    }
    @Bean(name="attributionOnlineDao")
    public AttributionOnlineDao attributionOnlineDao(){
        return new  AttributionOnlineDao();
    }

    @Bean(name="attributionRelationOnlineDao")
    public AttributionRelationOnlineDao attributionRelationOnlineDao(){
        return new AttributionRelationOnlineDao();
    }

    @Bean(name="attributionRelationStagedDao")
    public AttributionRelationStagedDao attributionRelationStagedDao(){
        return new AttributionRelationStagedDao();
    }

    @Bean(name="attributionStagedDao")
    public AttributionStagedDao attributionStagedDao(){
        return new AttributionStagedDao();
    }

    @Bean(name="baseStoreDao")
    public BaseStoreDao baseStoreDao(){
        return new BaseStoreDao();
    }

    @Bean(name="captchaDao")
    public CaptchaDao captchaDao(){
        return new CaptchaDao();
    }

    @Bean(name="cartDao")
    public CartDao cartDao(){
        return new CartDao();
    }

    @Bean(name="categoryOnlineDao")
    public CategoryOnlineDao categoryOnlineDao(){
        return new CategoryOnlineDao();
    }

    @Bean(name="categoryRelationOnlineDao")
    public CategoryRelationOnlineDao categoryRelationOnlineDao(){
        return new CategoryRelationOnlineDao();
    }

    @Bean(name="categoryRelationStagedDao")
    public CategoryRelationStagedDao categoryRelationStagedDao(){
        return new CategoryRelationStagedDao();
    }

    @Bean(name="categoryStagedDao")
    public CategoryStagedDao categoryStagedDao(){
        return new CategoryStagedDao();
    }

    @Bean(name="cmsSiteDao")
    public CMSSiteDao cmsSiteDao(){
        return new CMSSiteDao();
    }

    @Bean(name="couponDao")
    public CouponDao couponDao(){
        return new CouponDao();
    }

    @Bean(name="customerCouponDao")
    public CustomerCouponDao customerCouponDao(){
        return new CustomerCouponDao();
    }

    @Bean(name="deliveryTemplateDao")
    public DeliveryTemplateDao deliveryTemplateDao(){
        return new DeliveryTemplateDao();
    }

    @Bean(name="emailPathDao")
    public EmailPathDao emailPathDao(){
        return new EmailPathDao();
    }

    @Bean(name="errorDao")
    public ErrorDao errorDao(){
        return new ErrorDao();
    }

    @Bean(name="evaluationDao")
    public EvaluationDao evaluationDao(){
        return new EvaluationDao();
    }

    @Bean(name="inStockStatusDao")
    public InStockStatusDao inStockStatusDao(){
        return new InStockStatusDao();
    }

    @Bean(name="loginDao")
    public LoginDao loginDao(){
        return new LoginDao();
    }

    @Bean(name="logisticsCompaniesDao")
    public LogisticsCompaniesDao logisticsCompaniesDao(){
        return new LogisticsCompaniesDao();
    }

    @Bean(name="mobileDao")
    public MobileDao mobileDao(){
        return new MobileDao();
    }

    @Bean(name="orderDao")
    public OrderDao orderDao(){
        return new OrderDao();
    }

    @Bean(name="orderExpressDao")
    public OrderExpressDao orderExpressDao(){
        return new OrderExpressDao();
    }

    @Bean(name="orderNodeDao")
    public OrderNodeDao orderNodeDao(){
        return new OrderNodeDao();
    }

    @Bean(name="orderPickupCodeDao")
    public OrderPickupCodeDao orderPickupCodeDao(){
        return new OrderPickupCodeDao();
    }

    @Bean(name="orderPickupDao")
    public OrderPickupDao orderPickupDao(){
        return new OrderPickupDao();
    }

    @Bean(name="orderPriceDao")
    public OrderPriceDao orderPriceDao(){
        return new OrderPriceDao();
    }

    @Bean(name="orderProductDao")
    public OrderProductDao orderProductDao(){
        return new OrderProductDao();
    }

    @Bean(name="orderStatusDao")
    public OrderStatusDao orderStatusDao(){
        return new OrderStatusDao();
    }

    @Bean(name="orderTempDao")
    public OrderTempDao orderTempDao(){
        return new OrderTempDao();
    }

    @Bean(name="paymentModeDao")
    public PaymentModeDao paymentModeDao(){
        return new PaymentModeDao();
    }

    @Bean(name="pointOfServiceDao")
    public PointOfServiceDao pointOfServiceDao(){
        return new PointOfServiceDao();
    }

    @Bean(name="productDetailOnlineDao")
    public ProductDetailOnlineDao productDetailOnlineDao(){
        return new ProductDetailOnlineDao();
    }

    @Bean(name="productDetailStagedDao")
    public ProductDetailStagedDao productDetailStagedDao(){
        return new ProductDetailStagedDao();
    }

    @Bean(name="productRecommendOnlineDao")
    public ProductRecommendOnlineDao productRecommendOnlineDao(){
        return new ProductRecommendOnlineDao();
    }

    @Bean(name="productRecommendStagedDao")
    public ProductRecommendStagedDao productRecommendStagedDao(){
        return new ProductRecommendStagedDao();
    }

    @Bean(name="productResourceOnlineDao")
    public ProductResourceOnlineDao productResourceOnlineDao(){
        return new ProductResourceOnlineDao();
    }

    @Bean(name="productResourceStagedDao")
    public ProductResourceStagedDao productResourceStagedDao(){
        return new ProductResourceStagedDao();
    }

    @Bean(name="productSummaryOnlineDao")
    public ProductSummaryOnlineDao productSummaryOnlineDao(){
        return new ProductSummaryOnlineDao();
    }

    @Bean(name="productSummaryStagedDao")
    public ProductSummaryStagedDao productSummaryStagedDao(){
        return new ProductSummaryStagedDao();
    }

    @Bean(name="promptDao")
    public PromptDao promptDao(){
        return new PromptDao();
    }

    @Bean(name="purchaseAdviceDao")
    public PurchaseAdviceDao purchaseAdviceDao(){
        return new PurchaseAdviceDao();
    }

    @Bean(name="pwdErrorDao")
    public PwdErrorDao pwdErrorDao(){
        return new PwdErrorDao();
    }

    @Bean(name="qqDao")
    public QQDao qqDao(){
        return new QQDao();
    }

    @Bean(name="quickLoginDao")
    public QuickLoginDao quickLoginDao(){
        return new QuickLoginDao();
    }

    @Bean(name="refundDao")
    public RefundDao refundDao(){
        return new RefundDao();
    }

    @Bean(name="rulesActionDao")
    public RulesActionDao rulesActionDao(){
        return new RulesActionDao();
    }

    @Bean(name="rulesConditionDao")
    public RulesConditionDao rulesConditionDao(){
        return new RulesConditionDao();
    }

    @Bean(name="rulesModelDao")
    public RulesModelDao rulesModelDao(){
        return new RulesModelDao();
    }

    @Bean(name="rulesTmplDao")
    public RulesTmplDao rulesTmplDao(){
        return new RulesTmplDao();
    }

    @Bean(name="saleActivityDao")
    public SaleActivityDao saleActivityDao(){
        return new SaleActivityDao();
    }

    @Bean(name="saleDiscountCouponDao")
    public SaleDiscountCouponDao saleDiscountCouponDao(){
        return new SaleDiscountCouponDao();
    }

    @Bean(name="saleTemplateDao")
    public SaleTemplateDao saleTemplateDao(){
        return new SaleTemplateDao();
    }

    @Bean(name="shippingReplacementDao")
    public ShippingReplacementDao shippingReplacementDao(){
        return new ShippingReplacementDao();
    }

    @Bean(name="siteMsgDao")
    public SiteMsgDao siteMsgDao(){
        return new SiteMsgDao();
    }

    @Bean(name="siteMsgReadDao")
    public SiteMsgReadDao siteMsgReadDao(){
        return new SiteMsgReadDao();
    }

    @Bean(name="siteMsgTypeDao")
    public SiteMsgTypeDao siteMsgTypeDao(){
        return new SiteMsgTypeDao();
    }

    @Bean(name="sliderCaptchaDao")
    public SliderCaptchaDao sliderCaptchaDao(){
        return new SliderCaptchaDao();
    }

    @Bean(name="smsDao")
    public SmsDao smsDao(){
        return new SmsDao();
    }

    @Bean(name="staffDao")
    public StaffDao staffDao(){
        return new StaffDao();
    }

    @Bean(name="staffMobileDao")
    public StaffMobileDao staffMobileDao(){
        return new StaffMobileDao();
    }

    @Bean(name="stockCenterDao")
    public StockCenterDao stockCenterDao(){
        return new StockCenterDao();
    }

    @Bean(name="stockDao")
    public StockDao stockDao(){
        return new StockDao();
    }

    @Bean(name="stockStoreDao")
    public StockStoreDao stockStoreDao(){
        return new StockStoreDao();
    }

    @Bean(name="taoAreaDao")
    public TaoAreaDao taoAreaDao(){
        return new TaoAreaDao();
    }

    @Bean(name="taobaoDao")
    public TaobaoDao taobaoDao(){
        return new TaobaoDao();
    }

    @Bean(name="testDao")
    public TestDao testDao(){
        return new TestDao();
    }

    @Bean(name="thirdPartyDao")
    public ThirdPartyDao thirdPartyDao(){
        return new ThirdPartyDao();
    }

    @Bean(name="userAddressDao")
    public UserAddressDao userAddressDao(){
        return new UserAddressDao();
    }

    @Bean(name="userDao")
    public UserDao userDao(){
        return new UserDao();
    }

    @Bean(name="userFavoriteDao")
    public UserFavoriteDao userFavoriteDao(){
        return new UserFavoriteDao();
    }

    @Bean(name="userInfoDao")
    public UserInfoDao userInfoDao(){
        return new UserInfoDao();
    }

    @Bean(name="weiboDao")
    public WeiboDao weiboDao(){
        return new WeiboDao();
    }

    @Bean(name="weixiDao")
    public WeixiDao weixiDao(){
        return new WeixiDao();
    }

    @Bean(name="zoneDeliveryModeDao")
    public ZoneDeliveryModeDao zoneDeliveryModeDao(){
        return new ZoneDeliveryModeDao();
    }

    @Bean(name="zoneDeliveryModeValueDao")
    public ZoneDeliveryModeValueDao zoneDeliveryModeValueDao(){
        return new ZoneDeliveryModeValueDao();
    }

    @Bean(name="doKfk")
    public DoKfk doKfk(){
        return new DoKfk();
    }

    @Bean(name="testConfig")
    public TestConfig testConfig(){
        return new TestConfig();
    }

}
