package MENU;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MenuOrdering extends JPanel {
    private ArrayList<MenuItem> menuItems;
    private ArrayList<MenuItem> cartItems;

    private JPanel foodPanel; // Add the foodPanel here
    private JTextArea cartTextArea;
    private JButton checkoutButton;
    private OrderHistory orderHistoryPanel;

    public MenuOrdering(OrderHistory orderHistoryPanel) {

        this.orderHistoryPanel = orderHistoryPanel;

        // Initialize menuItems and cartItems lists
        menuItems = new ArrayList<>();
        cartItems = new ArrayList<>();

        // Initialize menu items by reading from the Reader class
        Reader reader = new Reader("/MENU/menus.txt", 3);
        menuItems = reader.getMenuItems();

        // Set the layout of the main panel
        setLayout(new BorderLayout());

        // Initialize the foodPanel for displaying menu items
        foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        JScrollPane foodScrollPane = new JScrollPane(foodPanel);
        foodScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create a panel to hold the cart details and checkout button
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Initialize the cart text area and scroll pane
        cartTextArea = new JTextArea(15, 30);
        cartTextArea.setEditable(false);
        JScrollPane cartScrollPane = new JScrollPane(cartTextArea);

        // Create a panel for the checkout button
        JPanel buttonPanel = new JPanel();
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(187,227, 231));
        checkoutButton.setOpaque(true);
        checkoutButton.setBorderPainted(false);
        checkoutButton.addActionListener(e -> performCheckout());
        buttonPanel.add(checkoutButton);

        // Create a panel for the cart details and checkout button
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the cart details and checkout button panel to the content panel
        contentPanel.add(foodScrollPane, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);

        // Add the content panel to the main panel
        add(contentPanel, BorderLayout.CENTER);

        // Populate the foodPanel with menu items
        populateFoodPanel();

        // Set the panel as visible
        setVisible(true);
    }

    public void populateFoodPanel() {
        for (MenuItem menuItem : menuItems) {
            // Create a panel to display details and buttons for each menu item
            JPanel foodItemPanel = new JPanel(new BorderLayout());
            foodItemPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
            foodItemPanel.setBackground(new Color(240, 240, 240));
            foodItemPanel.setPreferredSize(new Dimension(350, 100));
            foodItemPanel.setMaximumSize(new Dimension(350, 100));
            foodItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Create a panel to display name, description, and price of the menu item
            JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
            detailsPanel.setBackground(new Color(240, 240, 240));

            // Create labels for name, description, and price
            JLabel nameLabel = new JLabel(menuItem.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel descriptionLabel = new JLabel(menuItem.getDescription());
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel priceLabel = new JLabel("$" + String.format("%.02f", menuItem.getPrice()));
            priceLabel.setFont(new Font("Arial", Font.BOLD, 14));

            // Add labels to the details panel
            detailsPanel.add(nameLabel);
            detailsPanel.add(descriptionLabel);
            detailsPanel.add(priceLabel);

            // Add the details panel to the food item panel
            foodItemPanel.add(detailsPanel, BorderLayout.CENTER);

            // Create a panel for buttons (Add and Delete)
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonsPanel.setBackground(new Color(187,227, 231));

            // Create a text field for quantity input
            JTextField quantityTextField = new JTextField(5);
            buttonsPanel.add(new JLabel("Quantity:"));
            buttonsPanel.add(quantityTextField);

            // Create Add button and its action listener
            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
                try {
                    int selectedQuantity = Integer.parseInt(quantityTextField.getText());
                    addToCart(menuItem, selectedQuantity);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Create Delete button and its action listener
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(e -> {
                try {
                    int selectedQuantity = Integer.parseInt(quantityTextField.getText());
                    removeFromCart(menuItem, selectedQuantity);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Add buttons to the buttons panel
            buttonsPanel.add(addButton);
            buttonsPanel.add(deleteButton);

            // Add the buttons panel to the food item panel
            foodItemPanel.add(buttonsPanel, BorderLayout.SOUTH);

            // Add the food item panel to the foodPanel
            foodPanel.add(foodItemPanel);
        }
    }

    public void addToCart(MenuItem menuItem, int quantity) {
        if (quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                cartItems.add(menuItem);
            }
            updateCartTextArea();
        }
    }

    public void removeFromCart(MenuItem menuItem, int quantity) {
        if (quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                cartItems.remove(menuItem);
            }
            updateCartTextArea();
        }
    }

    public void updateCartTextArea() {
        cartTextArea.setText("");
        for (MenuItem item : cartItems) {
            cartTextArea.append(item.getName() + "\t$" + item.getPrice() + "\n");
        }
    }

    public void performCheckout() {
        double totalAmount = calculateTotalAmount();
        if (totalAmount > 0) {

            // Display an option dialog for pickup or delivery
            Object[] options = {"Pick Up", "Delivery"};
            int choice = JOptionPane.showOptionDialog(this, "Choose an option:", "Order Type",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            String info = "";
            String message = "";
            if (choice == JOptionPane.YES_OPTION) {   //pickUp
                String phoneNumber = JOptionPane.showInputDialog(this, "Enter your phone number:");
                String name = JOptionPane.showInputDialog(this, "Enter your name:");
                while(phoneNumber.equals(null) || name.equals(null)){
                    JOptionPane.showMessageDialog(this, "Pls put down ur info", "Invalid input", JOptionPane.WARNING_MESSAGE);
                    phoneNumber = JOptionPane.showInputDialog(this, "Enter your phone number:");
                    name = JOptionPane.showInputDialog(this, "Enter your name:");
                }
                while (!phoneNumber.startsWith("04") || (phoneNumber.length() != 10) || !isNumber(phoneNumber) ) {
                    JOptionPane.showMessageDialog(this, "Your number is invalid", "Invalid number", JOptionPane.WARNING_MESSAGE);
                    phoneNumber = JOptionPane.showInputDialog(this, "Enter your phone number:");
                    name = JOptionPane.showInputDialog(this, "Enter your name:");
                }
                message = "Total Amount: $" + totalAmount + "\nThank you for your order!";
                info = "pick up by " + name + phoneNumber;
            } else if (choice == JOptionPane.NO_OPTION) {  //deliver
                String deliveryAddress = JOptionPane.showInputDialog(this, "Enter your delivery address:");
                while (deliveryAddress.equals(null)){
                    JOptionPane.showMessageDialog(this, "Pls put down ur info", "Invalid input", JOptionPane.WARNING_MESSAGE);
                    deliveryAddress = JOptionPane.showInputDialog(this, "Enter your delivery address:");
                }
                totalAmount += 8.0;
                message = "Total Amount: $" + totalAmount + "\n(included $8 delivery fee)\nThank you for your order!";
                info = "deliver to: " + deliveryAddress;
            }




            //create order:
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            Map<String, Integer> itemCounts = new HashMap<>();
            for (MenuItem item : cartItems) {
                String itemType = item.getName().split(" - ")[0]; // Extract item type
                itemCounts.put(itemType, itemCounts.getOrDefault(itemType, 0) + 1);
            }

            // Format the result
            ArrayList<String> foodItem = new ArrayList<String>();
            ArrayList<Integer> amount = new ArrayList<Integer>();
            for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
                String itemType = entry.getKey();
                int count = entry.getValue();
                foodItem.add(itemType);
                amount.add(count);
            }

            String orderNo = generateOrderNo();
            try {
                File f = new File("src/main/resources/history.txt");
                // Create a FileWriter with append mode (true)
                FileWriter fw = new FileWriter(f, true);
                PrintWriter w = new PrintWriter(fw);
                for (int j = 0; j < foodItem.size(); j++) {
                    w.println(orderNo + "|" + formattedDateTime + "|" + foodItem.get(j) + "|" + amount.get(j) + "|" + String.format("%.2f", totalAmount) + "|" +info);
                };
                w.close();
                fw.close(); // Close the FileWriter
                this.orderHistoryPanel.updateTable();
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }

            message += "Your order number is: " + orderNo;

            JOptionPane.showMessageDialog(this, message, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
            clearCart();
            updateCartTextArea();
        } else {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Empty Cart", JOptionPane.WARNING_MESSAGE);
        }
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (MenuItem item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public String generateOrderNo() {
        // Define the characters and digits you want to include in the random string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#%&*@";

        // Initialize the random number generator
        Random random = new Random();

        // Create a StringBuilder to build the random string
        StringBuilder stringBuilder = new StringBuilder();

        // Generate 8 random characters/digits and append them to the StringBuilder
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        // Get the final random string
        String randomString = stringBuilder.toString();

       return randomString;
    }


    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
