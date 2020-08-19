package com.danilo.cinema.service;

import com.danilo.cinema.dto.CinemaDTO;
import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.repository.CinemaRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CinemaService {

    DozerBeanMapper mapper = new DozerBeanMapper();

    @Autowired
    CinemaRepository cinemaRepository;

    public List<CinemaDTO> findAllCinemas() {

        List<Cinema> cinemas = cinemaRepository.findAll();

        List<CinemaDTO> cinemaDTOs = new ArrayList<>();
        for (Cinema cinema: cinemas) {
            cinemaDTOs.add(mapper.map(cinema, CinemaDTO.class));
        }

        return cinemaDTOs;
    }

    public CinemaDTO findCinemaById(Long cinemaId) {

        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        return mapper.map(cinema, CinemaDTO.class);
    }

    public List<CinemaDTO> deleteCinema(Long cinemaId) {

        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        cinemaRepository.delete(cinema);

        return findAllCinemas();
    }

    public List<CinemaDTO> addCinema(CinemaDTO request) {
        if (cinemaRepository.findByName(request.getName()) != null){
            return null;
        }

        Cinema cinema = mapper.map(request, Cinema.class);

        cinemaRepository.save(cinema);

        return findAllCinemas();
    }

    public List<CinemaDTO> editCinema(CinemaDTO request) {
        Cinema cinema = cinemaRepository.findById(request.getId()).orElse(null);

        if (cinema == null){
            return null;
        }

        if (cinemaRepository.findByName(request.getName()) != null && !cinema.getName().equals(request.getName())){
            return null;
        }

        cinema = mapper.map(request, Cinema.class);
        cinemaRepository.save(cinema);

        return findAllCinemas();
    }
}
