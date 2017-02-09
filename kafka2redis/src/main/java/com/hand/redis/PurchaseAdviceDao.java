package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/12/23.
 */

@Repository("purchaseAdviceDao")
public class PurchaseAdviceDao extends BaseDao{
    {
        this.clazz = "Comment:purchaseAdvice";
        this.hashTag = "comment";
        this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("productCode",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("approvedStatus",FieldType.TYPE_EQUAL));
        addPagingField("productCode", "replyTime");
    }
}
