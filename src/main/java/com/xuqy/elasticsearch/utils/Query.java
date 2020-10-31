package com.xuqy.elasticsearch.utils;

import com.xuqy.elasticsearch.bean.ScenarioBean;
import com.xuqy.elasticsearch.enums.ScenarioCodeEnum;
import com.xuqy.elasticsearch.repository.EsRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Query {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    EsRepository esRepository;

    @Autowired
    TransportClient client;


    public ScenarioBean QueryByScenarios(String scenarioCode){
        QueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("scenarioCode", scenarioCode));

        TermsAggregationBuilder builder = AggregationBuilders.terms("group_scenarioCode").field("scenarioCode");
        SearchResponse searchResponse = client.prepareSearch("bc_traces").setQuery(query).addAggregation(builder)
                .setFrom(0).setSize(5)// 分页
                .get();
//        log.info(searchResponse.toString());

        //解析聚合
        Aggregations aggregations = searchResponse.getAggregations();
        StringTerms terms = aggregations.get("group_scenarioCode");
        List<StringTerms.Bucket> buckets = terms.getBuckets();
        ScenarioBean scenarioBean = new ScenarioBean();
        for (StringTerms.Bucket bucket : buckets) {
            scenarioBean.setCount(bucket.getDocCount());
            scenarioBean.setScenarioCode(ScenarioCodeEnum.getScenarioNameByValue(bucket.getKeyAsString()));
            scenarioBean.setScenarioCode(bucket.getKey());
            scenarioBean.setScenarioName(ScenarioCodeEnum.getScenarioNameByValue(bucket.getKeyAsString()));
            break;
        }
        return scenarioBean;
    }
}
