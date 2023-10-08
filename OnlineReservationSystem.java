import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservationSystem {
    private static Map<String, String> loginCredentials = new HashMap<>();
    private static Map<String, Reservation> reservations = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Add some sample login credentials
        loginCredentials.put("user1", "password1");
        loginCredentials.put("user2", "password2");

        // Login Form
        System.out.println("Welcome to the Online Reservation System!");
        System.out.print("Enter your login id: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if login credentials are valid
        if (isValidLogin(loginId, password)) {
            System.out.println("Login successful!");

            // Reservation System
            System.out.println("Reservation Form");
            System.out.print("Enter your basic details: ");
            String basicDetails = scanner.nextLine();
            System.out.print("Enter train number: ");
            int trainNumber = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter class type: ");
            String classType = scanner.nextLine();
            System.out.print("Enter date of journey: ");
            String dateOfJourney = scanner.nextLine();
            System.out.print("Enter from (place): ");
            String fromPlace = scanner.nextLine();
            System.out.print("Enter destination: ");
            String destination = scanner.nextLine();

            // Save the reservation details in the database
            String pnrNumber = saveReservationDetails(basicDetails, trainNumber, classType, dateOfJourney, fromPlace, destination);

            // Cancellation Form
            System.out.println("Cancellation Form");
            System.out.print("Enter your PNR number: ");
            String pnrToCancel = scanner.nextLine();

            // Confirm cancellation
            confirmCancellation(pnrToCancel);

            // Display information related to the PNR number
            displayPNRDetails(pnrNumber);
        } else {
            System.out.println("Invalid login credentials. Please try again.");
        }

        scanner.close();
    }

    private static boolean isValidLogin(String loginId, String password) {
        // Check if the login credentials exist in the loginCredentials map
        return loginCredentials.containsKey(loginId) && loginCredentials.get(loginId).equals(password);
    }

    private static String saveReservationDetails(String basicDetails, int trainNumber, String classType, String dateOfJourney, String fromPlace, String destination) {
        // Generate a unique PNR number
        String pnrNumber = generatePNRNumber();

        // Create a new Reservation object
        Reservation reservation = new Reservation(basicDetails, trainNumber, classType, dateOfJourney, fromPlace, destination);

        // Save the reservation details in the reservations map
        reservations.put(pnrNumber, reservation);

        System.out.println("Reservation details saved successfully! Your PNR number is: " + pnrNumber);
        return pnrNumber;
    }

    private static void displayPNRDetails(String pnrNumber) {
        // Check if the PNR number exists in the reservations map
        if (reservations.containsKey(pnrNumber)) {
            Reservation reservation = reservations.get(pnrNumber);
            System.out.println("PNR details: " + reservation);
        } else {
            System.out.println("Invalid PNR number. Please try again.");
        }
    }

    private static void confirmCancellation(String pnrNumber) {
        // Check if the PNR number exists in the reservations map
        if (reservations.containsKey(pnrNumber)) {
            // Remove the reservation associated with the PNR number
            reservations.remove(pnrNumber);
            System.out.println("Cancellation confirmed successfully!");
        } else {
            System.out.println("Invalid PNR number. Cancellation not confirmed.");
        }
    }

    private static String generatePNRNumber() {
        // Generate a random PNR number using current timestamp
        long timestamp = System.currentTimeMillis();
        return "PNR" + timestamp;
    }

    private static class Reservation {
        private String basicDetails;
        private int trainNumber;
        private String classType;
        private String dateOfJourney;
        private String fromPlace;
        private String destination;

        public Reservation(String basicDetails, int trainNumber, String classType, String dateOfJourney, String fromPlace, String destination) {
            this.basicDetails = basicDetails;
            this.trainNumber = trainNumber;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.fromPlace = fromPlace;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "Basic Details: " + basicDetails + ", Train Number: " + trainNumber + ", Class Type: " + classType + ", Date of Journey: " + dateOfJourney + ", From: " + fromPlace + ", Destination: " + destination;
        }
    }
}
