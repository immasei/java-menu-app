package MENU;
import java.io.*;
import java.util.ArrayList;

public class Writer {
    private String filepath = "src/main/resources";

    public Writer(String filename, String user, String pass) {
        String newLine = user + "," + pass;
        try {
            FileWriter f = new FileWriter(filepath + filename, true);
            f.write("\n" + user + ", " + pass);
            f.close();
        }catch (IOException e) {
            System.out.println("Error loginDB");
        }
    }

    public Writer(String filename, ArrayList<String> foodData) {
        try {
            File f = new File(filepath + filename);
            PrintWriter w = new PrintWriter(f);
            for (String line: foodData) {
                w.println(line);
            }
            w.close();
        }catch (FileNotFoundException e) {
            System.out.println("Error menus");
        }


    }
}
