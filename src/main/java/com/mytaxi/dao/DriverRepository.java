package com.mytaxi.dao;

import com.mytaxi.constants.OnlineStatus;
import com.mytaxi.entity.Driver;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<Driver, Long>
{
    Driver findByUsername(final String username);


    List<Driver> findByOnlineStatus(OnlineStatus onlineStatus);
}
