package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/11/28.
 */

@Repository("errorDao")
public class ErrorDao extends BaseDao{

    {
        this.hashTag = "sms";
        this.clazz = "Sms:error";
        idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("mobileNumber", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status", FieldType.TYPE_EQUAL));
    }
}
