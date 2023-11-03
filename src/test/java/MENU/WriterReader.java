package MENU;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WriterReader {
    @Test
    public void ReaderClass() {
        Reader r = new Reader("/MENU/loginDB.txt", 2);
        Reader b = new Reader("/MENU/menus.txt", 3);
        assertNotNull(r);
        assertNotNull(b);
    }

    @Test
    public void WriterClass() {
        Writer w = new Writer("/MENU/loginDB.txt", "user1", "pass1");

        assertNotNull(w);
    }
}
