package com.hand.dao;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Service;

/**
 * Created by Vivianus on 2017/1/4.
 */
@Service
public class DeadLetterDao extends BaseDao {
    public DeadLetterDao(){
        super();
        this.clazz = "Deadletter";
        this.hashTag = "deadletter";
        idDescriptor = createFieldDescriptor("DLID", FieldType.TYPE_EQUAL);
    }
}
