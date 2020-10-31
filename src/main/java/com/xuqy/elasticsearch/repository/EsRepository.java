package com.xuqy.elasticsearch.repository;



import com.xuqy.elasticsearch.bean.ESBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EsRepository extends ElasticsearchRepository<ESBean,String> {


}
