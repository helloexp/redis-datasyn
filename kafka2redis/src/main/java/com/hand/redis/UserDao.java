package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author cw
 */

@Repository("userDao")
public class UserDao extends BaseDao {
    {
        this.hashTag = "user";
        this.clazz = "User";
        idDescriptor = createFieldDescriptor("userId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("groups", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("name", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("email", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("mobileNumber", FieldType.TYPE_MATCH));
    }
}
