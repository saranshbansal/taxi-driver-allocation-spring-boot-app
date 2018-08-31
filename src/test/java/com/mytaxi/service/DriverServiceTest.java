package com.mytaxi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mytaxi.DataManipulationTest;
import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.dao.DriverRepository;
import com.mytaxi.entity.Driver;
import com.mytaxi.exception.ConstraintsViolationException;

/**
 * @author saransh
 */
public class DriverServiceTest extends DataManipulationTest
{

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DriverService.class);
    }


    @Test
    public void testCreate() throws ConstraintsViolationException
    {
        Driver driver = getDriver();
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);
        driverService.create(driver);
        verify(driverRepository, times(1)).save(any(Driver.class));
    }


    @Test
    public void testFindByOnlineStatus()
    {
        List<Driver> drivers = Collections.singletonList(getDriver());
        when(driverRepository.findByOnlineStatus(any(OnlineStatus.class))).thenReturn(drivers);
        driverRepository.findByOnlineStatus(OnlineStatus.ONLINE);
        verify(driverRepository, times(1)).findByOnlineStatus(any(OnlineStatus.class));
    }

}
