package dev.insaneduck.alphanovetest.batch;

import dev.insaneduck.alphanovetest.entites.Employee;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            jdbcTemplate.query("SELECT first_name, last_name FROM employees",
                    (rs, row) -> new Employee(rs.getString(1), rs.getString(2))
            ).forEach(person -> System.out.println("Found "+person+" in the database."));

            //can delete uploaded csv file from cache folder if needed
        }
    }
}
