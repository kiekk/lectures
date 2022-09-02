package io.springbatch.studyspringbatch.batch.chunk.processor;

import io.springbatch.studyspringbatch.batch.domain.ApiRequestVO;
import io.springbatch.studyspringbatch.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class ApiItemProcessor2 implements ItemProcessor<ProductVO, ApiRequestVO> {

    @Override
    public ApiRequestVO process(ProductVO item) throws Exception {
        return ApiRequestVO
                .builder()
                .id(item.getId())
                .productVO(item)
                .build();
    }
}
