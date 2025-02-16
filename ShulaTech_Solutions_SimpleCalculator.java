import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// CalculatorApp class extending JFrame and implementing ActionListener for event handling
public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField inputField; // Text field to display input and result
    private String mathOperator; // Stores the selected mathematical operator
    private double numberOne, numberTwo, calcResult; // Variables to hold numbers and result
    private ArrayList<String> calcHistory; // List to store calculation history
    private JTextArea historyLog; // Text area to display history

    // Constructor to initialize the calculator GUI
    public CalculatorApp() {
        setTitle("Basic Calculator"); // Set window title
        setSize(400, 500); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLayout(new BorderLayout());

        // Create and add text field for displaying input/output
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 24));
        add(inputField, BorderLayout.NORTH);

        // Initialize history list and history display area
        calcHistory = new ArrayList<>();
        historyLog = new JTextArea(5, 20);
        historyLog.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyLog);
        add(historyScroll, BorderLayout.EAST);

        // Create a panel for buttons with grid layout
        JPanel buttonGrid = new JPanel();
        buttonGrid.setLayout(new GridLayout(4, 4));

        // Define button labels
        String[] buttonNames = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};

        // Create buttons, set font, add action listener, and add to panel
        for (String name : buttonNames) {
            JButton calcButton = new JButton(name);
            calcButton.setFont(new Font("Arial", Font.BOLD, 18));
            calcButton.addActionListener(this);
            buttonGrid.add(calcButton);
        }

        add(buttonGrid, BorderLayout.CENTER); // Add button panel to frame
        setVisible(true); // Make the window visible
    }

    // Handle button click events
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand(); // Get button text

        // If a number button is clicked, append to input field
        if (Character.isDigit(action.charAt(0))) {
            inputField.setText(inputField.getText() + action);
        } 
        // If 'C' is clicked, clear input field
        else if (action.equals("C")) {
            inputField.setText("");
        } 
        // If '=' is clicked, perform calculation
        else if (action.equals("=")) {
            try {
                numberTwo = Double.parseDouble(inputField.getText()); // Get second number
                
                // Perform calculation based on selected operator
                switch (mathOperator) {
                    case "+": calcResult = numberOne + numberTwo; break;
                    case "-": calcResult = numberOne - numberTwo; break;
                    case "*": calcResult = numberOne * numberTwo; break;
                    case "/": calcResult = numberOne / numberTwo; break;
                }
                
                // Store calculation in history
                String historyEntry = numberOne + " " + mathOperator + " " + numberTwo + " = " + calcResult;
                calcHistory.add(historyEntry);
                updateHistory(); // Update history log
                inputField.setText(String.valueOf(calcResult)); // Display result
            } catch (Exception e) {
                inputField.setText("Error"); // Handle invalid input
            }
        } 
        // If an operator is clicked, store first number and operator
        else {
            numberOne = Double.parseDouble(inputField.getText());
            mathOperator = action;
            inputField.setText("");
        }
    }

    // Method to update the history display
    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        for (String entry : calcHistory) {
            sb.append(entry).append("\n");
        }
        historyLog.setText(sb.toString());
    }

    // Main method to run the application
    public static void main(String[] args) {
        new CalculatorApp();
    }
}
