package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.*;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import com.yevhenii.organisationSystem.repository.*;
import com.yevhenii.organisationSystem.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    ActivityRepository activityRepository;
    ApplicationRepository applicationRepository;
    VenueRepository venueRepository;
    EdgeRepository edgeRepository;
    SecurityUtils securityUtils;
    ApplicationMapper applicationMapper;
    PlannedActivityRepository plannedActivityRepository;

    @Autowired
    public ApplicationServiceImpl( PlannedActivityRepository plannedActivityRepository,ApplicationMapper applicationMapper, ActivityRepository activityRepository, ApplicationRepository applicationRepository, VenueRepository venueRepository, EdgeRepository edgeRepository, SecurityUtils securityUtils) {
        this.activityRepository = activityRepository;
        this.applicationRepository = applicationRepository;
        this.venueRepository = venueRepository;
        this.edgeRepository = edgeRepository;
        this.securityUtils = securityUtils;
        this.applicationMapper = applicationMapper;
        this.plannedActivityRepository = plannedActivityRepository;
    }


    @Override
    public void save(SaveApplicationDTO saveApplicationDTO) {
        Long userId = securityUtils.getUserId();
        ApplicationToGetVenue application = new ApplicationToGetVenue();
        application.setStatus(EApplicationStatus.EXPECT);
        application.setEndDate(saveApplicationDTO.getFinishTime());
        application.setStartDate(saveApplicationDTO.getStartTime());
        Activity activity = activityRepository.findByActivityTitle(saveApplicationDTO.getActivityTitle(), userId);
        application.setActivity(activity);
        applicationRepository.save(application);
    }

    @Override
    public boolean isApplicationBelongToUser(Long userId, Long applicationId) {
        return applicationRepository.isApplicationBelongToUser(userId, applicationId);
    }

    @Override
    public void delete(Long edgeId) {
        Optional<Edge> edge = edgeRepository.findById(edgeId);
        ApplicationToGetVenue applicationToGetVenue = edge.get().getApplicationToGetVenue();
        Optional<PlannedActivities> plannedActivities = plannedActivityRepository.findByActivityIdAndDate(applicationToGetVenue.getActivity().getId(),edge.get().getDate());
        if(plannedActivities.isPresent()) plannedActivityRepository.delete(plannedActivities.get());
        edgeRepository.delete(edge.get());
    }

    @Override
    public void sendApplication(SaveApplicationDTO saveApplicationDTO, List<Venue> venueList) {
        Long userId = securityUtils.getUserId();
        ApplicationToGetVenue application = new ApplicationToGetVenue();
        application.setStatus(EApplicationStatus.EXPECT);
        application.setEndDate(saveApplicationDTO.getFinishTime());
        application.setStartDate(saveApplicationDTO.getStartTime());
        Activity activity = activityRepository.findByActivityTitle(saveApplicationDTO.getActivityTitle(), userId);
        application.setActivity(activity);
        applicationRepository.save(application);
        for (Venue venue : venueList) {
            Edge edge = new Edge();
            edge.setVenue(venue);
            edge.setApplicationToGetVenue(application);
            edge.setDate(application.getStartDate());
            edge.setStatus(EApplicationStatus.EXPECT);
            edgeRepository.save(edge);
        }
    }

    @Override
    public void approve(Long edgeId) {
        Optional<Edge> edgeOptional = edgeRepository.findById(edgeId);
        Edge edge1 = edgeOptional.get();
        edge1.setStatus(EApplicationStatus.APPROVED);
        ApplicationToGetVenue applicationToGetVenue = edge1.getApplicationToGetVenue();
        //applicationToGetVenue.setStatus(EApplicationStatus.APPROVED);
        PlannedActivities plannedActivities = new PlannedActivities();
        plannedActivities.setActivity(applicationToGetVenue.getActivity());
        plannedActivities.setVenue(edge1.getVenue());
        plannedActivities.setStatus("PLANED");
        plannedActivities.setStartDate(edge1.getDate());
        plannedActivities.setEndDate(applicationToGetVenue.getEndDate());
        edgeRepository.save(edge1);
        plannedActivityRepository.save(plannedActivities);
    }

    @Override
    public void decline(Long edgeId) {
        Optional<Edge> edgeOptional = edgeRepository.findById(edgeId);
        Edge edge1 = edgeOptional.get();
        ApplicationToGetVenue applicationToGetVenue = edge1.getApplicationToGetVenue();
        Optional<PlannedActivities> plannedActivities = plannedActivityRepository.findByActivityIdAndDate(applicationToGetVenue.getActivity().getId(),edge1.getDate());
        if(plannedActivities.isPresent()) plannedActivityRepository.delete(plannedActivities.get());
        edge1.setStatus(EApplicationStatus.DECLINED);
        edgeRepository.save(edge1);
    }

    @Override
    public List<ApplicationToGetVenue> findOrganisatorApplicationsByUserId(long userId) {
        return applicationRepository.findOrganisatorApplicationsByUserId(userId);
    }

    @Override
    public List<Edge> findAllForOwner(long userId) {
        return applicationRepository.findAllForOwner(userId);
    }

    @Override
    public Page<Edge> findAllForOwnerPaginated(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size);
        return applicationRepository.findAllForOwnerPaginated(userId,pageable);
    }


}
