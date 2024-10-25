package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    void save(SaveApplicationDTO saveApplication);

    boolean isApplicationBelongToUser(Long userId, Long applicationId);

    void delete(Long edgeId);

    void sendApplication(SaveApplicationDTO saveApplicationDTO, List<Venue> venueTitleList);

    void approve(Long edgeId);

    void decline(Long edgeId);

    List<ApplicationToGetVenue> findOrganisatorApplicationsByUserId(long userId);

    List<Edge> findAllForOwner(long userId);

    Page<Edge> findAllForOwnerPaginated(long userId, int page, int size);
}
