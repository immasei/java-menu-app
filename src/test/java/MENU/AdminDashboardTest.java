package MENU;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.*;

public class AdminDashboardTest {
    AdminDashboard ad;
    JButton[] button;

    @Before
    public void setup() {
        ad = new AdminDashboard();
        button = ad.getButton();
        ad.updateOrder();
    }
    @Test
    public void buttonTest() {
        JButton save = button[0];
        JButton add = button[1];
        JButton delete = button[2];

        assertNotNull(save);
        assertNotNull(add);
        assertNotNull(delete);

        add.doClick();
        delete.doClick();
        save.doClick();
    }
}
