package org.example;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Booking {
    private final int bookingId;
    private final int roomNumber;
    private final RoomType roomTypeSnapshot;
    private final int roomPricePerNightSnapshot;
    private final int userIdSnapshot;
    private final int userBalanceSnapshot;
    private final Date checkIn;
    private final Date checkOut;

    public Booking(int bookingId, Room room, Users user, Date checkIn, Date checkOut) {
        this.bookingId = bookingId;
        this.roomNumber = room.getRoomNumber();
        this.roomTypeSnapshot = room.getType();
        this.roomPricePerNightSnapshot = room.getPricePerNight();
        this.userIdSnapshot = user.getId();
        this.userBalanceSnapshot = user.getBalance();
        this.checkIn = truncateTime(checkIn);
        this.checkOut = truncateTime(checkOut);
    }

    private Date truncateTime(Date d) {
        return new Date(d.getYear(), d.getMonth(), d.getDate());
    }

    public int getBookingId() { return bookingId; }
    public int getRoomNumber() { return roomNumber; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Booking{" +
                "bookingId=" + bookingId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomTypeSnapshot +
                ", pricePerNight=" + roomPricePerNightSnapshot +
                ", userId=" + userIdSnapshot +
                ", userBalanceAtBooking=" + userBalanceSnapshot +
                ", checkIn=" + sdf.format(checkIn) +
                ", checkOut=" + sdf.format(checkOut) +
                '}';
    }
}
