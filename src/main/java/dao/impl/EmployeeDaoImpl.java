package dao.impl;

import dao.CityDao;
import dao.EmployeeDao;
import jdbc.ConnectionManager;
import model.Employee;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final String INSERT = "INSERT INTO employee(first_name, last_name, gender, age, city_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_LAST_EMPLOYEE = "SELECT * FROM employee ORDER BY id DESC LIMIT 1";

    private final CityDao cityDao = new CityDaoImpl();

    @Override
    public Optional<Employee> add(Employee employee) {
        int cityId;
        if (employee.getCity() != null && cityDao.findById(employee.getCity().getCityId()).isPresent()) {
            cityId = employee.getCity().getCityId();
        }
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setObject(5, employee.getCity());
            if (preparedStatement.executeUpdate() != 0) {
                try (Statement findLastStatement = connection.createStatement();
                ResultSet resultSet = findLastStatement.executeQuery(FIND_LAST_EMPLOYEE)) {
                    if (resultSet.next()) {
                        return Optional.of(readEmployee(resultSet));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Optional<Employee> updateById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> deleteById(int id) {
        return Optional.empty();
    }

    private Employee readEmployee(ResultSet resultSet) {

    }

}
