package com.mytaxi.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.dao.DriverCarRepository;
import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Driver;
import com.mytaxi.entity.DriverCar;

/**
 * @author saransh
 */
public class DriverCarRepositoryTest extends AbstractRepositoryTest
{

    @Autowired
    private DriverCarRepository driverCarRepository;


    @Test
    public void testFindByDriverIdAndCarId()
    {
        DriverCar driverCar = getDriverCar();
        driverCarRepository.save(driverCar);
        DriverCar savedDriverCar = driverCarRepository.findByDriverIdAndCarId(1L, 1L);
        Assert.assertNotNull(savedDriverCar);
    }


    @Test
    public void testFindByDriverSeatCount()
    {
        DriverCar driverCar = getDriverCar();
        driverCarRepository.save(driverCar);
        CarDTO carData = CarDTO.newBuilder().setSeatCount(3).createCarDTO();
        DriverDTO driverData = DriverDTO.newBuilder().setUsername("test").createDriverDTO();
        List<Object[]> drivers = driverCarRepository.findDriverByFilterCriteria(carData, driverData);
        drivers.forEach(obj -> {
            Driver driver = (Driver) obj[0];
            Car car = (Car) obj[1];
            Assert.assertEquals(OnlineStatus.OFFLINE, driver.getOnlineStatus());
            Assert.assertEquals(Integer.valueOf(3), car.getSeatCount());
        });
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testAlreadyExistCarWithDriver()
    {
        DriverCar driverCar1 = getDriverCar();
        DriverCar driverCar2 = getDriverCar();
        driverCarRepository.save(driverCar1);
        DriverCar savedDriverCar = driverCarRepository.findByDriverIdAndCarId(1L, 1L);
        driverCarRepository.save(driverCar2);
        Assert.assertNotNull(savedDriverCar);
    }


    private DriverCar getDriverCar()
    {
        DriverCar driverCar = new DriverCar();
        driverCar.setDriverId(1L);
        driverCar.setCarId(1L);
        return driverCar;
    }

}
