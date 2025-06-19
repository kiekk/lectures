package io.springbatch.studyspringbatch.batch.chunk.processor;

import io.springbatch.studyspringbatch.batch.domain.Product;
import io.springbatch.studyspringbatch.batch.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(item, Product.class);
    }
}
