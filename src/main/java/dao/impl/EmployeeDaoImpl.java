package dao.impl;

import dao.EmployeeDao;
import hibernate.HibernateSessionFactoryUtil;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public Employee add(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Serializable createdId = session.save(employee);
            Employee createdEmployee = session.get(Employee.class, createdId);
            transaction.commit();
            return createdEmployee;
        }
    }

    @Override
    public Optional<Employee> getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Employee.class, id));
        }
    }

    @Override
    public List<Employee> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    @Override
    public Employee update(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            return employee;
        }
    }

    @Override
    public Optional<Employee> delete(Employee employee) {
        Optional<Employee> employeeOptional = getById(employee.getId());
        if (employeeOptional.isPresent()) {
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(employeeOptional.get());
                transaction.commit();
                return employeeOptional;
            }
        }
        return Optional.empty();
    }

}
