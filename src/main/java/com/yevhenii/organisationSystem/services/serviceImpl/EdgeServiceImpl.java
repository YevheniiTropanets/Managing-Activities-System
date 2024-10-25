package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.services.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EdgeServiceImpl implements EdgeService {

    EdgeRepository edgeRepository;

    @Autowired
    public EdgeServiceImpl(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    @Override
    public Optional<Edge> findById(Long id) {
        return edgeRepository.findById(id);
    }

    @Override
    public List<Edge> findUserEdgesForDate(LocalDate filterDate, long userId) {
        return edgeRepository.findUserEdgesForDate(filterDate, userId);
    }

    @Override
    public List<Edge> findEdgesByOrganisatorAndDate(LocalDate startDate, Long userId) {
        return edgeRepository.findEdgesByOrganisatorAndDate(startDate, userId);
    }

    @Override
    public List<Edge> findOrganisatorApplicationsByUserId(long userId){
        return edgeRepository.findOrganisatorApplicationsByUserId(userId);
    }

    @Override
    public Page<Edge> findPage(int pageNumber) {
        return edgeRepository.findAll(PageRequest.of(pageNumber - 1, 10));
    }

    @Override
    public Page<Edge> findOrganisatorApplicationsByUserIdPaginated(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size);
        return edgeRepository.findOrganisatorApplicationsByUserIdPaginated(userId,pageable);
    }

}
