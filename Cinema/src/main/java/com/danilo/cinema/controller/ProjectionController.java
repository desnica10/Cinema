package com.danilo.cinema.controller;

import com.danilo.cinema.dto.ProjectionDTO;
import com.danilo.cinema.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projection")
public class ProjectionController {

    @Autowired
    ProjectionService projectionService;

    @GetMapping("/{cinemaId}")
    public ResponseEntity<?> getAllCinemas(@PathVariable Long cinemaId){

        List<ProjectionDTO> projections = projectionService.findAllCinemaProjections(cinemaId);

        if (projections == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(projections, HttpStatus.OK);
    }

    @PostMapping("/{cinemaId}")
    public ResponseEntity<?> addCinemaProjection(@PathVariable Long cinemaId, @RequestBody ProjectionDTO request){

        List<ProjectionDTO> projections = projectionService.addCinemaProjection(cinemaId, request);

        if (projections == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(projections, HttpStatus.OK);
    }

    @PutMapping("/{cinemaId}")
    public ResponseEntity<?> editCinemaProjection(@PathVariable Long cinemaId, @RequestBody ProjectionDTO request){

        List<ProjectionDTO> projections = projectionService.editCinemaProjection(cinemaId, request);

        if (projections == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(projections, HttpStatus.OK);
    }

    @DeleteMapping("/{projectionId}")
    public ResponseEntity<?> deleteCinemaProjection(@PathVariable Long projectionId){

        List<ProjectionDTO> projections = projectionService.deleteCinemaProjection(projectionId);

        if (projections == null){
            return new ResponseEntity<>("Cinema with that id do not exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(projections, HttpStatus.OK);
    }
}
