import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculatorApp extends JFrame implements ActionListener {

    private JTextField inputField;
    private String mathOperator;
    private double numberOne, numberTwo, calcResult;
    private ArrayList<String> calcHistory;
    private JTextArea historyLog;

    public CalculatorApp() {

        // ===== MODERN LOOK AND FEEL =====
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        setTitle("Modern Calculator");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // ===== DISPLAY FIELD =====
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.BOLD, 30));
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField.setEditable(false);
        inputField.setBackground(Color.WHITE);

        add(inputField, BorderLayout.NORTH);

        // ===== HISTORY PANEL =====
        calcHistory = new ArrayList<>();

        historyLog = new JTextArea();
        historyLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        historyLog.setEditable(false);
        historyLog.setBackground(new Color(250, 250, 250));

        JScrollPane scrollPane = new JScrollPane(historyLog);
        scrollPane.setPreferredSize(new Dimension(150, 0));
        add(scrollPane, BorderLayout.EAST);

        // ===== BUTTON PANEL =====
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));

        String[] buttons = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "C","0","=","+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btn.setFocusPainted(false);
            btn.setBackground(getButtonColor(text));
            btn.setForeground(Color.BLACK);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===== BUTTON COLORS (MODERN STYLE) =====
    private Color getButtonColor(String text) {
        if (text.equals("=")) return new Color(76, 175, 80);
        if (text.equals("C")) return new Color(244, 67, 54);
        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/"))
            return new Color(33, 150, 243);
        return new Color(224, 224, 224);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (Character.isDigit(action.charAt(0))) {
            inputField.setText(inputField.getText() + action);
        }
        else if (action.equals("C")) {
            inputField.setText("");
            mathOperator = "";
        }
        else if (action.equals("=")) {
            try {
                numberTwo = Double.parseDouble(inputField.getText());

                switch (mathOperator) {
                    case "+": calcResult = numberOne + numberTwo; break;
                    case "-": calcResult = numberOne - numberTwo; break;
                    case "*": calcResult = numberOne * numberTwo; break;
                    case "/": calcResult = numberTwo != 0 ? numberOne / numberTwo : 0; break;
                }

                String entry = numberOne + " " + mathOperator + " " + numberTwo + " = " + calcResult;
                calcHistory.add(entry);
                updateHistory();

                inputField.setText(String.valueOf(calcResult));

            } catch (Exception ex) {
                inputField.setText("Error");
            }
        }
        else {
            try {
                numberOne = Double.parseDouble(inputField.getText());
                mathOperator = action;
                inputField.setText("");
            } catch (Exception ex) {
                inputField.setText("Error");
            }
        }
    }

    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        for (String s : calcHistory) {
            sb.append(s).append("\n");
        }
        historyLog.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorApp::new);
    }
}
