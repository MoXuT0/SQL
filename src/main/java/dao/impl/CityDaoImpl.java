package dao.impl;

import dao.CityDao;
import hibernate.HibernateSessionFactoryUtil;
import model.City;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CityDaoImpl implements CityDao {

    @Override
    public City add(City city) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Serializable createdId = session.save(city);
            City createdCity = session.get(City.class, createdId);
            transaction.commit();
            return createdCity;
        }
    }

    @Override
    public Optional<City> getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(City.class, id));
        }
    }

    @Override
    public List<City> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM City", City.class).list();
        }
    }

    @Override
    public City update(City city) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(city);
            transaction.commit();
            return city;
        }
    }

    @Override
    public Optional<City> delete(City city) {
        Optional<City> cityOptional = getById(city.getCityId());
        if (cityOptional.isPresent()) {
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(cityOptional.get());
                transaction.commit();
                return cityOptional;
            }
        }
        return Optional.empty();
    }
}
