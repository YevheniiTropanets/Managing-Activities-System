package com.yevhenii.organisationSystem.repository;


import com.yevhenii.organisationSystem.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    void deleteById(Long venueId);
    @Query("select count(v) > 0 from Venue v where v.id = :venueId and v.user.id = :userId")
    boolean isVenueBelongToUser(Long userId, Long venueId);
    @Query("select v from Venue v where v.title =:title")
    Venue findByVenueTitle(String title);
    @Query("select v from Venue v where v.title =:title and v.user.id =:userId")
    Venue findByVenueTitleAndUserId(String title,Long userId);
    @Query("select v from  Venue v where v.user.id =:userId")
    List<Venue> findAllById(Long userId);
    Optional<Venue> findById(Long venueId);

    @Query(value = "SELECT v.* FROM Venue v LEFT JOIN PlannedActivities pa ON pa.venueId = v.id AND date(pa.startDate) <= now()   AND date(pa.endDate)  >= now()     WHERE pa.id IS NULL",nativeQuery = true)
    List<Venue> findAllFreeVenuesForCurrentDate();
    @Query(value = "SELECT v.* FROM venue v\n" +
            "            LEFT JOIN PlannedActivities pa\n" +
            "              ON pa.venueId = v.id\n" +
            "              AND DATE(pa.startDate) <= :date\n" +
            "              AND DATE(pa.endDate) >= :date\n" +
            "            WHERE pa.id IS NULL;",nativeQuery = true)
    List<Venue> findAllFreeVenuesForDate(LocalDate date);
    @Query(value = "SELECT v.* FROM venue v\n" +
            "                        LEFT JOIN PlannedActivities pa\n" +
            "                          ON pa.venueId = v.id\n" +
            "                          AND DATE(pa.startDate) <= :localDate\n" +
            "                          AND DATE(pa.endDate) >= :localDate\n" +
            "           LEFT JOIN Street s on v.streetid = s.id\n" +
            "           LEFT JOIN City c on s.cityid = c.id\n" +
            "                        WHERE pa.id IS NULL AND v.maximumseats >= :amountSeats AND c.cityName = :cityName",nativeQuery = true)
    List<Venue> findFreeVenuesByDateCapacityCity(LocalDate localDate, int amountSeats, String cityName);




}
