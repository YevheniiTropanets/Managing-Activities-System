package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    List<City> findAll();
}
