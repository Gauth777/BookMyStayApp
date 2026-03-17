import java.util.*;

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        inventory.decrement(roomType);

        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {
        int counter = 1;
        String roomId;

        do {
            roomId = roomType + "-" + counter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}

/**
 * Reservation class
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRooms(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        inventory.addRooms("Single", 2);
        inventory.addRooms("Double", 1);

        RoomAllocationService service = new RoomAllocationService();

        List<Reservation> reservations = Arrays.asList(
                new Reservation("Abhi", "Single"),
                new Reservation("Subha", "Single"),
                new Reservation("Rahul", "Double"),
                new Reservation("Neha", "Single") // should fail
        );

        for (Reservation r : reservations) {
            service.allocateRoom(r, inventory);
        }
    }
}