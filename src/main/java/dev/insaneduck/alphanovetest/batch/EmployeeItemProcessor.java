package dev.insaneduck.alphanovetest.batch;

import dev.insaneduck.alphanovetest.entites.Employee;
import jakarta.validation.constraints.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeItemProcessor implements ItemProcessor<Employee,Employee> {
    @Override
    public Employee process(@NotNull Employee employee) throws Exception {
        //custom filters if needed
        return employee;
    }
}
