package dev.insaneduck.alphanovetest.batch;


import dev.insaneduck.alphanovetest.entites.StringResponse;
import dev.insaneduck.alphanovetest.exception.BatchTriggerFailedException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class BatchController {
    public static final String CACHE = System.getProperty("user.home")+"/.cache/";
    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/trigger-batch")
    StringResponse triggerBatch(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            File cachedFile = new File(CACHE + fileName);
            multipartFile.transferTo(cachedFile);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobFile", CACHE + fileName)
                    .addLong("startedAt", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            return new StringResponse("Batch job completed successfully!");
        } catch (JobExecutionAlreadyRunningException |
                 JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException |
                 IOException e) {
            //throw new BatchTriggerFailedException("Error triggering batch job: " + e.getMessage());
            return new StringResponse("Error triggering batch job: " + e.getMessage());
        }
    }
}
