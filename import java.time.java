import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class UserRegistration {
    
    public static final double VIP_DISCOUNT_UNDER_18_BIRTHDAY = 25.0;
    public static final double VIP_DISCOUNT_UNDER_18 = 20.0;
    public static final double VIP_BASE_FEE = 100.0;

    
    private String fullName;
    private String emailAddress;
    private String dateOfBirth;
    private long cardNumber;
    private String cardProvider;
    private String cardExpiryDate;
    private double feeToCharge;
    private int cvv;
    private String userType;
    private boolean emailValid;
    private boolean minorAndBirthday;
    private boolean minor;
    private boolean ageValid;
    private boolean cardNumberValid;
    private boolean cardStillValid;
    private boolean validCVV;

   
    public void registration() {
        Scanner scanner = new Scanner(System.in);

        
        System.out.println("Welcome to the ERyder Registration.");
        System.out.println("Here are your two options:");
        System.out.println("1. Register as a Regular User");
        System.out.println("2. Register as a VIP User");
        System.out.print("Please enter your choice (1 or 2): ");
        String choice = scanner.nextLine().trim();

        
        if (choice.equals("1")) {
            userType = "Regular User";
        } else if (choice.equals("2")) {
            userType = "VIP User";
        } else {
            System.out.println("Invalid choice! Please enter 1 or 2. Restarting registration...");
            scanner.close();
            registration();
            return;
        }

        
        System.out.print("Enter your full name: ");
        fullName = scanner.nextLine().trim();

        
        System.out.print("Enter your email address: ");
        emailAddress = scanner.nextLine().trim();
        emailValid = analyseEmail(emailAddress);

        
        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        dateOfBirth = scanner.nextLine().trim();
        LocalDate dob;
        try {
            dob = LocalDate.parse(dateOfBirth);
        } catch (Exception e) {
            System.out.println("Invalid date format! Please use YYYY-MM-DD. Restarting registration...");
            scanner.close();
            registration();
            return;
        }
        ageValid = analyseAge(dob);

        
        System.out.print("Enter your card number (VISA, MasterCard, American Express only): ");
        try {
            cardNumber = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid card number! Must be numeric. Restarting registration...");
            scanner.close();
            registration();
            return;
        }
        cardNumberValid = analyseCardNumber(cardNumber);

        
        System.out.print("Enter your card expiry date (MM/YY): ");
        cardExpiryDate = scanner.nextLine().trim();
        cardStillValid = analyseCardExpiryDate(cardExpiryDate);

        
        System.out.print("Enter your card CVV: ");
        try {
            cvv = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid CVV! Must be numeric. Restarting registration...");
            scanner.close();
            registration();
            return;
        }
        validCVV = analyseCVV(cvv);

        
        finalCheckpoint();

        
        scanner.close();
    }

    private boolean analyseEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            System.out.println("Email is valid");
            return true;
        } else {
            System.out.println("Invalid email address. Going back to the start of the registration");
            registration();
            return false;
        }
    }

    private boolean analyseAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        
        boolean isBirthday = (dob.getMonthValue() == currentDate.getMonthValue()) 
                && (dob.getDayOfMonth() == currentDate.getDayOfMonth());

       
        if (age <= 12 || age > 120) {
            System.out.println("Looks like you are either too young or already dead. Sorry, you can't be our user. Have a nice day");
            System.exit(0);
            return false;
        }

        
        if ("VIP User".equals(userType)) {
            if (isBirthday && age <= 18 && age > 12) {
                System.out.println("Happy Birthday!");
                System.out.println("You get 25% discount on the VIP subscription fee for being born today and being under 18!");
                minorAndBirthday = true;
            } else if (!isBirthday && age <= 18 && age > 12) {
                System.out.println("You get 20% discount on the VIP subscription fee for being under 18!");
                minor = true;
            }
        }

        return true;
    }

    private boolean analyseCardNumber(long cardNumber) {
        String cardNumStr = String.valueOf(cardNumber);
        int firstTwoDigits = 0;
        int firstFourDigits = 0;

        if (cardNumStr.length() >= 2) {
            firstTwoDigits = Integer.parseInt(cardNumStr.substring(0, 2));
        }


        if (cardNumStr.length() >= 4) {
            firstFourDigits = Integer.parseInt(cardNumStr.substring(0, 4));
        }


        if ((cardNumStr.length() == 13 || cardNumStr.length() == 15) && cardNumStr.startsWith("4")) {
            cardProvider = "VISA";
            return true;
        }

        else if (cardNumStr.length() == 16 && 
                ((firstTwoDigits >= 51 && firstTwoDigits <= 55) || 
                (firstFourDigits >= 2221 && firstFourDigits <= 2720))) {
            cardProvider = "MasterCard";
            return true;
        }

        else if (cardNumStr.length() == 15 && 
                (cardNumStr.startsWith("34") || cardNumStr.startsWith("37"))) {
            cardProvider = "American Express";
            return true;
        }

        else {
            System.out.println("Sorry, but we accept only VISA, MasterCard, or American Express cards. Please try again with a valid card.");
            System.out.println("Going back to the start of the registration.");
            registration();
            return false;
        }
    }

 
    private boolean analyseCardExpiryDate(String expiryDate) {
   
        if (expiryDate.length() != 5 || expiryDate.charAt(2) != '/') {
            System.out.println("Invalid expiry date format! Use MM/YY. Restarting registration...");
            registration();
            return false;
        }

        int month;
        int year;
        try {
          

            year = Integer.parseInt(expiryDate.substring(3, 5)) + 2000;
        } catch (NumberFormatException e) {
            System.out.println("Invalid expiry date! Restarting registration...");
            registration();
            return false;
        }

        if (month < 1 || month > 12) {
            System.out.println("Invalid month in expiry date! Restarting registration...");
            registration();
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        
        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            System.out.println("The card is still valid");
            return true;
        } else {
            System.out.println("Sorry, your card has expired. Please use a different card.");
            System.out.println("Going back to the start of the registration process…");
            registration();
            return false;
        }
    }


    private boolean analyseCVV(int cvv) {
        String cvvStr = String.valueOf(cvv);


        if ((cardProvider.equals("American Express") && cvvStr.length() == 4) ||
            ((cardProvider.equals("VISA") || cardProvider.equals("MasterCard")) && cvvStr.length() == 3)) {
            System.out.println("Card CVV is valid.");
            return true;
        } else {
            System.out.println("Invalid CVV for the given card.");
            System.out.println("Going back to the start of the registration process.");
            registration();
            return false;
        }
    }


    private void finalCheckpoint() {
        if (emailValid && ageValid && cardNumberValid && cardStillValid && validCVV) {
            chargeFees();
        } else {
            System.out.println("Sorry, your registration was unsuccessful due to the following reason(s)");
            if (!emailValid) System.out.println("- Invalid email address");
            if (!ageValid) System.out.println("- Invalid age");
            if (!cardNumberValid) System.out.println("- Invalid card number");
            if (!cardStillValid) System.out.println("- Card has expired");
            if (!validCVV) System.out.println("- Invalid CVV");
            System.out.println("Going back to the start of the registration process.");
            registration();
        }
    }


    private void chargeFees() {

        if (minorAndBirthday) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18_BIRTHDAY / 100);
        } else if (minor) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18 / 100);
        } else {
            feeToCharge = VIP_BASE_FEE;
        }

     
        String cardNumberStr = String.valueOf(cardNumber);
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);


        System.out.printf("Thank you for your payment.%n");
        System.out.printf("A fee of %.2f has been charged to your card ending with %s%n", feeToCharge, lastFourDigits);
    }


    @Override
    public String toString() {
        String cardNumberStr = String.valueOf(cardNumber);
        // Censor all digits except last four
        String censoredPart = cardNumberStr.substring(0, cardNumberStr.length() - 4).replaceAll(".", "*");
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);
        String censoredNumber = censoredPart + lastFourDigits;

        return "UserRegistration{" +
                "fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", cardNumber='" + censoredNumber + '\'' +
                ", cardProvider='" + cardProvider + '\'' +
                ", cardExpiryDate='" + cardExpiryDate + '\'' +
                ", feeToCharge=" + feeToCharge +
                ", userType='" + userType + '\'' +
                ", emailValid=" + emailValid +
                ", ageValid=" + ageValid +
                ", cardStillValid=" + cardStillValid +
                ", validCVV=" + validCVV +
                '}';
    }


    public String getUserType() {
        return userType;
    }

    public double getFeeToCharge() {
        return feeToCharge;
    }
}