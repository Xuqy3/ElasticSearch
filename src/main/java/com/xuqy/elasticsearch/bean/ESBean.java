package com.xuqy.elasticsearch.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Document(indexName = "bc_traces", type = "trace", shards = 1, replicas = 0, refreshInterval = "-1")
public class ESBean {

    @Id
    private String id;

    private Object scenarioCode;       //场景代码

    private Object orderId;            //订单号

    private Object traceId;             //交易号

    private Object timestamp;       //交易时间

    private Object total_time;    //订单耗时

    private Object servNo;        //业务号码

}
