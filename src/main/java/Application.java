import dao.EmployeeDao;
import dao.impl.EmployeeDaoImpl;
import model.Employee;


public class Application {

    public static void main(String[] args) {

        System.out.println("Задание 1");

        EmployeeDao employeeDao = new EmployeeDaoImpl();

        Employee anton = employeeDao.add(new Employee("Anton", "Antonov", "male", 32, 4));
        System.out.println("Добавленный сотрудник - " + anton);
        Employee petr = employeeDao.add(new Employee("Petr", "Petrov", "male", 39, 5));
        System.out.println("Добавленный сотрудник - " + petr);

        System.out.println("Все сотрудники");
        employeeDao.getAll().forEach(System.out::println);

        employeeDao.getById(petr.getId())
                .ifPresent(employee -> System.out.println("Найденный сотрудник - " + employee));

        petr.setAge(29);
        petr.setLastName("Vasyliev");
        petr = employeeDao.update(petr);
        System.out.println("Обновленный сотрудник - " + petr);

        employeeDao.delete(petr)
                .ifPresent(employee -> System.out.println("Удаленный - " + employee));

    }

}
