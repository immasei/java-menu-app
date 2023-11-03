package MENU;

import java.util.HashMap;

public class Login {
    static HashMap<String, String> account;
    static String filename = "/MENU/loginDB.txt";

    //signin
    public static boolean validAdmin(String user, String pass) {
        updateAccount();
        System.out.print(account);
        for (String key: account.keySet()){
            if (key.equals(user) && account.get(key).equals(pass)){
                return true;
            }
        }
        return false;
    }

    //signup
    public static boolean signUp(String user, String pass) {
        updateAccount();

        // Check for empty or whitespace-only username or password
        if (user.trim().isEmpty() || pass.trim().isEmpty() || user.contains(" ") || pass.contains(" ")) {
            return false;
        }

//        // Check if the username already exists
//        if (account.containsKey(user)) {
//            // If the username exists, return false to indicate that the signup failed
//            return false;
//        }

        // If the username is unique, add it to the account
        Writer upd = new Writer(filename, user, pass);
        return true;
    }


    public static void updateAccount() {
        //reread db and update account list
        Reader log = new Reader(filename, 2);
        account = log.getInfo();
    }
}
