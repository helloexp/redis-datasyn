
package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/12/1.
 */
@Repository("evaluationDao")
public class EvaluationDao extends BaseDao {

    {
        this.clazz = "Comment:evaluation";
        this.hashTag = "comment";
        this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("orderId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("approvedStatus", FieldType.TYPE_EQUAL));
        addPagingField("productCode", "creationTime");
    }
}

