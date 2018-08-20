package com.mytaxi.repository;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.mytaxi.dao.CarRepository;
import com.mytaxi.entity.Car;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * @author saransh
 */
public class CarRepositoryTest extends AbstractRepositoryTest
{

    @Autowired
    private CarRepository carRepository;


    @Test
    public void testDriverById()
    {
        Car car = null;
        try
        {
            car = carRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        }
        catch (EntityNotFoundException e)
        {
            e.printStackTrace();
        }
        Assert.assertNotNull(car);
    }


    @Test
    public void testAllCars()
    {
        List<Car> cars = Lists.newArrayList(carRepository.findAll());
        Assert.assertThat(cars, hasSize(3));
    }

}
