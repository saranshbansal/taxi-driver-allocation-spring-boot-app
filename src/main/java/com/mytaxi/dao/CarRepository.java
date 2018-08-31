package com.mytaxi.dao;

import com.mytaxi.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long>
{}
