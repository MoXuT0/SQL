package dao;

import model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    Employee add(Employee employee);
    Optional<Employee> getById(int id);
    List<Employee> getAll();
    Employee update(Employee employee);
    Optional<Employee> delete(Employee employee);

}
