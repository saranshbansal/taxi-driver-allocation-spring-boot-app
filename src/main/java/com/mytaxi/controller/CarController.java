package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.dto.CarDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.CarService;

/**
 * All operations with a car will be routed by this controller.
 * @author saransh
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController
{
    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping(value = "/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return new ResponseEntity<>(CarMapper.toDto(carService.find(carId)), HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CarDTO>> getAllCars()
    {
        return new ResponseEntity<>(CarMapper.toDtos(carService.findAllCars()), HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carData) throws EntityNotFoundException
    {
        Car carDO = CarMapper.toEntity(carData);
        return new ResponseEntity<>(CarMapper.toDto(carService.create(carDO)), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<Void> updateCar(@Valid @RequestBody CarDTO carData) throws EntityNotFoundException
    {
        Car carDO = CarMapper.toEntity(carData);
        carService.update(carDO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
