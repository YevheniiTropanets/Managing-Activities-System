package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationToGetVenue,Long> {
//    @Query("select a  from ApplicationToGetVenue a  where a.activity.user.id = :userId")
//    List<ApplicationToGetVenue> getApplicationByUserId(long userId);
    void deleteById(Long applicationId);
    @Query("select count(a) > 0 from ApplicationToGetVenue a where a.id = :applicationId and a.activity.user.id = :userId")
    boolean isApplicationBelongToUser(Long userId, Long applicationId);

    @Query("select a from ApplicationToGetVenue  a where a.activity.user.id = :userId")
    List<ApplicationToGetVenue> findOrganisatorApplicationsByUserId(long userId);
//    @Query( value = "SELECT e.* FROM applicationtogetvenue a\n" +
//            "JOIN Edge e on a.id = e.applvenueid\n" +
//            "JOIN Venue v on e.venueid  = v.id\n" +
//            "WHERE v.userid = :userId", nativeQuery = true)
//    List<Edge> findAllForOwner(long userId);
    @Query("select e from Edge e  where e.venue.user.id = :userId")
    List <Edge> findAllForOwner(long userId);
    @Query("select e from Edge e where e.venue.user.id = :userId")
    Page<Edge> findAllForOwnerPaginated(long userId, Pageable pageable);




}
