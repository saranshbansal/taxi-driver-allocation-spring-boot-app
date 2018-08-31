package com.mytaxi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mytaxi.DataManipulationTest;
import com.mytaxi.dao.CarRepository;
import com.mytaxi.dao.ManufacturerRepository;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Manufacturer;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * @author saransh
 */
public class CarServiceTest extends DataManipulationTest
{

    @Mock
    private CarRepository carRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private CarServiceImpl carService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(CarService.class);
    }


    @Test
    public void testFindAllCars()
    {
        Iterable<Car> cars = Collections.singletonList(getCar());
        when(carRepository.findAll()).thenReturn(cars);
        carService.findAllCars();
        verify(carRepository, times(1)).findAll();
    }


    @Test
    public void testCreate() throws EntityNotFoundException
    {
        Car car = getCar();
        Manufacturer manufacturer = getManufacturer();
        when(manufacturerRepository.findByName(any(String.class))).thenReturn(manufacturer);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        carService.create(car);
        verify(manufacturerRepository, times(1)).findByName(any(String.class));
        verify(carRepository, times(1)).save(car);
    }

}
