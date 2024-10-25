package com.yevhenii.organisationSystem.repository;


import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Long> {

    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId")
    List<Edge>  getApplicationForVenues(Timestamp date, long userId);

    @Query("select  e  from Edge e where DATE(e.date) = :date and e.venue.user.id =:userId")
    List<Edge>  getApplicationForVenuesForDay(LocalDate date, long userId);

    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId and e.isMatching = true")
    List<Edge>  getMatchingEdges(long userId,Timestamp date);

    @Query("select  e from Edge e where e.id =:id")
    Optional<Edge> findById(Long id);

    @Query("select e from Edge e where e.applicationToGetVenue.id = :applicationId")
    List<Edge> findByApplicationId(Long applicationId);
    @Transactional
    @Query("SELECT e FROM Edge e JOIN e.venue v WHERE v.user.id = :userId AND DATE(e.date) = :startDate")
    List<Edge> findUserEdgesForDate(
            @Param("startDate") LocalDate startDate,
            @Param("userId") Long userId
    );

    //@Query("SELECT e FROM Edge e JOIN e.venue v WHERE v.user.id = :userId AND DATE(e.date) = :startDate AND e.status =:status")
    @Query("SELECT e " +
            "FROM Edge e " +
            "JOIN e.venue v " +
            "LEFT JOIN PlannedActivities pa ON pa.venue.id = v.id AND DATE(pa.startDate) = :startDate " +
            "WHERE v.user.id = :userId " +
            "AND DATE(e.date) = :startDate " +
            "AND e.status = :status " +
            "AND pa.activity.id IS NULL AND pa.id IS NULL")
    List<Edge> findUserEdgesForDateAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("userId") Long userId,
            @Param("status") EApplicationStatus status
    );


    @Query("select e FROM Edge e where e.applicationToGetVenue.activity.user.id =:userId AND DATE(e.date) =:startDate ")
    List<Edge> findEdgesByOrganisatorAndDate(
            @Param("startDate") LocalDate startDate,
            @Param("userId") Long userId
    );





    @Query("select e from Edge e where e.applicationToGetVenue.activity.user.id = :userId")
    List<Edge> findOrganisatorApplicationsByUserId(long userId);

    @Query("select e from Edge e where e.applicationToGetVenue.activity.user.id = :userId")
    Page<Edge> findOrganisatorApplicationsByUserIdPaginated(long userId, Pageable pageable);

}
