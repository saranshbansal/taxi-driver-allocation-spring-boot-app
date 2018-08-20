package com.mytaxi;

import java.time.ZonedDateTime;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Driver;
import com.mytaxi.entity.DriverCar;
import com.mytaxi.entity.GeoCoordinate;
import com.mytaxi.entity.Manufacturer;

/**
 * @author saransh
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class DataManipulationTest
{

    public Car getCar()
    {
        Car car = new Car();
        car.setId(1L);
        car.setSeatCount(2);
        car.setRating(11.0F);
        car.setDateCreated(ZonedDateTime.now());
        car.setLicensePlate("A01");
        car.setEngineType("engine01");
        car.setConvertible(true);
        
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BMW");
        manufacturer.setId(1L);
        manufacturer.setDateCreated(ZonedDateTime.now());
        car.setManufacturer(manufacturer);
        return car;
    }


    public Manufacturer getManufacturer()
    {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setDateCreated(ZonedDateTime.now());
        manufacturer.setId(1L);
        manufacturer.setName("BMW");
        return manufacturer;
    }


    public CarDTO getCarDTO()
    {
        return CarDTO
            .newBuilder()
            .setRating(4.0F)
            .setEngineType("test")
            .setLicensePlate("A01")
            .setSeatCount(4)
            .setConvertible(true)
            .createCarDTO();
    }


    public Driver getDriver()
    {
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setDateCreated(ZonedDateTime.now());
        driver.setDeleted(false);
        driver.setUsername("test");
        driver.setPassword("test");
        driver.setOnlineStatus(OnlineStatus.ONLINE);
        GeoCoordinate geoCoordinate = new GeoCoordinate(50, 60);
        driver.setCoordinate(geoCoordinate);
        return driver;
    }


    public DriverDTO getDriverDTO()
    {
        GeoCoordinate geoCoordinate = new GeoCoordinate(50, 60);
        return DriverDTO
            .newBuilder()
            .setId(1L)
            .setPassword("test")
            .setUsername("test")
            .setCoordinate(geoCoordinate)
            .createDriverDTO();
    }


    public DriverCar getDriverCar()
    {
        DriverCar driverCar = new DriverCar();
        driverCar.setCarId(1L);
        driverCar.setDriverId(1L);
        driverCar.setCarId(1L);
        return driverCar;
    }
}
