package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.Street;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreetService {
    List<Street> findAll();
}
