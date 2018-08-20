package com.mytaxi.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.entity.GeoCoordinate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    @NotNull(message = "Online Status can not be null")
    private String status;

    private GeoCoordinate coordinate;

    private CarDTO carData;


    private DriverDTO()
    {}


    private DriverDTO(Long id, String username, String password, String status, GeoCoordinate coordinate, CarDTO carData)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.coordinate = coordinate;
        this.carData = carData;
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public CarDTO getCarDTO()
    {
        return carData;
    }


    public void setCarDTO(CarDTO carData)
    {
        this.carData = carData;
    }

    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private String status;
        private GeoCoordinate coordinate;
        private CarDTO carData;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setStatus(String status)
        {
            this.status = status;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public DriverDTOBuilder setCarDto(CarDTO carData)
        {
            this.carData = carData;
            return this;
        }


        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, status, coordinate, carData);
        }

    }
}
