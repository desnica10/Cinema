package com.danilo.cinema.controller;

import com.danilo.cinema.dto.TicketDTO;
import com.danilo.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserTickets(@PathVariable Long userId){

        List<TicketDTO> tickets = ticketService.getUserTickets(userId);

        if (tickets == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{userId}/cinema/{cinemaId}")
    public ResponseEntity<?> getUserTicketsInCinema(@PathVariable Long userId, @PathVariable Long cinemaId){

        List<TicketDTO> tickets = ticketService.getUserTicketsInCinema(userId, cinemaId);

        if (tickets == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addTicket(@RequestBody TicketDTO request){

        List<TicketDTO> tickets = ticketService.addTicket(request);

        if (tickets == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PutMapping("/pai/{ticketId}")
    public ResponseEntity<?> paiTickets(@PathVariable Long ticketId){

        List<TicketDTO> tickets = ticketService.paiTicket(ticketId);

        if (tickets == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<?> cancelTickets(@PathVariable Long ticketId){

        List<TicketDTO> tickets = ticketService.cancelTicket(ticketId);

        if (tickets == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
