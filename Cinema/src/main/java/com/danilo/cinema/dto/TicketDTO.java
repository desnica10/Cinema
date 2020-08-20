package com.danilo.cinema.dto;

import com.danilo.cinema.util.TicketStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TicketDTO {
    private long id;
    private TicketStatus status;
    private int numberOfSeats;
    private Date reservationDate;
    private HallDTO hall;
    private UserDTO customer;
    private ProjectionDTO projection;
}
