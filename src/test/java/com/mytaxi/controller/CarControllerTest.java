package com.mytaxi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.DataManipulationTest;
import com.mytaxi.dto.CarDTO;
import com.mytaxi.entity.Car;
import com.mytaxi.service.CarService;

/**
 * Created by vinodjagwani on 7/15/17.
 */

public class CarControllerTest extends DataManipulationTest
{

    private static final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;


    @BeforeClass
    public static void setup()
    {
        MockitoAnnotations.initMocks(CarController.class);
    }


    @Before
    public void init()
    {
        mvc = MockMvcBuilders.standaloneSetup(carController).dispatchOptions(true).build();
    }


    @Test
    public void testGetCar() throws Exception
    {
        CarDTO carData = getCarDTO();

        doReturn(carData).when(carService).find(any(Long.class));

        carController.getCar(1L);

        MvcResult result =
            mvc
                .perform(get("/v1/cars/{carId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        final String responseBody = result.getResponse().getContentAsString();

        Assert.assertTrue(responseBody.contains("A01"));
    }


    @Test
    public void getAllCars() throws Exception
    {
        List<CarDTO> cars = Collections.singletonList(getCarDTO());
        doReturn(cars).when(carService).findAllCars();
        carController.getAllCars();
        MvcResult result =
            mvc
                .perform(get("/v1/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("A01"));
    }


    @Test
    public void createCar() throws Exception
    {
        CarDTO carData = getCarDTO();
        String jsonInString = mapper.writeValueAsString(carData);
        doReturn(carData).when(carService).create(any(Car.class));
        carController.createCar(carData);
        MvcResult result =
            mvc
                .perform(
                    post("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonInString))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("11.0"));
    }


    @Test
    public void updateCar() throws Exception
    {
        CarDTO carData = getCarDTO();

        String jsonInString = mapper.writeValueAsString(carData);

        doNothing().when(carService).update(any(Car.class));

        carController.updateCar(carData);

        MvcResult result =
            mvc
                .perform(
                    put("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonInString))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void deleteCar() throws Exception
    {
        doNothing().when(carService).delete(any(Long.class));

        carController.deleteCar(1L);

        MvcResult result =
            mvc
                .perform(delete("/v1/cars/{carId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}
