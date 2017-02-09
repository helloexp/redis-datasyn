package com.hand.util.redis.dao;

import com.hand.util.redis.Field.FieldType;
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
