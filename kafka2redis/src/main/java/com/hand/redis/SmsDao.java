package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by DongFan on 2016/11/15.
 */
@Repository("smsDao")
public class SmsDao extends BaseDao {

    public SmsDao() {
        this.hashTag = "sms";
        this.clazz = "Sms";
        idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("mobile", FieldType.TYPE_MATCH));
    }

}
