import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

public class ProfilePage extends JFrame {
//    private UserController userController;
    private Customer currentCustomer;
    private String destinationInput;
    private String originInput;
    private int price;
    public ProfilePage() {
//        this.userController = user;

        setTitle("User Profile");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {

        // Header


        JPanel panel = new JPanel(new GridLayout(5,2));
        ImageIcon headerIcon = resizeImageIcon( new ImageIcon("headerImage.jpg"), 1280, 300); // Header image
        JLabel headerLabel = new JLabel(headerIcon);
        panel.add(headerLabel);
        // Footer

        //SHOULD BE CHANGED TO GETCUSTOMER() FUNCTIONALITY
        Address address = new Address("55th", "Calgary", "Alberta", "Canada", "90210");
        currentCustomer = new Customer("John Smith", address, "johnsmith00", "password123");;
        //GETCUSTOMER()^^

        JLabel nameLabel = (new JLabel("Hey, " + currentCustomer.getName()));
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 21)); // Set the font size to 16 (adjust as needed)
        JPanel memberButtonsPanel = new JPanel();

        panel.add(nameLabel);

        if ( currentCustomer.getMembership() != null) {
            panel.add(new JLabel("Membership ID: " + currentCustomer.getId()));
        } else {
            JButton startMembershipButton = new JButton("Start your free membership");
            JButton noMembershipButton = new JButton("No Thank you!");
            startMembershipButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayPolicy(panel, memberButtonsPanel);
                    panel.remove(memberButtonsPanel);
                }
            });
            memberButtonsPanel.add(startMembershipButton);
            memberButtonsPanel.add(noMembershipButton);
//            panel.add(startMembershipButton);
//            panel.add(noMembershipButton);
            panel.add(memberButtonsPanel);

            noMembershipButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.add(new JLabel("Membership: Not a member"));
                    panel.remove(memberButtonsPanel);
                    revalidate();
                    repaint();
                }
            });

        }

        panel.add(new JLabel("Address: " + currentCustomer.getAddress().toString()));
        JPanel flightPanel = new JPanel();

        JButton bookFlightButton = new JButton("Book a Flight!");
        //Plane plane = new Plane(); /* initialize Plane */

        ArrayList<FlightAttendant> flightAttendants = new ArrayList<>();
        Pilot pilot = new Pilot("Johnathan Smith", address, "johnsmith01", "password1234");;

        // Create a Crew
        Crew crew = new Crew(pilot, flightAttendants);// initialize Crew
        Address originAddress = new Address("9th St. NE", "Calgary", "Alberta", "Canada", "90210");
        Address destAddress = new Address("9th Ave SW", "Vancouver", "British Columbia", "Canada", "01209");


        // Create Airports
        Airport origin = new Airport(originAddress, "YYC"); /* initialize Airport */
        Airport destination = new Airport(destAddress, "YVR"); /* initialize Airport */

        // Set departure and arrival times
        LocalDateTime departureDateTime = LocalDateTime.now();
        LocalDateTime arrivalDateTime = departureDateTime.plusHours(2);

        // Create a Flight object
        price = 200;
