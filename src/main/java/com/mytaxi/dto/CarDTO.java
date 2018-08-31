package com.mytaxi.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "rating can not be null!")
    private Float rating;

    @NotNull(message = "engineType can not be null!")
    private String engineType;

    @NotNull(message = "seatCount can not be null!")
    private Integer seatCount;

    @NotNull(message = "convertible can not be null!")
    private Boolean convertible;

    @NotNull(message = "licensePlate can not be null!")
    private String licensePlate;

    @NotNull(message = "manufacturer can not be null!")
    private String manufacturer;


    public CarDTO()
    {

    }


    public CarDTO(
        Long id, Float rating, String engineType, Integer seatCount, Boolean convertible, String licensePlate, String manufacturer)
    {
        this.id = id;
        this.rating = rating;
        this.engineType = engineType;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public Float getRating()
    {
        return rating;
    }


    public String getEngineType()
    {
        return engineType;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private Float rating;
        private String engineType;
        private Integer seatCount;
        private Boolean convertible;
        private String licensePlate;
        private String manufacturer;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setRating(Float rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(String engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, rating, engineType, seatCount, convertible, licensePlate, manufacturer);
        }

    }

}
