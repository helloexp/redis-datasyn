package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ljf on 2016/11/28.
 */
@Repository("userFavoriteDao")
public class UserFavoriteDao extends BaseDao {
    private static final String ZSET = "z";

    {
        this.hashTag = "user";
        this.clazz = "User:favorite";
        idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
        this.addPagingField("userId", "creationTime");

    }


}

