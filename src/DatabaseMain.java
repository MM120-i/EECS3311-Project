import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class DatabaseMain {
    public static void main(String[] args) {
        java.util.Date d = new Date();
        DatabaseAccess dba = new DatabaseAccess();
        User user1 = new User("JeffreyJiang", 1, d, 150,  100, 1, 0);
        //dba.addUser(user1);
        //dba.deleteUser(user1);
        dba.updateUser(user1, 3, d);
    }
}