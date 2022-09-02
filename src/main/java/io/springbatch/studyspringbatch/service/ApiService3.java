package io.springbatch.studyspringbatch.service;

import io.springbatch.studyspringbatch.batch.domain.ApiInfo;
import io.springbatch.studyspringbatch.batch.domain.ApiResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService3 extends AbstractApiService {

    @Override
    protected ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8083/product/3", apiInfo, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        return ApiResponseVO.builder().status(statusCodeValue).msg(responseEntity.getBody()).build();
    }
}