package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.Iterator;

public interface CarRepoInterface {
    Car create(Car car);
    Car findById(String id);
    Iterator<Car> findAll();
    Car update(String id, Car car);
    void delete(String id);
}
