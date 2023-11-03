package MENU;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class Main {

    private static Boolean isAdmin;
//    private ArrayList<Order> orders = new ArrayList<Order>();
    public static void main(String[] args) {
        LoginWindow login = new LoginWindow(true);
        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                MenuWindow menu = new MenuWindow(isAdmin);
            }
        });
    }
    public static void setAdminRights(Boolean x) {
        isAdmin = x;
    }
}