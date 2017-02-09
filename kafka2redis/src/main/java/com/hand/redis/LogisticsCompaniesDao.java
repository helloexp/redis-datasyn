package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by ljf on 2016/12/26.
 */
@Repository("logisticsCompaniesDao")
public class LogisticsCompaniesDao extends BaseDao {
    {
        this.clazz = "Base:logisticsCompaniesDao";
        this.hashTag = "base";
        this.idDescriptor = new FieldDescriptor("code");
    }
}
