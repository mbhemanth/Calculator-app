import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double num1, num2, result;
    private char operator;

    public Calculator() {
   
        setTitle("Java Calculator");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        currentInput = new StringBuilder();

       
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "±", "%", "←"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9]") || command.equals(".")) {
               
                currentInput.append(command);
                display.setText(currentInput.toString());
            } else if (command.equals("C")) {
               
                currentInput.setLength(0);
                display.setText("");
                num1 = num2 = result = 0;
                operator = '\0';
            } else if (command.equals("±")) {
               
                if (currentInput.length() > 0) {
                    if (currentInput.charAt(0) == '-') {
                        currentInput.deleteCharAt(0);
                    } else {
                        currentInput.insert(0, '-');
                    }
                    display.setText(currentInput.toString());
                }
            } else if (command.equals("←")) {
                
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                    display.setText(currentInput.toString());
                }
            } else if (command.equals("%")) {
                
                if (currentInput.length() > 0) {
                    double value = Double.parseDouble(currentInput.toString());
                    value = value / 100;
                    currentInput.setLength(0);
                    currentInput.append(value);
                    display.setText(currentInput.toString());
                }
            } else if (command.equals("=")) {
                
                if (operator != '\0' && currentInput.length() > 0) {
                    num2 = Double.parseDouble(currentInput.toString());
                    switch (operator) {
                        case '+': result = num1 + num2; break;
                        case '-': result = num1 - num2; break;
                        case '*': result = num1 * num2; break;
                        case '/':
                            if (num2 == 0) {
                                display.setText("Cannot divide by zero");
                                return;
                            } else {
                                result = num1 / num2;
                            }
                            break;
                    }
                    display.setText(formatResult(result));
                    currentInput.setLength(0);
                    currentInput.append(result);
                    operator = '\0'; 
                }
            } else if ("+-*/".contains(command)) {
               
                if (currentInput.length() > 0) {
                    num1 = Double.parseDouble(currentInput.toString());
                    operator = command.charAt(0);
                    currentInput.setLength(0);
                }
            }
        } catch (Exception ex) {
            display.setText("Error");
            currentInput.setLength(0);
        }
    }

    
    private String formatResult(double value) {
        if (value == (long) value)
            return String.format("%d", (long) value);
        else
            return String.format("%s", value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
