package com.hand.redis;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * Created by Hand on 2016/12/22.
 */
@Component
public class TestDao extends BaseDao {

    public TestDao(){
        this.clazz = "Test";
        this.hashTag = "test";
        this.idDescriptor = createFieldDescriptor("testId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("userId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("creationTime",FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
    }

}
