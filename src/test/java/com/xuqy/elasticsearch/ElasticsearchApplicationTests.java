package com.xuqy.elasticsearch;

import com.xuqy.elasticsearch.controller.EsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    EsController esController;

    @Test
    void contextLoads() {

        System.out.println(esController.queryScenarios("11111011004").toString());
//        esController.findAll();
//        System.out.println(esController.findByScenario("翼支付功能开通",null));
    }

}
