package com.hand.redis;


import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserAddressDao extends BaseDao {
    {
        this.hashTag = "user";
        this.clazz = "User:address";
        idDescriptor = createFieldDescriptor("addressId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("consignee", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("region", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("address", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("zipcode", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("mobilenumber", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("fixednumber", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("isDefault", FieldType.TYPE_EQUAL));
    }
}
