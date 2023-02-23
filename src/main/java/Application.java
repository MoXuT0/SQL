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

        City samara = new City("Samara");

        List<Employee> employees = List.of(new Employee("Anton", "Antonov", "male", 32, samara),
                new Employee("Petr", "Petrov", "male", 39, samara));

        samara.setEmployees(employees);

        cityDao.add(samara);

    }

}
