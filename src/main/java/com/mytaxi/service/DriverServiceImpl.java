package com.mytaxi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.dao.DriverCarRepository;
import com.mytaxi.dao.DriverRepository;
import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Driver;
import com.mytaxi.entity.DriverCar;
import com.mytaxi.entity.GeoCoordinate;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DriverServiceImpl implements DriverService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DriverServiceImpl.class);

    @Autowired
    private CarService carService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverCarRepository driverCarRepository;

    @Autowired
    private DriverCarService driverCarService;


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public Driver find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public Driver create(Driver driverDO) throws ConstraintsViolationException
    {
        Driver driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        Driver driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        Driver driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<Driver> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private Driver findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository
            .findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    @Override
    public DriverDTO selectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        Object[] object = new Object[2];
        Driver driver;
        Car car;
        try
        {
            driver = find(driverId);
            car = carService.find(carId);
            if (null != driver && null != car && OnlineStatus.ONLINE.equals(driver.getOnlineStatus()))
            {
                DriverCar driverCar = new DriverCar();
                driverCar.setDriverId(driver.getId());
                driverCar.setCarId(car.getId());
                driverCarService.save(driverCar);
                object[0] = driver;
                object[1] = car;
            }
            else if (null != driver && null != car && OnlineStatus.OFFLINE.equals(driver.getOnlineStatus()))
            {
                throw new CarAlreadyInUseException("Driver is offline");
            }
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CarAlreadyInUseException("Car is already taken by driver");
        }
        return DriverMapper.toDto(object);
    }


    @Override
    public void deSelectCarByDriver(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        Driver driver;
        Car car;
        try
        {
            driver = find(driverId);
            car = carService.find(carId);
            if (null != driver && null != car && OnlineStatus.ONLINE.equals(driver.getOnlineStatus()))
            {
                DriverCar driverCar = driverCarService.findByDriverIdAndCarId(driver.getId(), car.getId());
                driverCarService.delete(driverCar);
            }
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        catch (InvalidDataAccessApiUsageException e)
        {
            throw new CarAlreadyInUseException("Car is already deselected by the driver");
        }
    }


    @Override
    public List<DriverDTO> findDriversByFilterCriteria(Map<String, String> params) throws EntityNotFoundException
    {
        List<DriverDTO> driverDataList = new ArrayList<>();
        try
        {
            CarDTO carFilter = CarMapper.convertParamsToDto(params);
            DriverDTO driverFilter = DriverMapper.convertParamsToDto(params);

            List<Object[]> drivers = driverCarRepository.findDriverByFilterCriteria(carFilter, driverFilter);

            drivers.forEach(object -> driverDataList.add(DriverMapper.toDto(object)));
        }
        catch (Exception e)
        {
            throw new EntityNotFoundException("Driver entity not found ");
        }

        return driverDataList;
    }

}
