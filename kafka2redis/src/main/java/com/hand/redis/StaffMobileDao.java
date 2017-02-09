package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/12/28.
 */

@Repository("staffMobileDao")
public class StaffMobileDao extends BaseDao{

    {
        this.hashTag = "user";
        this.clazz = "User:staffmobile";
        idDescriptor = createFieldDescriptor("mobileNumber", FieldType.TYPE_EQUAL);
    }
}
