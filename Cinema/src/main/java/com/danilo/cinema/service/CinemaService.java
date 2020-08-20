package com.danilo.cinema.service;

import com.danilo.cinema.dto.CinemaDTO;
import com.danilo.cinema.dto.HallDTO;
import com.danilo.cinema.model.Cinema;
import com.danilo.cinema.model.Hall;
import com.danilo.cinema.model.User;
import com.danilo.cinema.repository.CinemaRepository;
import com.danilo.cinema.repository.HallRepository;
import com.danilo.cinema.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    HallRepository hallRepository;

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

    public List<CinemaDTO> findManagerCinemas(Long managerId) {
        User manager = userRepository.findById(managerId).orElse(null);

        if (manager == null){
            return null;
        }

        List<Cinema> cinemas = cinemaRepository.findAllByManagers(manager);
        List<CinemaDTO> cinemaDTOs = new ArrayList<>();

        for (Cinema cinema : cinemas) {
            cinemaDTOs.add(mapper.map(cinema, CinemaDTO.class));
        }

        return cinemaDTOs;
    }

    public List<HallDTO> findCinemaHalls(Long cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        List<Hall> halls = hallRepository.findAllByCinema(cinema);
        List<HallDTO> hallsDTOs = new ArrayList<>();

        for (Hall hall : halls) {
            hallsDTOs.add(mapper.map(hall, HallDTO.class));
        }

        return hallsDTOs;
    }

    public List<HallDTO> deleteCinemaHall(Long cinemaId, Long hallId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        hallRepository.deleteById(hallId);

        return findCinemaHalls(cinemaId);
    }

    public List<HallDTO> addCinemaHall(Long cinemaId, HallDTO request) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        Hall hall = mapper.map(request, Hall.class);
        hall.setCinema(cinema);
        hallRepository.save(hall);

        return findCinemaHalls(cinemaId);
    }

    public List<HallDTO> editCinemaHalls(Long cinemaId, HallDTO request) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);

        if (cinema == null){
            return null;
        }

        Hall hall = hallRepository.findById(request.getId()).orElse(null);

        if (hall == null){
            return null;
        }

        if (hallRepository.findByName(request.getName()) != null && !hall.getName().equals(request.getName())){
            return null;
        }

        hall = mapper.map(request, Hall.class);
        hall.setCinema(cinema);
        hallRepository.save(hall);

        return findCinemaHalls(cinemaId);
    }
}
