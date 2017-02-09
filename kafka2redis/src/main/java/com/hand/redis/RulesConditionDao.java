package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by hand on 2017/1/4.
 */
@Repository
public class RulesConditionDao extends BaseDao {
    {
        this.hashTag = "rules";
        this.clazz = "Rules:condition";
        idDescriptor = createFieldDescriptor("conditionId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("modelId", FieldType.TYPE_EQUAL));
    }
}
