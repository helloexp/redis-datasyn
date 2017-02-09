package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Hand on 2016/11/24.
 */
@Repository
public class OrderTempDao extends BaseDao{
    public OrderTempDao(){
        this.clazz ="Order:temp";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("tempId", FieldType.TYPE_EQUAL);
//        addFieldDescriptor(createFieldDescriptor("flag",FieldType.TYPE_EQUAL));
    }
}
