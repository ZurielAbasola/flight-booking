package entity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import boundary.*;

public class Flight {
    private int flightNumber;
	private Plane plane;
	private Crew crew;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	private Airport origin;
	private Airport destination;
    private Map<String, Ticket> tickets;

	public Flight(Plane plane, Crew crew, LocalDateTime departureDateTime,
                  LocalDateTime arrivalDateTime, Airport origin, Airport destination, float basePrice) {
        this.flightNumber = (int) Math.random() * 1000000; // for now
        this.plane = plane;
        this.tickets = makeTickets(basePrice);
        this.crew = crew;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.origin = origin;
        this.destination = destination;
    }

    private Map<String, Ticket> makeTickets(float basePrice) {
        Map<String, Ticket> ticketMap = new HashMap<>();
        for (Map.Entry<String, Seat> entry : plane.getSeats().entrySet()) {
            Ticket ticket = new Ticket(this, plane.getSeat(entry.getKey()), basePrice);
            SQLConnector.getInstance().addTicket(ticket);
            ticketMap.put(entry.getKey(), ticket);
        }
        return ticketMap;
    }

    // getters and setters

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Map<String, Ticket> getTickets() {
        return tickets;
    }

    public Boolean isSeatAvailable(String location) {
        //return !tickets.get(seatKey).isSold();
        return true;
    }

    public void setFlightNum(int num){
        this.flightNumber = num;
    }

    public int getFlightNum(){
        return flightNumber;
    }
}