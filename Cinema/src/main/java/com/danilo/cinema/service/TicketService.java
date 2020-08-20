package com.danilo.cinema.service;

import com.danilo.cinema.dto.TicketDTO;
import com.danilo.cinema.model.Ticket;
import com.danilo.cinema.model.User;
import com.danilo.cinema.repository.TicketRepository;
import com.danilo.cinema.repository.UserRepository;
import com.danilo.cinema.util.TicketStatus;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    DozerBeanMapper mapper = new DozerBeanMapper();

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    public List<TicketDTO> getUserTickets(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        List<Ticket> tickets = ticketRepository.findAllByCustomer(user);
        List<TicketDTO> ticketDTOs = new ArrayList<>();

        for (Ticket ticket: tickets) {
            ticketDTOs.add(mapper.map(ticket, TicketDTO.class));
        }

        return ticketDTOs;
    }

    public List<TicketDTO> addTicket(TicketDTO request) {

        Ticket ticket = mapper.map(request, Ticket.class);

        List<Ticket> tickets = ticketRepository.findAllByProjectionAndHall(ticket.getProjection(), ticket.getHall());

        if (tickets.size() + ticket.getNumberOfSeats() > ticket.getHall().getCapacity()){
            return null;
        }

        ticket.setReservationDate(new Date());
        ticket.setStatus(TicketStatus.RESERVED);
        ticketRepository.save(ticket);

        return getUserTicketsInCinema(request.getCustomer().getId(), request.getProjection().getCinema().getId());
    }

    public List<TicketDTO> getUserTicketsInCinema(Long userId, Long cinemaId) {

        List<Ticket> tickets = ticketRepository.findAllTicketsInCinema(userId, cinemaId);

        List<TicketDTO> ticketDTOs = new ArrayList<>();

        for (Ticket ticket: tickets) {
            ticketDTOs.add(mapper.map(ticket, TicketDTO.class));
        }

        return ticketDTOs;
    }

    public List<TicketDTO> paiTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket == null){
            return null;
        }

        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.save(ticket);

        return getUserTicketsInCinema(ticket.getCustomer().getId(), ticket.getProjection().getCinema().getId());
    }

    public List<TicketDTO> cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket == null){
            return null;
        }

        ticket.setStatus(TicketStatus.CANCELED);
        ticketRepository.save(ticket);

        return getUserTicketsInCinema(ticket.getCustomer().getId(), ticket.getProjection().getCinema().getId());
    }
}
