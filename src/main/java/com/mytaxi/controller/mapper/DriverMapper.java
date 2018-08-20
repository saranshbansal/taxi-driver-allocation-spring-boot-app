package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;

import com.mytaxi.dto.CarDTO;
import com.mytaxi.dto.DriverDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.entity.Driver;
import com.mytaxi.entity.GeoCoordinate;

public class DriverMapper
{
    public static Driver toEntity(DriverDTO driverDTO)
    {
        return new Driver(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO toDto(Object[] object)
    {
        Driver driver = (Driver) object[0];
        Car car = (Car) object[1];
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setId(car.getId())
                .setRating(car.getRating())
                .setSeatCount(car.getSeatCount())
                .setEngineType(car.getEngineType())
                .setConvertible(car.getConvertible())
                .setLicensePlate(car.getLicensePlate())
                .setManufacturer(car.getManufacturer().getName());

        DriverDTO carDriverDTO = toDto(driver);
        carDriverDTO.setCarDTO(carDTOBuilder.createCarDTO());
        return carDriverDTO;
    }


    public static DriverDTO toDto(Driver driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
            DriverDTO
                .newBuilder()
                .setId(driverDO.getId())
                .setPassword(driverDO.getPassword())
                .setStatus(driverDO.getOnlineStatus().name())
                .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> toDtos(Collection<Driver> drivers)
    {
        return drivers
            .stream()
            .map(DriverMapper::toDto)
            .collect(Collectors.toList());
    }


    public static DriverDTO convertParamsToDto(Map<String, String> searchParams)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
            DriverDTO
                .newBuilder()
                .setUsername(MapUtils.getString(searchParams, "username"))
                .setStatus(MapUtils.getString(searchParams, "status"));

        return driverDTOBuilder.createDriverDTO();
    }
}
