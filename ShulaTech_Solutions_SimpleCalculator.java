import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CalculatorApp extends JFrame implements ActionListener {

    private JTextField display;
    private String operator = "";
    private double num1 = 0, num2 = 0;

    private boolean darkMode = true;

    private ArrayList<String> history = new ArrayList<>();
    private DefaultListModel<String> historyModel = new DefaultListModel<>();
    private JList<String> historyList = new JList<String>(historyModel);
    private JScrollPane historyPane;

    public CalculatorApp() {

        setTitle("Smart Calculator");
        setSize(380, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== DISPLAY =====
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 38));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        applyTheme();

        add(display, BorderLayout.NORTH);

        // ===== HISTORY PANEL =====
        historyList.setFont(new Font("Consolas", Font.PLAIN, 13));
        historyPane = new JScrollPane(historyList);
        historyPane.setPreferredSize(new Dimension(140, 0));
        add(historyPane, BorderLayout.EAST);

        // ===== BUTTON PANEL =====
        JPanel panel = new JPanel(new GridLayout(6, 4, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "AC", "DEL", "THEME", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", "%", ".", "="
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusPainted(false);
            btn.setBackground(getColor(text));
            btn.setForeground(Color.WHITE);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===== THEME =====
    private void applyTheme() {

        if (darkMode) {
            getContentPane().setBackground(new Color(20, 20, 20));
            display.setBackground(new Color(30, 30, 30));
            display.setForeground(Color.WHITE);
            historyList.setBackground(new Color(30, 30, 30));
            historyList.setForeground(Color.WHITE);
        } else {
            getContentPane().setBackground(new Color(240, 240, 240));
            display.setBackground(Color.WHITE);
            display.setForeground(Color.BLACK);
            historyList.setBackground(Color.WHITE);
            historyList.setForeground(Color.BLACK);
        }
    }

    // ===== BUTTON COLORS =====
    private Color getColor(String text) {

        if (text.equals("=")) return new Color(0, 200, 100);
        if (text.equals("AC")) return new Color(255, 59, 48);
        if (text.equals("DEL")) return new Color(255, 149, 0);
        if (text.equals("THEME")) return new Color(100, 100, 255);
        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("%"))
            return new Color(255, 140, 0);

        return new Color(60, 60, 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        try {

            // NUMBERS
            if (cmd.matches("[0-9]") || cmd.equals(".")) {
                if (display.getText().equals("0"))
                    display.setText(cmd);
                else
                    display.setText(display.getText() + cmd);
            }

            // CLEAR
            else if (cmd.equals("AC")) {
                display.setText("0");
                operator = "";
            }

            // DELETE
            else if (cmd.equals("DEL")) {
                String t = display.getText();
                display.setText(t.length() > 1 ? t.substring(0, t.length() - 1) : "0");
            }

            // THEME SWITCH
            else if (cmd.equals("THEME")) {
                darkMode = !darkMode;
                applyTheme();
            }

            // OPERATORS
            else if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/") || cmd.equals("%")) {
                num1 = Double.parseDouble(display.getText());
                operator = cmd;
                display.setText("0");
            }

            // RESULT
            else if (cmd.equals("=")) {

                num2 = Double.parseDouble(display.getText());

                double result = 0;

                if (operator.equals("+")) result = num1 + num2;
                else if (operator.equals("-")) result = num1 - num2;
                else if (operator.equals("*")) result = num1 * num2;
                else if (operator.equals("/")) result = (num2 != 0) ? num1 / num2 : 0;
                else if (operator.equals("%")) result = num1 % num2;
                else result = num2;

                String entry = num1 + " " + operator + " " + num2 + " = " + result;

                history.add(entry);
                historyModel.addElement(entry);

                display.setText(String.valueOf(result));
                operator = "";
            }

        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculatorApp();
            }
        });
    }
}
