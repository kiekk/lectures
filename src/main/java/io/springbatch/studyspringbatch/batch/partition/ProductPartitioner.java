package io.springbatch.studyspringbatch.batch.partition;

import io.springbatch.studyspringbatch.batch.domain.ProductVO;
import io.springbatch.studyspringbatch.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;

        for (ProductVO productVO : productList) {
            ExecutionContext value = new ExecutionContext();
            value.put("product", productVO);
            result.put("partition" + number, value);
            number++;
        }

        return result;
    }
}
