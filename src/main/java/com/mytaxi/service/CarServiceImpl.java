package com.mytaxi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dao.CarRepository;
import com.mytaxi.dao.ManufacturerRepository;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Manufacturer;
import com.mytaxi.exception.EntityNotFoundException;

@Service
public class CarServiceImpl implements CarService
{

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    @Override
    public Car find(final Long carId) throws EntityNotFoundException
    {
        return carCheck(carId);
    }


    @Override
    public List<Car> findAllCars()
    {
        List<Car> target = new ArrayList<>();
        Iterable<Car> iterable = carRepository.findAll();
        iterable.forEach(target::add);
        return target;
    }


    @Override
    public Car create(final Car car) throws EntityNotFoundException
    {
        car.setManufacturer(manufacturerCheck(car));
        return carRepository.save(car);
    }


    @Override
    @Transactional
    public void update(final Car car) throws EntityNotFoundException
    {
        Car updateCar = carCheck(car.getId());
        updateCar.setManufacturer(manufacturerCheck(car));
        updateCar.setConvertible(car.getConvertible());
        updateCar.setEngineType(car.getEngineType());
        updateCar.setLicensePlate(car.getLicensePlate());
        updateCar.setRating(car.getRating());
        updateCar.setSeatCount(car.getSeatCount());
    }


    @Override
    @Transactional
    public void delete(final Long carId) throws EntityNotFoundException
    {
        Car car = carCheck(carId);
        car.setDeleted(Boolean.TRUE);
    }


    private Car carCheck(final Long carId) throws EntityNotFoundException
    {
        return carRepository
            .findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }


    private Manufacturer manufacturerCheck(final Car car) throws EntityNotFoundException
    {
        String manufacturerName = car.getManufacturer().getName();
        Manufacturer manufacturer = manufacturerRepository.findByName(manufacturerName);
        if (null == manufacturer)
        {
            throw new EntityNotFoundException("Manufacturer not found with this name: " + manufacturerName);
        }
        return manufacturer;
    }
}
