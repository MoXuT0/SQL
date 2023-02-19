package dao;

import model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> add(Employee employee);
    Optional<Employee> getById(int id);
    List<Employee> getAll();
    Optional<Employee> updateById(int id);
    Optional<Employee> deleteById(int id);

}