//        Flight flight = new Flight(plane, crew, departureDateTime, arrivalDateTime, origin, destination, (float) price);
//
//        // Make calls to get methods
//        Plane flightPlane = flight.getPlane();
//        Crew flightCrew = flight.getCrew();
//        LocalDateTime flightDepartureDateTime = flight.getDepartureDateTime();
//        LocalDateTime flightArrivalDateTime = flight.getArrivalDateTime();
//        Airport flightOrigin = flight.getOrigin();
//        Airport flightDestination = flight.getDestination();
//        Map<String, Ticket> flightTickets = flight.getTickets();
//        boolean isSeatAvailable = flight.isSeatAvailable();/* provide seat location */
//        double flightNumber = flight.getFlightNumber();
//


        flightPanel.add(bookFlightButton);
        panel.add(flightPanel);
        JPanel browseFlightsPanel = new JPanel( new GridLayout(5,2));
        JPanel searchByPanel = new JPanel();
        JTextField destTextBox = new JTextField(20);
        searchByPanel.add(new JLabel("Enter a destination (City):"));
        searchByPanel.add(destTextBox);

        // Create and add the second text box
        JTextField originTextBox = new JTextField(20);
        searchByPanel.add(new JLabel("Enter the origin (City):"));
        searchByPanel.add(originTextBox);

        bookFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panel);
                JLabel nameLabel = (new JLabel("You can search for a flight by typing a destination or a origin or both!\n"));
                flightPanel.add(nameLabel);
                flightPanel.add(searchByPanel);
                flightPanel.remove(bookFlightButton);
                add(flightPanel);
                revalidate();
                repaint();
            }
        });
        // Create a button to trigger action (optional)
        JButton saveButton = new JButton("Search for a flight!");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserInputs(originTextBox, destTextBox);
                System.out.println("Origins (input):|" + originInput + "|onFile: |" + originAddress.getCity() + "|");
                System.out.println("destination (input):|" + destinationInput + "|onFile: |" + destAddress.getCity() + "|");

                if (originAddress.getCity().equals( originInput) && destAddress.getCity().equals( destinationInput)){
                    displayFlight(flightPanel);//flight Number should be passed as an argument with the panel
                    System.out.println("We are here!");
                } else if (originInput.equals(originAddress.getCity()) && destinationInput.equals("")) {
                    displayFlight(flightPanel);
                    System.out.println("We are here elseif!");
                } else if (destinationInput.equals(destAddress.getCity()) && originInput.equals("")) {
                    displayFlight(flightPanel);
                    System.out.println("We are here elseif2!");
                } else {
                    System.out.println("We are here else!");
                }
                revalidate();
                repaint();
            }
        });
        searchByPanel.add(saveButton);

        // Make the frame visible
        setVisible(true);

        add(panel);
    }

    private void displayFlight(JPanel currentPanel) {
        JTextArea flightDetails = new JTextArea("Flight number: AC575\nDeparting From: " + originInput + "\nDeparting at: " + LocalDateTime.now() + "Arriving to: " + destinationInput + "\nArriving at: " + LocalDateTime.now().plusHours(2) + "\nPrice: " + price);
        flightDetails.setEditable(false);

        JPanel TextPanel = new JPanel();
        JButton selectSeatsButton = new JButton("Select Seats for flight");
        JButton cancelButton = new JButton("Return to main page");
        selectSeatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.removeAll(); //remove the search panel to show the seats panel
                // Replace this with the logic of the Seat Map implementation in MainFrame
                JLabel nameLabel = (new JLabel("Here will be the implementation of the seating map and choosing a seat\n"));
                currentPanel.add(nameLabel);
                revalidate();
                repaint();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfilePage();
                dispose();

//                revalidate();
//                repaint();
            }
        });
        TextPanel.add(flightDetails);
        TextPanel.add(selectSeatsButton);
        TextPanel.add(cancelButton);
        currentPanel.add(TextPanel);


        revalidate();
        repaint();
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void saveUserInputs(JTextField origin, JTextField destination) {
        // Retrieve user inputs from text boxes and save them to variables
        this.originInput = origin.getText().trim();
        this.destinationInput = destination.getText().trim();
    }

        private void displayPolicy(JPanel panel, JPanel membersPanel) {

        JTextArea policyText = new JTextArea("       \t\tPOLICY          \n 1. Your membership is free and you will not be charged.\n 2. You agree to receiving emails from us that may be promotional.\n 3. A membership gives you benefits such as exclusive discounts.\n");
        policyText.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(policyText);
//
//        panel.add(scrollPane);

        JPanel enrollPanel = new JPanel();
        JButton enrollButton = new JButton("Enroll");
        JButton cancelButton = new JButton("Cancel");
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                panel.remove(policyText); // Clear the added components
//                panel.remove(enrollButton); // Clear the added buttons
//                panel.remove(cancelButton); // Clear the added buttons
                panel.remove(enrollPanel);
                // Replace this with your actual logic to generate and store the membership ID
                currentCustomer.setMembership(new Membership());
                //panel.add(new JLabel("Membership ID: " + currentCustomer.getMembership()));
                double idMember = Math.floor(Math.random()* 100000);
                panel.add(new JLabel("Membership ID: " + idMember));


                revalidate();
                repaint();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                panel.remove(policyText); // Clear the added components
//                panel.remove(enrollButton); // Clear the added buttons
//                panel.remove(cancelButton); // Clear the added buttons
                panel.remove(enrollPanel);
//                panel.add(membershipButton); //prompt user again
//                panel.add(noButton); //prompt user again
                panel.add(membersPanel);

                revalidate();
                repaint();
            }
        });
//        panel.add(enrollButton);
//        panel.add(cancelButton);
        enrollPanel.add(policyText);
        enrollPanel.add(enrollButton);
        enrollPanel.add(cancelButton);
        panel.add(enrollPanel);


        revalidate();
        repaint();
    }


}