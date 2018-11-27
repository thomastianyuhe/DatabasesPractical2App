import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyLoginGUI extends JFrame implements ActionListener {
    JLabel loginLabel, userNameLabel, passwordLabel;
    JTextField usernameField;
    JButton loginButton;
    JPasswordField passwordField;
    MyLoginGUI() {
        JFrame frame = new JFrame();
        loginLabel = new JLabel();
        loginLabel.setForeground(Color.CYAN);

        userNameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginLabel.setBounds(120, 40, 200, 30);
        userNameLabel.setBounds(80, 70, 200, 30);
        passwordLabel.setBounds(80, 110, 200, 30);
        usernameField.setBounds(300, 70, 200, 30);
        passwordField.setBounds(300, 110, 200, 30);
        loginButton.setBounds(250, 200, 100, 30);

        frame.add(loginLabel);
        frame.add(userNameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.setSize(600, 300);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String correctPassword = MyConnect.getCustomerPassword(username);
        if(password.equals(correctPassword))
        {
            int customerId = MyConnect.getCustomerId(username);
            new MyGUI(customerId);
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Incorrect Username/Password",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MyLoginGUI();
    }
}