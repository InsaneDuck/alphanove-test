package dev.insaneduck.alphanovetest.batch;

import dev.insaneduck.alphanovetest.entites.Employee;
import dev.insaneduck.alphanovetest.repository.EmployeeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeItemWriter implements ItemWriter<Employee> {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeItemWriter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        employeeRepository.saveAll(chunk);
    }
}
