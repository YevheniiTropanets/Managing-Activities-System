package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StreetRepository extends JpaRepository<Street,Long> {
    @Query("select s from Street s where s.streetName=:streetName and s.city.cityName =:cityName")
    public Optional<Street> findStreetByStreetNameAndCity(String streetName,String cityName);
    public Optional<Street> findById(Long id);
    @Query("select s from  Street  s")
    List<Street> findAll();
}
