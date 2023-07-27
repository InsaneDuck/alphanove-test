package dev.insaneduck.alphanovetest.batch;


import dev.insaneduck.alphanovetest.entites.Employee;
import dev.insaneduck.alphanovetest.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public BatchConfig(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    @Bean
    @StepScope
    public FlatFileItemReader<Employee> reader(@Value("#{jobParameters[JobFile]}") String inputFile)
    {
        FlatFileItemReader<Employee> employeeFlatFileItemReader = new FlatFileItemReader<Employee>();
        employeeFlatFileItemReader.setResource(new FileSystemResource(new File(inputFile)));
        employeeFlatFileItemReader.setName("employeeItemReader");
        employeeFlatFileItemReader.setLinesToSkip(1);
        employeeFlatFileItemReader.setLineMapper(lineMapper());
        return employeeFlatFileItemReader;
    }

    /**
     * for mapping csv items to object
     * @return returns LineMapper object
     */
    private LineMapper<Employee> lineMapper(){
        DefaultLineMapper<Employee> employeeDefaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("firstName","lastName");

        BeanWrapperFieldSetMapper<Employee> employeeBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        employeeBeanWrapperFieldSetMapper.setTargetType(Employee.class);

        employeeDefaultLineMapper.setLineTokenizer(lineTokenizer);
        employeeDefaultLineMapper.setFieldSetMapper(employeeBeanWrapperFieldSetMapper);

        return employeeDefaultLineMapper;
    }

    /**
     * For modifying values from input csv file of check the values for any errors
     * @return returns Custom EmployeeItemProcessor
     */
    @Bean
    public EmployeeItemProcessor processor()
    {
        return new EmployeeItemProcessor();
    }

    /**
     * for writing extracted data from csv to Database
     * @param dataSource datasource in yml is injected automatically
     * @return returns RepositoryItemWriter
     */
    @Bean
    public RepositoryItemWriter<Employee> writer(DataSource dataSource)
    {
        RepositoryItemWriter<Employee> employeeRepositoryItemWriter = new RepositoryItemWriter<>();
        employeeRepositoryItemWriter.setRepository(employeeRepository);
        employeeRepositoryItemWriter.setMethodName("save");
        return employeeRepositoryItemWriter;
    }

    /**
     * returns Job objected to be injected into Job in BatchController
     * @param jobRepository
     * @param listener
     * @param step1
     * @return returns Job
     */
    @Bean
    public Job importEmployeeJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importEmployeeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    /**
     * for injecting bean for creating the job
     * @param jobRepository
     * @param transactionManager
     * @param reader
     * @param writer
     * @return returns step
     */
    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Employee> reader,
                      EmployeeItemWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<Employee, Employee> chunk(10, transactionManager)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

}

