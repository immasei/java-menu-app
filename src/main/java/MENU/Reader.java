package MENU;
import java.io.*;
import java.util.*;

public class Reader {
    private String filepath = "src/main/resources";
    private HashMap<String, String> info = new HashMap<String, String>();
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    public Reader(String filename, int fields) {
        try {
            File f = new File(filepath + filename);
            Scanner sc = new Scanner(f);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                if (fields == 2) {
                    String user = arr[0].trim();
                    String pass = arr[1].trim();
                    info.put(user, pass);
                }
                if  (fields == 3){
                    String name = arr[0].trim();
                    double price = Double.parseDouble(arr[arr.length-1].trim());
                    //if has , in description join Array[1:-1]
                    String s = "";
                    for (int j = 1; j < arr.length-1; j++) {
                        s += (String) arr[j];
                        //if not last description
                        if (j!=arr.length-2)
                            s += ",";
                    }
                    menuItems.add(new MenuItem(name, s, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
        }
    }

    public HashMap<String, String> getInfo() {
        return info;
    }
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
}
