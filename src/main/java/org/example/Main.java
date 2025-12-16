package org.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Service service = new Service();

        // Create rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // Create users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // User 1 books Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
        service.bookRoom(1, 2, sdf.parse("30/06/2026"), sdf.parse("07/07/2026"));

        // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026. (invalid)
        service.bookRoom(1, 2, sdf.parse("07/07/2026"), sdf.parse("30/06/2026"));

        // User 1 books Room 1 from 07/07/2026 to 08/07/2026 (1 night)
        service.bookRoom(1, 1, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));

        // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
        service.bookRoom(2, 1, sdf.parse("07/07/2026"), sdf.parse("09/07/2026"));

        // User 2 books Room 3 from 07/07/2026 to 08/07/2026 (1 night)
        service.bookRoom(2, 3, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));

        // setRoom(1, suite, 10000).
        service.setRoom(1, RoomType.SUITE, 10000);

        System.out.println("\n=== printAll ===");
        service.printAll();

        System.out.println("\n=== printAllUsers ===");
        service.printAllUsers();
    }
}
