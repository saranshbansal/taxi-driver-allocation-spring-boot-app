package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;

import com.mytaxi.dto.CarDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Manufacturer;

public class CarMapper
{
    public static Car toEntity(CarDTO carDTO)
    {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(carDTO.getManufacturer());
        return new Car(carDTO.getRating(), carDTO.getEngineType(), carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(), manufacturer);
    }


    public static CarDTO toDto(Car carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setId(carDO.getId())
                .setRating(carDO.getRating())
                .setEngineType(carDO.getEngineType())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setConvertible(carDO.getConvertible());

        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> toDtos(Collection<Car> cars)
    {
        return cars
            .stream()
            .map(CarMapper::toDto)
            .collect(Collectors.toList());
    }


    public static CarDTO convertParamsToDto(Map<String, String> searchParams)
    {
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setRating(MapUtils.getFloat(searchParams, "rating"))
                .setEngineType(MapUtils.getString(searchParams, "engineType"))
                .setConvertible(MapUtils.getBoolean(searchParams, "convertible"))
                .setLicensePlate(MapUtils.getString(searchParams, "licensePlate"))
                .setSeatCount(MapUtils.getInteger(searchParams, "seatCount"));

        return carDTOBuilder.createCarDTO();
    }
}
