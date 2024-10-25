package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.Street;
import com.yevhenii.organisationSystem.repository.StreetRepository;
import com.yevhenii.organisationSystem.services.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetServiceImpl implements StreetService {
    StreetRepository streetRepository;
    @Autowired
    public StreetServiceImpl(StreetRepository streetRepository){
        this.streetRepository = streetRepository;
    }
    @Override
    public List<Street> findAll() {
        return streetRepository.findAll();
    }
}
