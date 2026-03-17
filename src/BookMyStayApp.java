import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean allocateRoom(String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }
}

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid reservation ID");
            return;
        }

        String roomType = reservationRoomTypeMap.remove(reservationId);
        inventory.releaseRoom(roomType);
        releasedRoomIds.push(reservationId);
    }

    public void showRollbackHistory() {
        while (!releasedRoomIds.isEmpty()) {
            System.out.println("Cancelled Reservation ID: " + releasedRoomIds.pop());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String r1 = "R1";
        String r2 = "R2";

        if (inventory.allocateRoom("Single")) {
            service.registerBooking(r1, "Single");
        }

        if (inventory.allocateRoom("Double")) {
            service.registerBooking(r2, "Double");
        }

        service.cancelBooking(r1, inventory);
        service.cancelBooking(r2, inventory);

        System.out.println("Rollback History:");
        service.showRollbackHistory();
    }
}