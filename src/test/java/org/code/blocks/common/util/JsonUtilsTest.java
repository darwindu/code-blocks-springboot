package org.code.blocks.common.util;

import java.util.HashMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * jackson测试
 * @author darwindu
 * @date 2019/3/27
 **/
@Slf4j
public class JsonUtilsTest {

    @Data
    class Detail {
        String cardId;
        String carType;
        public Detail(){}
    }

    @Test
    public void testObjToJson() {

        HashMap<String, Object> propertitesMap = new HashMap<>();
        propertitesMap.put("id", 1);
        propertitesMap.put("name", "this is name");

        Detail detail = new Detail();
        detail.setCardId("360");
        detail.setCarType("身份证");
        propertitesMap.put("detail", detail);

        String propertites = JsonUtils.objToJson(propertitesMap);
        log.info("[testObjToJsonStr] propertites:{}", propertites);
        Assert.assertNotNull(propertites);
    }

    @Test
    public void testJsonToObj() {

        String s = "{\"name\":\"zhang san\", \"age\":21}";
        HashMap<String, Object> propertitesMap = JsonUtils.jsonToObj(s, HashMap.class);

        log.info("[testJsonStrToObj] propertites:{}", propertitesMap);
        Assert.assertNotNull(propertitesMap);
    }

    @Test
    public void testJsonToObjTt() {

        /*String s = "{\"orderNo\":\"001\", \"data\":{\"cardId\":\"10\", \"carType\":\"1\"}}";

        Order1<Detail1> order = JsonUtils.jsonToObj(s, new TypeReference<Order1<Detail1>>(){});
        log.info("[testJsonToObjTt] propertites:{}", order);
        Assert.assertNotNull(order);*/
    }
}
