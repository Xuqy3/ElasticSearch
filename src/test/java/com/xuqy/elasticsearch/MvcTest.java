package com.xuqy.elasticsearch;

import com.sun.deploy.net.MessageHeader;
import com.xuqy.elasticsearch.utils.PageResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 使用Spring测试模块提供的测试请求功能，测试curd请求的正确性
 * Spring4测试的时候，需要servlet3.0的支持
 * @author lfy
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class MvcTest {
	@Autowired
    WebApplicationContext context;
	// 虚拟mvc请求，获取到处理结果。
	MockMvc mockMvc;

	@Before
	public void initMokcMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testPage() throws Exception {
		//模拟请求拿到返回值
		MessageHeader jsonObject = new MessageHeader();
		MvcResult result = mockMvc.perform
				(MockMvcRequestBuilders.post("http://127.0.0.1:8080//query/findByScenario?scenarioName=4G业务新装受理开通").
								content(jsonObject.toString()).
								contentType(MediaType.APPLICATION_JSON)).
				andDo(MockMvcResultHandlers.print()).
				andReturn();
		//请求成功以后，请求域中会有PageResult；我们可以取出PageResult进行验证
		MockHttpServletRequest request = result.getRequest();
//		System.out.println(request.getAttribute("pageResult").toString());
		PageResult pageResult = (PageResult)request.getAttribute("pageResult");
		System.out.println(pageResult.getRows());

	}
}
