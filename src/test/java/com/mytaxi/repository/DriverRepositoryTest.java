package com.mytaxi.repository;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.dao.DriverRepository;
import com.mytaxi.entity.Driver;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * @author saransh
 */
public class DriverRepositoryTest extends AbstractRepositoryTest
{

    private static final String USER_NAME = "driver02";

    @Autowired
    private DriverRepository driverRepository;


    @Test
    public void testDriverById()
    {
        Driver driver = null;
        try
        {
            driver = driverRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        }
        catch (EntityNotFoundException e)
        {
            e.printStackTrace();
        }
        Assert.assertNotNull(driver);
    }


    @Test
    public void testDriverByOnlineStatus()
    {
        List<Driver> onlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.ONLINE);
        Assert.assertThat(onlineDrivers, hasSize(4));
    }


    @Test
    public void testDriverByOfflineStatus()
    {
        List<Driver> offlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.OFFLINE);
        Assert.assertThat(offlineDrivers, hasSize(4));
    }


    @Test
    public void testDriverByUsername()
    {
        Driver driver = driverRepository.findByUsername(USER_NAME);
        Assert.assertNotNull(driver);
    }
}
