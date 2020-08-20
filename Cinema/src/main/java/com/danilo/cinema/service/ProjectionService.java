package com.danilo.cinema.service;

import com.danilo.cinema.dto.ProjectionDTO;
import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.model.Projection;
import com.danilo.cinema.repository.CinemaRepository;
import com.danilo.cinema.repository.ProjectioRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectionService {

    DozerBeanMapper mapper = new DozerBeanMapper();

    @Autowired
    ProjectioRepository projectioRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    public List<ProjectionDTO> findAllCinemaProjections(Long cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        List<Projection> projections = projectioRepository.findAllByCinema(cinema);
        List<ProjectionDTO> projectionDTOs = new ArrayList<>();

        for (Projection projection : projections) {
            ProjectionDTO projectionDTO = mapper.map(projection, ProjectionDTO.class);
            projectionDTOs.add(projectionDTO);
        }

        return projectionDTOs;
    }

    public List<ProjectionDTO> addCinemaProjection(Long cinemaId, ProjectionDTO request) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        Projection projection = mapper.map(request, Projection.class);
        projection.setCinema(cinema);
        projectioRepository.save(projection);

        return findAllCinemaProjections(cinemaId);
    }

    public List<ProjectionDTO> editCinemaProjection(Long cinemaId, ProjectionDTO request) {
        Projection projection = projectioRepository.findOneById(request.getId());

        if (projection == null){
            return null;
        }

        projection = mapper.map(request, Projection.class);
        projectioRepository.save(projection);

        return findAllCinemaProjections(cinemaId);
    }

    public List<ProjectionDTO> deleteCinemaProjection(Long projectionId) {
        Projection projection = projectioRepository.findOneById(projectionId);

        if (projection == null){
            return null;
        }

        projectioRepository.delete(projection);

        return findAllCinemaProjections(projection.getCinema().getId());
    }
}
