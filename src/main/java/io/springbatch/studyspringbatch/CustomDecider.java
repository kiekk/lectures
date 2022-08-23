package io.springbatch.studyspringbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CustomDecider implements JobExecutionDecider {

    private int count = 0;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        return ++count % 2 == 0 ? new FlowExecutionStatus("EVEN") : new FlowExecutionStatus("ODD");
    }
}
