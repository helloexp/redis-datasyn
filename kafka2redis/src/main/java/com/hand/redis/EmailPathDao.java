package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/12/8.
 */

@Repository("emailPathDao")
public class EmailPathDao extends BaseDao {
    {
        this.hashTag = "user";
        this.clazz = "User:emailpath";
        idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("email", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status", FieldType.TYPE_EQUAL));
    }
}
