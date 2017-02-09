package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/12/7.
 */

@Repository("pwdErrorDao")
public class PwdErrorDao extends BaseDao{

    {
        this.hashTag = "user";
        this.clazz = "User:pwderror";
        idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("customerId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status", FieldType.TYPE_EQUAL));
    }
}
