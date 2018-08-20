package com.mytaxi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.DriverCar;

public interface DriverCarRepository extends CrudRepository<DriverCar, Long>
{
    DriverCar findByDriverIdAndCarId(final Long driverId, final Long carId);


    @Query("SELECT D, C FROM Car C, Driver D, DriverCar DC "
        +
        "WHERE DC.carId = C.id AND DC.driverId = D.id " +
        "AND (C.rating = :#{#carData.rating} OR C.licensePlate = :#{#carData.licensePlate} " +
        "OR C.seatCount = :#{#carData.seatCount} OR C.convertible = :#{#carData.convertible} " +
        "OR C.engineType = :#{#carData.engineType} OR D.onlineStatus = :#{#driverData.status} " +
        "OR D.username = :#{#driverData.username})")
    List<Object[]> findDriverByFilterCriteria(@Param("carData") final CarDTO carData, @Param("driverData") final DriverDTO driverData);

}
