package dao;

import model.City;
import model.Employee;

import java.util.List;
import java.util.Optional;

public interface CityDao {

    City add(City city);
    Optional<City> getById(int id);
    List<City> getAll();
    City update(City city);
    Optional<City> delete(City city);

}
