package MENU;

import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void signinOK() {
        Login login = new Login();
        assertNotNull(login);
        assertTrue(Login.validAdmin("sei", "7050"));
    }

    @Test
    public void signinNO() {
        Login login = new Login();
        assertNotNull(login);
        assertFalse(Login.validAdmin(" ", " "));
    }

    @Test
    public void signupOK() {
        Login login = new Login();
        assertNotNull(login);
        assertTrue(Login.signUp("new-user", ":D"));
    }

    @Test
    public void signupNO() {
        Login login = new Login();
        assertNotNull(login);
        assertFalse(Login.signUp("  ", "white-space"));
    }
}