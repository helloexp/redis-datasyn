package com.hand.redis;

public enum OrderStatus {
    WAIT_BUYER_PAY,//代付款状态
    WAIT_SELLER_SEND_GOODS,//待发货状态
    SELLER_CONSIGNED_PART,//部分发货状态
    WAIT_BUYER_CONFIRM_GOODS,//等待买家确认收货
    WAIT_BUYER_TAKE_GOODS,//待自提
    TRADE_FINISHED,//交易成功
    TRADE_CLOSED_BY_UNIQLO,//付款以前，卖家或买家主动关闭交易
    TRADE_CLOSED,//付款以后用户退款成功，交易自动关闭

    //退换货流程
    WAIT_BUYER_RETURN_GOODS,//待买家寄回商品
    WAIT_SELLER_CONFIRM_GOODS,//待卖家收货
}
