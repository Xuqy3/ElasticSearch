package com.xuqy.elasticsearch.controller;

import com.xuqy.elasticsearch.bean.ESBean;
import com.xuqy.elasticsearch.bean.ScenarioBean;
import com.xuqy.elasticsearch.enums.ScenarioCodeEnum;
import com.xuqy.elasticsearch.repository.EsRepository;
import com.xuqy.elasticsearch.utils.PageResult;
import com.xuqy.elasticsearch.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.action.admin.indices.stats.CommonStatsFlags.Flag.Search;

@Slf4j
@RestController
public class EsController {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    EsRepository esRepository;

    @Autowired
    TransportClient client;

    @Autowired
    Query query;

    /**
     * 根据场景搜索
     * @param scenarioCode
     */
    @GetMapping("/query/scenarios")
    public List<ScenarioBean> queryScenarios(String scenarioCode) {
//        String scenarioCode = ScenarioCodeEnum.getValueByScenarioName(scenarioName);
        String scenarioName = ScenarioCodeEnum.getScenarioNameByValue(scenarioCode);
        log.info(scenarioName);
        List<ScenarioBean> scenarioBeans = new ArrayList<>();
        scenarioBeans.add(query.QueryByScenarios(scenarioCode));
        return scenarioBeans;
    }

    /**
     * 显示场景和数量
     * @return
     */
    @GetMapping("/query/findAll")
    public List<ScenarioBean> findAll(){
        List<ScenarioBean> scenarioBeans = new ArrayList<>();
        for (ScenarioCodeEnum scenarioCodeEnum:ScenarioCodeEnum.values()){
            log.info(scenarioCodeEnum.getValue()+"|"+scenarioCodeEnum.getScenarioName());
            ScenarioBean scenarioBean = query.QueryByScenarios(scenarioCodeEnum.getValue());
            log.info(scenarioBean.toString());
            scenarioBeans.add(scenarioBean);
        }
        return scenarioBeans;
    }

    /**
     * 通过场景查找数据
     * @param scenarioCode
     * @return
     */
    @GetMapping("/query/findByScenario/")
    public PageResult findByScenario(String scenarioCode,int page){
        //输入页码并设置每页显示的数量
        PageRequest pageRequest = PageRequest.of(page,20);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //执行es查询并封装为ESBean对象
        ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.queryStringQuery(scenarioCode).field("scenarioCode"));
        Page<ESBean> esPage = esRepository.search(queryBuilder, pageRequest);

        PageResult pageResult = new PageResult();
        pageResult.setPage(esPage.getNumber());
        pageResult.setTotal(esPage.getTotalPages());
        pageResult.setRecords(esPage.getTotalElements());
        pageResult.setRows(esPage.getContent());
        log.info(pageResult.toString());
        return pageResult;
    }


}
