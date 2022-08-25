package io.springbatch.studyspringbatch;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer2> {

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Customer2 process(Customer item) throws Exception {
        return mapper.map(item, Customer2.class);
    }
}
