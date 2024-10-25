package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    @Query("select  c from City c  where c.cityName =:name")
    public Optional<City> findCityByName(String name);
    @Query("select  c from City c where c.id =:id")
    Optional<City> findById(Long id);
    @Query("select c from City c")
    List<City> findAll();
}
