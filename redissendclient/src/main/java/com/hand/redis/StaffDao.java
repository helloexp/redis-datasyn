/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;

/**
 * @author shengli.yuan@hand-china.com
 */
@Repository("staffDao")
public class StaffDao extends BaseDao {
    public StaffDao(){
        super();
        this.clazz = "Base:staff";
        this.hashTag = "base";
        this.idDescriptor = new FieldDescriptor("employeeId");

    }
}
