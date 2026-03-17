import java.util.*;
import java.io.*;

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void setRoomCount(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.setRoomCount(parts[0], Integer.parseInt(parts[1]));
                    hasData = true;
                }
            }

            if (!hasData) {
                System.out.println("No valid inventory data found. Starting fresh.");
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        String filePath = "inventory.txt";

        service.loadInventory(inventory, filePath);

        if (inventory.getInventory().isEmpty()) {
            inventory.setRoomCount("Single", 5);
            inventory.setRoomCount("Double", 3);
            inventory.setRoomCount("Suite", 2);
        }

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        service.saveInventory(inventory, filePath);
    }
}