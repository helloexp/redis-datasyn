/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author shengli.yuan@hand-china.com
 */
@Repository("staffDao")
public class StaffDao extends BaseDao{
    public StaffDao(){
        super();
        this.clazz = "Base:staff";
        this.hashTag = "base";
        this.idDescriptor = new FieldDescriptor("employeeId");

    }
}
