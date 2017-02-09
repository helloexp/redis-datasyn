package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shanks on 2017/1/5.
 */
@Repository
public class SaleActivityDao extends BaseDao {
    public SaleActivityDao(){

        this.clazz = "SaleActivity";
        this.hashTag = "SaleActivity";
        this.idDescriptor = createFieldDescriptor("id", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("ruleId", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("ruleName",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("ruleDes",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("priority",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("isPileUp",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("ruleGroup",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("startTime",FieldType.TYPE_PAGE));
        addFieldDescriptor(createFieldDescriptor("endTime",FieldType.TYPE_PAGE));
        addFieldDescriptor(createFieldDescriptor("isExcludeShow",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("pageShowMes",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("validStartTime",FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("validEndTime",FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("creationTime", FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("lastCreationTime", FieldType.TYPE_RANGE));

    }
    public List<Map<String,Object>> querySaleTemplate(Map<String,Object> map, int page, int pageSize)
    {
        return null;
    }

    public Map<String,Object> submitSaleTemplate(Map<String,Object> map)
    {
        map.put("id", UUID.randomUUID());
        this.add(map);
        return map;
    }
    public Map<String,Object> updateSaleTemplate(Map<String,Object> map)
    {
        this.update(map);
        return map;
    }
    public Map<String,Object> deleteSaleTemplate(Map<String,Object> map)
    {
        if(map.get("id")!=null) {
            this.delete(map.get("id").toString());
        }
        return map;
    }

}
