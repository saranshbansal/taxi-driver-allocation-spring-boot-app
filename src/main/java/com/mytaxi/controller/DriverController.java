package com.mytaxi.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Driver;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.toDto(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        Driver driverDO = DriverMapper.toEntity(driverDTO);
        return DriverMapper.toDto(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDriversByStatus(@RequestParam OnlineStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return DriverMapper.toDtos(driverService.find(onlineStatus));
    }


    @PostMapping("/select")
    public DriverDTO selectCarByDriver(@RequestParam long driverId, @RequestParam long carId)
        throws EntityNotFoundException,
        CarAlreadyInUseException
    {

        return driverService.selectCarByDriver(driverId, carId);
    }


    @DeleteMapping("/deselect")
    public void deSelectCarByDriver(@RequestParam long driverId, @RequestParam long carId)
        throws EntityNotFoundException,
        CarAlreadyInUseException
    {
        driverService.deSelectCarByDriver(driverId, carId);
    }


    @PostMapping("/search")
    public List<DriverDTO> findDriversByFilterCriteria(@RequestBody Map<String, String> params)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return driverService.findDriversByFilterCriteria(params);
    }

}
