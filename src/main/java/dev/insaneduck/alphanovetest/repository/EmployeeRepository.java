package dev.insaneduck.alphanovetest.repository;

import dev.insaneduck.alphanovetest.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
