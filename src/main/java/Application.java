import dao.CityDao;
import dao.EmployeeDao;
import dao.impl.CityDaoImpl;
import dao.impl.EmployeeDaoImpl;
import model.City;
import model.Employee;

import java.util.List;


public class Application {

    public static void main(String[] args) {

        System.out.println("Задание 1");

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        CityDao cityDao = new CityDaoImpl();

        City city = new City("Vladivostok");

        List<Employee> employees = List.of(new Employee("Anton", "Antonov", "male", 32, city),
                new Employee("Petr", "Petrov", "male", 39, city));

        city.setEmployees(employees);

        cityDao.add(city);
        employeeDao.getAll().forEach(System.out::println);

        cityDao.getById(city.getCityId());

        cityDao.getAll().forEach(System.out::println);

        city.setCityName("Volgograd");
        cityDao.update(city);
        cityDao.getAll().forEach(System.out::println);

        cityDao.delete(city);

        employeeDao.getAll().forEach(System.out::println);
        cityDao.getAll().forEach(System.out::println);

    }

}
