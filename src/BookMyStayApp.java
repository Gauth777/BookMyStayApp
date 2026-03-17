import java.util.*;
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

 class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        if (!servicesByReservation.containsKey(reservationId)) {
            servicesByReservation.put(reservationId, new ArrayList<>());
        }
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }
        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        AddOnService s1 = new AddOnService("Breakfast", 500);
        AddOnService s2 = new AddOnService("Spa", 700);
        AddOnService s3 = new AddOnService("Airport Pickup", 300);

        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);
        manager.addService(reservationId, s3);

        double total = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + total);
    }
}