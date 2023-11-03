package MENU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {
    private JPasswordField password;
    private JTextField username;
    private JLabel pass, user, message;
    private JButton signin, reset, signup, guest;
    private JCheckBox show;
    private Boolean isAdmin = false;

    LoginWindow(boolean firstTime) {
        Font font1 = new Font("Consolas", 1, 17);
        Font font2 = new Font("Consolas", 0, 16);
        Font font3 = new Font("Consolas", 1, 14);

        ImageIcon logo = new ImageIcon("src/main/resources/MENU/logo1.jpg");
        setTitle("CC-A24-G02-Ankit");
        setSize(500, 350);
        setIconImage(logo.getImage());
        if (firstTime)
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(227, 227, 203));

        //label text space
        user = new JLabel("Username");
        user.setBounds(40, 50, 100, 40);
        user.setFont(font1);

        pass = new JLabel("Password");
        pass.setBounds(40, 100, 100, 40);
        pass.setFont(font1);

        //create text space
        username = new JTextField();
        username.setBounds(140, 50, 300, 40);
        username.setFont(font2);

        password = new JPasswordField();
        password.setBounds(140, 100, 300, 40);
        password.setEchoChar('*');
        password.setFont(font2);

        //button
        signin = new JButton("Sign in");
        signin.setBounds(140, 200, 95, 40);
        signin.setBackground(new Color(42, 157, 143));
        signin.setOpaque(true);
        signin.setBorderPainted(false);

        reset = new JButton("Reset");
        reset.setBounds(243, 200, 95, 40);
        reset.setBackground(new Color(244, 162, 97));
        reset.setOpaque(true);
        reset.setBorderPainted(false);

        signup = new JButton("Signup");
        signup.setBounds(140, 200, 95, 40);
        signup.setBackground(new Color(233, 196, 106));
        signup.setOpaque(true);
        signup.setBorderPainted(false);


        guest = new JButton("Guest");
        guest.setBounds(345, 200, 95, 40);
        guest.setBackground(new Color(231, 111, 81));
        guest.setOpaque(true);
        guest.setBorderPainted(false);

        JButton[] button = {signin, reset, signup, guest};
        for (int i = 0; i < button.length; i++) {
            button[i].setFocusable(false);
            button[i].addActionListener(this);
            button[i].setFont(font3);
        }

        //check box
        show = new JCheckBox("Show password");
        show.setBounds(140, 150, 300, 40);
        show.setFocusable(false);
        show.addActionListener(this);
        show.setFont(font3);
        show.setBackground(new Color(227, 227, 203));

        //message
        message = new JLabel(":D");
        message.setBounds(140, 250, 300, 50);
        message.setFont(font3);
        message.setForeground(Color.red);

        //show
        add(user);
        add(pass);
        add(username);
        add(password);
        add(reset);
        add(show);
        add(message);

        if (firstTime) {
            add(signin);
            add(guest);
        } else {
            add(signup);
        }
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent x) {
        String msg;
        if (x.getSource() == signin) {
            if (Login.validAdmin(username.getText(), new String(password.getPassword()))) {
                msg = "Welcome back " + username.getText() + ". :D";
                JOptionPane.showMessageDialog(this, msg);
                Main.setAdminRights(true);
                this.dispose(); //close log in window
            } else {
                msg = "Invalid account!";
            }
        } else if (x.getSource() == signup) {
            if (Login.signUp(username.getText(), new String(password.getPassword()))) {
                msg = "Welcome " + username.getText() + ". :3";
                JOptionPane.showMessageDialog(this, msg);
                this.dispose(); //close log in window
            } else {
                msg = "Account existed or invalid characters!";
            }
        } else if (x.getSource() == guest) {
            msg = "Welcome customers!";
//            JOptionPane.showMessageDialog(this, msg);
            Main.setAdminRights(false);
            this.dispose(); //close log in window
        } else if (x.getSource() == reset) {
            username.setText("");
            password.setText("");
            msg = ":<";
        } else {
            if (show.isSelected()) {
                password.setEchoChar((char) 0);
                msg = ":>";
            } else {
                password.setEchoChar('*');
                msg = ":')";
            }
        }
        message.setText(msg);
    }
}

