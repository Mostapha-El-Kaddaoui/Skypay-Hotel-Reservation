# Skypay-Hotel-Reservation
1. Should all functions be in the same service?

Not really. Putting everything in one service makes it do too many things at once: managing users, rooms, and bookings. This can make the code messy, hard to maintain, and harder to test.

A better approach is to split responsibilities:

RoomService → handles creating and updating rooms.

UserService → handles user creation and balance.

BookingService → handles bookings, checking availability, and payments.

This way, each class focuses on one thing, which makes the code cleaner and easier to work with.

2. setRoom shouldn’t impact old bookings – is there another way?

Right now, if you change a room’s price or type, old bookings still keep the original info because the booking saves the room details when it was booked.

Another approach:

Immutable rooms: instead of updating a room, create a new room object for the updated price or type. Old bookings keep the old room object.

Why this is good: It ensures historical bookings never change, which is important for tracking payments and for fairness.

In short: Always save the room details with the booking, or create a new room when changes happen. This way, the past is safe, and users won’t be affected by changes later.