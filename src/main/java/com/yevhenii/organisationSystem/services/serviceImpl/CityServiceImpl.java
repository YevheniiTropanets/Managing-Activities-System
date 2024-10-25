package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.repository.CityRepository;
import com.yevhenii.organisationSystem.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    CityRepository cityRepository;
    @Autowired
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @Override
    public List<City> findAll() {
        return  cityRepository.findAll();
    }
}
