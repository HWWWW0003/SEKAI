
public class ERyder {
  
    private String bikeID;
    private int batteryLevel;
    private boolean isAvailable;
    private double kmDriven;

    
    public ERyder() {
        this.bikeID = "0000";
        this.batteryLevel = 0;
        this.isAvailable = false;
        this.kmDriven = 0.0;
    }

    
    public ERyder(String bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        this.bikeID = bikeID;
        this.setBatteryLevel(batteryLevel); 
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
    }

    
    public void ride() {
        
        if (batteryLevel > 0 && isAvailable) {
            System.out.println("Bike is available");
        } else {
            System.out.println("Bike is not available");
        }
    }

    
    public void printBikeDetails() {
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Is Available: " + isAvailable);
        System.out.println("KM Driven: " + kmDriven + " km");
    }

    
    public int getBatteryLevel() {
        return batteryLevel;
    }

    
    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            this.batteryLevel = 0; 
        }
    }

    
    public String getBikeID() {
        return bikeID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public double getKmDriven() {
        return kmDriven;
    }
}


class Main {
    public static void main(String[] args) {
        
        ERyder bike1 = new ERyder();
        bike1.printBikeDetails();

        System.out.println("-------------------"); 
        
        ERyder bike2 = new ERyder("ER001", 80, true, 50.5);
        bike2.ride();
        bike2.printBikeDetails();
    }
}