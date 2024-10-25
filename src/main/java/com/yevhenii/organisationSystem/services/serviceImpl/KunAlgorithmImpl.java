package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.*;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import com.yevhenii.organisationSystem.payload.response.AlgorithmResponse;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.services.KunAlgorithmService;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class KunAlgorithmImpl implements KunAlgorithmService {
    EdgeRepository edgeRepository;
    Graph graph;
    ApplicationMapper applicationMapper;
    SecurityUtils securityUtils;
    VenueService venueService;

    @Autowired
    public KunAlgorithmImpl(VenueService venueService,SecurityUtils securityUtils, EdgeRepository edgeRepository, ApplicationMapper applicationMapper) {
        this.edgeRepository = edgeRepository;
        this.applicationMapper = applicationMapper;
        this.securityUtils = securityUtils;
        this.venueService = venueService;
    }

    @Override
    public List<Edge> getApplicationForVenues(Timestamp date, long userId) {
        return edgeRepository.getApplicationForVenues(date, userId);
    }

    @Override
    public List<Edge> findUserEdgesForDateAndStatus(LocalDate date, long userId,EApplicationStatus status) {
        return edgeRepository.findUserEdgesForDateAndStatus(date,userId,status);
    }

    @Override
    public List<Edge> getEdgesForVenues(LocalDate date, long userId) {
        return edgeRepository.getApplicationForVenuesForDay(date, userId);
    }

    @Override
    public List<Edge> getKuhnResultList(List<Edge> edgeList) {
        if(edgeList.isEmpty()) return new ArrayList<Edge>();
        else {
            graph = new Graph(edgeList);
            graph.maximumBipartiteMatching();
            graph.createResultEdgeList();
            graph.changeResultListStatus();
            saveMatching(graph.getResultMatchingEdgeList());
            graph.displayAdjMatrix();
            int[] resultMatching = graph.maximumBipartiteMatching();
            System.out.println(Arrays.toString(resultMatching));
            System.out.println(graph.getVenueList());
            System.out.println(graph.getApplicationList());
            List<ApplicationToGetVenue> notMatchingApplication = graph.getApplicationsNotInResultMatching();
            sendApplication(notMatchingApplication, graph);
            return graph.getResultMatchingEdgeList();
        }
    }


    public void sendApplication(List<ApplicationToGetVenue> applicationToGetVenueList, Graph graph) {
        for (ApplicationToGetVenue application : applicationToGetVenueList) {
            Long userId = securityUtils.getUserId();
            List<Edge> applicationEdgeList = graph.getApplicationEdgeList(application);
            List<String> cityNames = graph.getCityNameOnEdge(applicationEdgeList);
            ZonedDateTime zdt = application.getStartDate().toInstant().atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            for (String cityName : cityNames) {
                List<Venue> venueList = venueService.findFreeVenuesByDateCapacityCity(localDate, application.getActivity().getAmountSeats(), cityName);
                if (!venueList.isEmpty()) {
                    venueList = graph.removeSimilarVenue(venueList);
                    if (!venueList.isEmpty()) {
                        Edge edge = new Edge();
                        Random random = new Random();
                        edge.setVenue(venueList.get(random.nextInt(venueList.size())));
                        edge.setApplicationToGetVenue(application);
                        edge.setDate(application.getStartDate());
                        edge.setStatus(EApplicationStatus.EXPECT);
                        edgeRepository.save(edge);
                        //norificationService.sendNotification(message);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public Map<Venue, ApplicationToGetVenue> doKuhnAlgorithm(List<Edge> edgeList) {
        graph = new Graph(edgeList);
        graph.maximumBipartiteMatching();
        graph.createMap();
        graph.changeStatus();
        saveMatching(edgeList);
        graph.displayAdjMatrix();
        int[] resultMatching = graph.maximumBipartiteMatching();
        System.out.println(Arrays.toString(resultMatching));
        return graph.getResultMatching();
    }

    @Override
    public void saveMatching(List<Edge> edgeList) {
        for (Edge edge : edgeList) {
            if (edge.isMatching()) {
                edgeRepository.save(edge);
            }
        }
    }

    @Override
    public List<AlgorithmResponse> getMatchingEdges(long userId, Timestamp date) {
        List<Edge> edgeList = edgeRepository.getMatchingEdges(userId, date);
        List<AlgorithmResponse> responseList = new ArrayList<>();
        for (Edge edge : edgeList) {
            Venue venue = edge.getVenue();
            Activity activity = edge.getApplicationToGetVenue().getActivity();
            responseList.add(mapReponse(venue, activity));
        }
        return responseList;
    }

    private AlgorithmResponse mapReponse(Venue venue, Activity activity) {
        AlgorithmResponse algorithmResponse = new AlgorithmResponse();
        algorithmResponse.setVenueTitle(venue.getTitle());
        algorithmResponse.setActivityTitle(activity.getTitle());
        algorithmResponse.setGenre(activity.getGenre());
        algorithmResponse.setDescription(activity.getDescription());
        algorithmResponse.setOrganisation(activity.getOrganisation());
        algorithmResponse.setAmountSeats(activity.getAmountSeats());
        algorithmResponse.setActivityType(activity.getActivityType());
        return algorithmResponse;

    }

    @Override
    public List<AlgorithmResponse> getKuhnResult(Map<Venue, ApplicationToGetVenue> map) {
        List<AlgorithmResponse> responseList = new ArrayList<>();
        for (Map.Entry<Venue, ApplicationToGetVenue> entry : map.entrySet()) {
            Venue venue = entry.getKey();
            Activity activity = entry.getValue().getActivity();
            responseList.add(mapReponse(venue, activity));
        }
        return responseList;
    }


}
