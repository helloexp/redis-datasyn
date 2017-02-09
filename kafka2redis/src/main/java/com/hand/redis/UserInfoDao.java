package com.hand.redis;


import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDao extends BaseDao {
    {
        this.hashTag = "user";
        this.clazz = "User:info";
        idDescriptor = createFieldDescriptor("userId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("name", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("sex", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("birthday", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("city", FieldType.TYPE_MATCH));
    }
}
