package com.mytaxi.dao;

import com.mytaxi.entity.Manufacturer;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long>
{

    Manufacturer findByName(final String name);
}
