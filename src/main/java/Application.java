import dao.EmployeeDao;
import dao.impl.EmployeeDaoImpl;
import jdbc.ConnectionManager;
import model.City;
import model.Employee;

import java.sql.*;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        System.out.println("Задание 1");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE id = 1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(
                        "id: " + resultSet.getInt("id")
                        + " name: " + resultSet.getString("first_name")
                        + " last name: " + resultSet.getString("last_name")
                        + " gender: " + resultSet.getString("gender")
                        + " age: " + resultSet.getInt("age")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Задание 2");

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        City samara = new City(4, "Samara");
        City ufa = new City(5, "Ufa");

        employeeDao.add(new Employee("Anton", "Antonov", "male", 32, samara))
                .ifPresent(employee -> System.out.println("Добавленный сотрудник - " + employee));
        Optional<Employee> employeeOptional = employeeDao.add(new Employee("Petr", "Petrov", "male", 39, ufa));
        employeeOptional.ifPresent(employee -> System.out.println("Добавленный сотрудник - " + employee));

        System.out.println("Все сотрудники");
        employeeDao.getAll().forEach(System.out::println);

        if(employeeOptional.isPresent()) {
            employeeDao.getById(employeeOptional.get().getId()).ifPresent(employee -> System.out.println("Найденный сотрудник - " + employee));
        }

        if(employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setAge(29);
            employee.setLastName("Vasyliev");
            employeeDao.update(employee).ifPresent(employee1 -> System.out.println("Обновленный сотрудник - " + employee1));
        }

        if(employeeOptional.isPresent()) {
            employeeDao.deleteById(employeeOptional.get().getId()).ifPresent(employee -> System.out.println("Удаленный сотрудник - " + employee));
        }

    }

}
