package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Users> users = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();
    private int nextBookingId = 1;

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        // find room
        Room found = null;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                found = r;
                break;
            }
        }
        if (found == null) {
            rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
        } else {
            // requirement: setRoom should not impact previous bookings.
            // So we replace the room entry with a new Room instance, but bookings keep snapshots.
            rooms.remove(found);
            rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
        }
    }

    public void setUser(int userId, int balance) {
        Users found = null;
        for (Users u : users) {
            if (u.getId() == userId) {
                found = u;
                break;
            }
        }
        if (found == null) {
            users.add(new Users(userId, balance));
        } else {
            // update balance by replacing user
            users.remove(found);
            users.add(new Users(userId, balance));
        }
    }

    private Users findUser(int userId) {
        for (Users u : users) if (u.getId() == userId) return u;
        return null;
    }

    private Room findRoom(int roomNumber) {
        for (Room r : rooms) if (r.getRoomNumber() == roomNumber) return r;
        return null;
    }

    private Date truncate(Date d) {
        return new Date(d.getYear(), d.getMonth(), d.getDate());
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            if (checkIn == null || checkOut == null) throw new IllegalArgumentException("Dates required");
            Date ci = truncate(checkIn);
            Date co = truncate(checkOut);
            if (!ci.before(co)) throw new IllegalArgumentException("checkIn must be before checkOut");

            Users user = findUser(userId);
            if (user == null) throw new IllegalArgumentException("User not found");
            Room room = findRoom(roomNumber);
            if (room == null) throw new IllegalArgumentException("Room not found");

            long diff = co.getTime() - ci.getTime();
            int nights = (int)(diff / (1000L*60*60*24));
            if (nights <= 0) throw new IllegalArgumentException("Invalid nights");

            long totalPrice = (long)nights * room.getPricePerNight();
            if (user.getBalance() < totalPrice) {
                System.out.println("Booking failed: not enough balance for user " + userId);
                return;
            }

            // check availability: bookings overlapping
            for (Booking b : bookings) {
                if (b.getRoomNumber() == roomNumber) {
                    Date existingIn = b.getCheckIn();
                    Date existingOut = b.getCheckOut();
                    // overlap if ci < existingOut && existingIn < co
                    if (ci.before(existingOut) && existingIn.before(co)) {
                        System.out.println("Booking failed: room " + roomNumber + " is not available for the given period");
                        return;
                    }
                }
            }

            // perform booking
            Booking booking = new Booking(nextBookingId++, room, user, ci, co);
            bookings.add(booking);
            user.debit((int)totalPrice);
            System.out.println("Booking successful: " + booking);
        } catch (IllegalArgumentException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
    public void printAll() {
        // print rooms and bookings from latest created to oldest
        System.out.println("Rooms:");
        for (int i = rooms.size()-1; i>=0; i--) System.out.println(rooms.get(i));
        System.out.println("Bookings:");
        for (int i = bookings.size()-1; i>=0; i--) System.out.println(bookings.get(i));
    }

    public void printAllUsers() {
        System.out.println("Users:");
        for (int i = users.size()-1; i>=0; i--) System.out.println(users.get(i));
    }
}
