public class ERyder {
    
    public static final String COMPANY_NAME = "ERyder";
    public static final double BASE_FARE = 1.0;
    public static final double PER_MINUTE_FARE = 0.5;

    
    private final String LINKED_ACCOUNT;
    private final String LINKED_PHONE_NUMBER;

    
    private String bikeID;
    private int batteryLevel;
    private boolean isAvailable;
    private double kmDriven;
    private int totalUsageInMinutes;
    private double totalFare;

    
    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        
        this.bikeID = bikeID;
        this.batteryLevel = batteryLevel;
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
        
        
        this.LINKED_ACCOUNT = "default_user";
        this.LINKED_PHONE_NUMBER = "0000000000";
        
      
        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    
    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven,
                  String linkedAccount, String linkedPhoneNumber) {
        
        this.bikeID = bikeID;
        this.batteryLevel = batteryLevel;
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
        
       
        this.LINKED_ACCOUNT = linkedAccount;
        this.LINKED_PHONE_NUMBER = linkedPhoneNumber;
        
        
        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    
    public void printRideDetails(int usageInMinutes) {
        
        this.totalFare = calculateFare(usageInMinutes);
        this.totalUsageInMinutes = usageInMinutes;

        
        System.out.println("=== " + COMPANY_NAME + " Ride Details ===");
        System.out.println("Linked Account: " + LINKED_ACCOUNT);
        System.out.println("Linked Phone Number: " + LINKED_PHONE_NUMBER);
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Total Usage Time: " + totalUsageInMinutes + " minutes");
        System.out.println("Total Fare: $" + String.format("%.2f", totalFare));
        System.out.println("===============================\n");
    }

    
    private double calculateFare(int usageInMinutes) {
        
        return BASE_FARE + (PER_MINUTE_FARE * usageInMinutes);
    }

    
    public static void main(String[] args) {
        
        ERyder bike1 = new ERyder("EB001", 85, true, 12.5);
        
        bike1.printRideDetails(10);

        
        ERyder bike2 = new ERyder("EB002", 90, false, 8.7, "john_doe", "1234567890");
        
        bike2.printRideDetails(15);

        
    }
}

