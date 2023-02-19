package dao.impl;

import dao.CityDao;
import dao.EmployeeDao;
import jdbc.ConnectionManager;
import model.City;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final String INSERT = "INSERT INTO employee(first_name, last_name, gender, age, city_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_LAST_EMPLOYEE = "SELECT * FROM employee ORDER BY id DESC LIMIT 1";
    private static final String FIND_BY_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM employee";
    private static final String UPDATE = "UPDATE employee SET first_name = ?, last_name = ?, gender = ?, age = ?, city_id = ?";

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
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(readEmployee(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                employees.add(readEmployee(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Optional<Employee> updateById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> deleteById(int id) {
        return Optional.empty();
    }

    private Employee readEmployee(ResultSet resultSet) throws SQLException {
        int cityId = resultSet.getObject("city_id", int.class);
        City city = null;
        if (cityId != 0) {
            city = cityDao.findById(cityId).orElse(null);
        }
        return new Employee(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("gender"),
                resultSet.getInt("age"),
                city
        );
    }

}
