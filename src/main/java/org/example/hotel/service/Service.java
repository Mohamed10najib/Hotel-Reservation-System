package org.example.hotel.service;

import org.example.hotel.enums.RoomType;
import org.example.hotel.model.Booking;
import org.example.hotel.model.Room;
import org.example.hotel.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {

    private final List<Room> rooms = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setType(roomType);
                room.setPricePerNight(roomPricePerNight);
                return;
            }
        }
        rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
    }

    public void setUser(int userId, int balance) {
        for (User user : users) {
            if (user.getId() == userId) return;
        }
        users.add(new User(userId, balance));
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            System.out.println("❌ Invalid dates provided.");
            return;
        }

        if (!checkOut.after(checkIn)) {
            System.out.println("❌ Check-out must be after check-in.");
            return;
        }

        User user = findUserById(userId);
        Room room = findRoomByNumber(roomNumber);

        if (user == null || room == null) {
            System.out.println("❌ User or Room not found.");
            return;
        }

        if (isRoomBooked(roomNumber, checkIn, checkOut)) {
            System.out.println("❌ Room is already booked for the given period.");
            return;
        }

        int nights = (int) ((checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24));
        int totalCost = nights * room.getPricePerNight();

        if (user.getBalance() < totalCost) {
            System.out.println("❌ User does not have enough balance.");
            return;
        }

        user.deductBalance(totalCost);

        // Store snapshot of room at time of booking
        Room bookedRoomSnapshot = new Room(room.getRoomNumber(), room.getType(), room.getPricePerNight());

        Booking booking = new Booking(
                bookedRoomSnapshot,
                user,
                checkIn,
                checkOut,
                totalCost
        );

        bookings.add(booking);
        System.out.println("✅ Booking successful.");
    }

    public void printAll() {
        System.out.println("\n=== Rooms (Latest to Oldest) ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        System.out.println("\n=== Bookings (Latest to Oldest) ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
    }

    public void printAllUsers() {
        System.out.println("\n=== Users (Latest to Oldest) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    private User findUserById(int userId) {
        for (User u : users) {
            if (u.getId() == userId) return u;
        }
        return null;
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    private boolean isRoomBooked(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking b : bookings) {
            if (b.getRoom().getRoomNumber() == roomNumber &&
                    !(checkOut.before(b.getCheckIn()) || checkIn.after(b.getCheckOut()))) {
                return true;
            }
        }
        return false;
    }
}
