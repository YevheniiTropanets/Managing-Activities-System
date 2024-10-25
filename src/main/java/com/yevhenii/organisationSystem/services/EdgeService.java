package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.Edge;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EdgeService {
    Optional<Edge> findById(Long id);

    List<Edge> findUserEdgesForDate(LocalDate filterDate, long userId);

    List<Edge> findEdgesByOrganisatorAndDate(LocalDate startDate, Long userId);

    List<Edge> findOrganisatorApplicationsByUserId(long userId);

    Page<Edge> findPage(int pageNumber);

    Page<Edge> findOrganisatorApplicationsByUserIdPaginated(long userId, int page, int size);
}
