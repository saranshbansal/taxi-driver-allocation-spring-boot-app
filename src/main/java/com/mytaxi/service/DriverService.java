package com.mytaxi.service;

import java.util.List;
import java.util.Map;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Driver;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface DriverService
{

    Driver find(Long driverId) throws EntityNotFoundException;


    Driver create(Driver driverDO) throws ConstraintsViolationException;


    void delete(Long driverId) throws EntityNotFoundException;


    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;


    List<Driver> find(OnlineStatus onlineStatus);


    DriverDTO selectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;


    void deSelectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;


    List<DriverDTO> findDriversByFilterCriteria(Map<String, String> params) throws EntityNotFoundException;

}
