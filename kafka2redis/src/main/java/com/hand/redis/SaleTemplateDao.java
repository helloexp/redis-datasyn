package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shanks on 2017/1/4.
 */
@Repository
public class SaleTemplateDao extends BaseDao{

    public SaleTemplateDao(){

        this.clazz = "SaleTemplate";
        this.hashTag = "SaleTemplate";
        this.idDescriptor = createFieldDescriptor("id", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("templateId", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("templateName",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("templateDes",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("ruleGroup",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("maxRuleExecute",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("pageShowMes",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("creationTime", FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("lastCreationTime", FieldType.TYPE_RANGE));

    }

    public List<Map<String,Object>> querySaleTemplate(Map<String,Object> map,int page,int pageSize)
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
