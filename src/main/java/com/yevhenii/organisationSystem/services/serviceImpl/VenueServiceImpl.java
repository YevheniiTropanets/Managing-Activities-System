package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveVenueDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.Street;
import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.repository.CityRepository;
import com.yevhenii.organisationSystem.repository.StreetRepository;
import com.yevhenii.organisationSystem.repository.VenueRepository;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class VenueServiceImpl implements VenueService {
    VenueRepository venueRepository;
    ApplicationMapper applicationMapper;
    CityRepository cityRepository;
    StreetRepository streetRepository;
    SecurityUtils securityUtils;
    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository, ApplicationMapper applicationMapper, CityRepository cityRepository, StreetRepository streetRepository, SecurityUtils securityUtils) {
        this.venueRepository = venueRepository;
        this.applicationMapper = applicationMapper;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
        this.securityUtils = securityUtils;
    }




    @Override
    public void save(SaveVenueDTO saveVenueDTO) {

        Optional<City> cityOptional = cityRepository.findCityByName(saveVenueDTO.getCityName());
        City city = cityOptional.orElseGet(() -> {
            City newCity = new City(saveVenueDTO.getCityName());
            return cityRepository.save(newCity);
        });

        Optional<Street> streetOptional = streetRepository.findStreetByStreetNameAndCity(saveVenueDTO.getStreetName(), saveVenueDTO.getCityName());
        Street street = streetOptional.orElseGet(() -> {
            Street newStreet = new Street(saveVenueDTO.getStreetName());
            newStreet.setCity(city);
            return streetRepository.save(newStreet);
        });

        Venue venue = new Venue();
        Long userId = securityUtils.getUserId();
        venue.setUser(new User(userId));
        venue.setStreet(street);
        venue.setTitle(saveVenueDTO.getTitle());
        venue.setMaximumSeats(saveVenueDTO.getMaximumSeats());
        venue.setAdresIndex(saveVenueDTO.getAdresIndex());
        venue.setRentPrice(saveVenueDTO.getRentPrice());
        venue.setDescription(saveVenueDTO.getDescription());
        venueRepository.save(venue);
    }

    @Override
    public boolean isVenueBelongToUser(Long userId, Long venueId) {
        return venueRepository.isVenueBelongToUser(userId, venueId);
    }

    @Override
    public void delete(Long venueId) {
        venueRepository.deleteById(venueId);
    }

    @Override
    public List<VenueDTO> findAllById(Long userId) {

        return venueRepository
                .findAllById(userId)
                .stream()
                .map(applicationMapper::venueToDTO)
                .collect(toList());
    }

    @Override
    public Optional<VenueDTO> findById(Long venueId) {
        return venueRepository
                .findById(venueId)
                .map(applicationMapper::venueToDTO);
    }

    @Override
    public void update(SaveVenueDTO saveVenueDTO, Long venueId) {
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        Venue venue = optionalVenue.get();
        Optional<City> cityOptional = cityRepository.findCityByName(saveVenueDTO.getCityName());
        City city = cityOptional.orElseGet(() -> {
            City newCity = new City(saveVenueDTO.getCityName());
            return cityRepository.save(newCity);
        });

        Optional<Street> streetOptional = streetRepository.findStreetByStreetNameAndCity(saveVenueDTO.getStreetName(), saveVenueDTO.getCityName());
        Street street = streetOptional.orElseGet(() -> {
            Street newStreet = new Street(saveVenueDTO.getStreetName());
            newStreet.setCity(city);
            return streetRepository.save(newStreet);
        });
        Long userId = securityUtils.getUserId();
        venue.setDescription(saveVenueDTO.getDescription());
        venue.setUser(new User(userId));
        venue.setStreet(street);
        venue.setTitle(saveVenueDTO.getTitle());
        venue.setMaximumSeats(saveVenueDTO.getMaximumSeats());
        venue.setAdresIndex(saveVenueDTO.getAdresIndex());
        venue.setRentPrice(saveVenueDTO.getRentPrice());
        venue.setDescription(saveVenueDTO.getDescription());
        venueRepository.save(venue);
    }

    @Override
    public List<Venue> findAllFreeVenuesForCurrentDate() {
        return venueRepository.findAllFreeVenuesForCurrentDate();
    }

    @Override
    public List<Venue> findAllFreeVenuesForDate(LocalDate date) {
        return venueRepository.findAllFreeVenuesForDate(date);
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public List<Venue> findFreeVenuesByDateCapacityCity(LocalDate localDate, int amountSeats, String cityName) {
        return venueRepository.findFreeVenuesByDateCapacityCity(localDate, amountSeats, cityName);
    }




}
