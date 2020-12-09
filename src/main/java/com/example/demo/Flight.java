package com.example.demo;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Flight {
    private Date departs;
    private List<Ticket> tickets;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    public Date getDeparts() { return departs; }

    @JsonProperty("Departs")
    public void setDeparts(Date dateTime) { this.departs = dateTime; }

    public List<Ticket> getTickets() { return tickets; }

    @JsonProperty("Tickets")
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    static class Ticket {
        private Person passenger;
        private int price;

        public Person getPassenger() { return passenger; }

        @JsonProperty("Passenger")
        public void setPassenger(Person passenger) { this.passenger = passenger; }

        public int getPrice() { return price; }

        @JsonProperty("Price")
        public void setPrice(int price) { this.price = price; }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        static class Person {
            private String firstName;
            private String lastName;

            public String getFirstName() { return firstName; }
            @JsonProperty("FirstName")
            public void setFirstName(String firstName) { this.firstName = firstName; }
            public String getLastName() { return lastName; }
            @JsonProperty("LastName")
            public void setLastName(String lastName) { this.lastName = lastName; }
        }
    }

}
