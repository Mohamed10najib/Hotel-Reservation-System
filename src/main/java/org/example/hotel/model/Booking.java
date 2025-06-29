package org.example.hotel.model;

import java.util.Date;

public class Booking {
    private Room room;
    private User user;
    private Date checkIn;
    private Date checkOut;
    private int totalPrice;

    public Booking(Room room, User user, Date checkIn, Date checkOut, int totalPrice) {
        this.room = room;
        this.user = user;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public Room getRoom() {
        return room;
    }



    public User getUser() {
        return user;
    }


    public Date getCheckIn() {
        return checkIn;
    }



    public Date getCheckOut() {
        return checkOut;
    }



    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Booking => Room: " + room +
                " | User: " + user +
                " | From: " + checkIn +
                " | To: " + checkOut +
                " | Total: " + totalPrice;
    }
}
