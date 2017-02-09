package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by cw on 2016/11/16.
 */
@Repository("loginDao")
public class LoginDao extends BaseDao {
    public LoginDao() {
        this.hashTag = "user";
        this.clazz = "User:login";
        idDescriptor = new FieldDescriptor("customerId");
    }
}