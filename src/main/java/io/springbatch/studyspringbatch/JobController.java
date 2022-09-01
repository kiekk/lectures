package io.springbatch.studyspringbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobRegistry jobRegistry;
    private final JobExplorer jobExplorer;
    private final JobOperator jobOperator;

    @PostMapping("/batch/start")
    public String start(@RequestBody JobInfo jobInfo) throws NoSuchJobException, JobInstanceAlreadyExistsException, JobParametersInvalidException {

        for (String jobName : jobRegistry.getJobNames()) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(jobName);
            System.out.println("jobName : " + jobName);

            jobOperator.start(job.getName(), "id=" + jobInfo.getId());
        }
        return "batch is started";
    }

    @PostMapping("/batch/stop")
    public String stop() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {

        for (String jobName : jobRegistry.getJobNames()) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(jobName);
            System.out.println("jobName : " + jobName);

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);

            for (JobExecution runningJobExecution : runningJobExecutions) {
                jobOperator.stop(runningJobExecution.getId());
            }
        }
        return "batch is stopped";
    }

    @PostMapping("/batch/restart")
    public String restart() throws NoSuchJobException, NoSuchJobExecutionException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException {

        for (String jobName : jobRegistry.getJobNames()) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(jobName);
            System.out.println("jobName : " + job.getName());

            JobInstance lastJobInstance = jobExplorer.getLastJobInstance(jobName);

            if (null != lastJobInstance) {
                JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);

                if (null != lastJobExecution) {
                    jobOperator.restart(lastJobExecution.getId());
                }
            }

        }
        return "batch is stopped";
    }

}
