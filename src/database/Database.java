/*
        login();
        Users:
        insertUser();
        getPicture(String username);
        getUsername(String username);
        getFullName(String username);
        getPassword(String username);
        getEmail(String username);
        getStatus(String username);
        getPhone(String username);
        getNameEmail(String email);
        getAllUsers();
        LinkedList getAllUsersG(int groupID);        To find all users in a given group
        getUserId(String username);                 Returns UserId
        getUsernameFromPhno(String phno);
        
        Insert:
        insertPot(int gid, String potvalue);

        Update:
        updatePicture(String username, String newPath);
        updateFullName(String username, String newFullName);
        updateEmail(String username, String newEmail);
        updatePassword(String username, String newPass);
        updatePhone(String username, int newNumber);

        Verify:
        verifyEmail(String email);
        verifyPhone(String number)

        Groups:
        insertGroup(userid, groupname);
        LinkedList getGroups(int userId); // To find all groups a user belongs to

        Delete:
        deleteUser(String username);
        truncateTable(String tableName);
        dropTable(String tableName);
        */

package database;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import android.content.Intent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Harsh Parakh
 */
public class Database {

    private static Connection myConn;
    private static Statement myStatement;
    private static ResultSet myRs;
    private static final String URL = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9170735";
    private static final String USERNAME = "sql9170735";//"2309296_database";
    private static final String PASSWORD = "qyUYST8qTr";

    protected static int salt_key = 5;

    /*Default Constructor*/
    public Database() {

    }

    /**
     * Gets a connection to the database
     */
    private static void getConnectionToDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //Creates a statement
            myStatement = myConn.createStatement();
            //System.out.println(myStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Insert user hashed pass
        //truncateTable("users");
        //truncateTable("transaction");
        //truncateTable("pot");
        //truncateTable("groups");
        insertUser("hp", "Harsh Parakh", "password", "123456789", "hparakh@purdue.edu", "Available", "/path/home/hparakh");
        //getTrial();
//System.out.println(getGroups(4));
        //insertTransaction(2, 4,3, "103");
        //insertGroup(2, 4,"La La Land", 1);
        //updateBoolean(6, 2, 6);
        //insertGroup(3, 5,"Blah Blah Land", 1);
        //insertTransaction(6, 5,6, "21", 1);
        //System.out.println(viewTransactions(2,3));
        //System.out.println(getAllUsersG(3));

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scan.nextLine();

        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        if (BCrypt.checkpw(password, getPassword(username))) {//password.equals(getPassword(username))){ //replace with BCrypt method checkpw after getting hashed password
            System.out.println("Correct! Logging in...");

        }

    }

    public static boolean login(String username, String password) {
        int x = 0;
        return (BCrypt.checkpw(password, getPassword(username)));
        //return password.equals(getPassword(username));
        //password.equals(getPassword(username))){ //replace with BCrypt method checkpw after getting hashed password
    }
    
    public static void getTrial() {
        getConnectionToDB();
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        //ArrayList<Integer> payeeList = new ArrayList<>();
        //ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM users");

            String amount = "";
            String gid = "";
            //int payee = 0;

            while (myRs.next()) {
                gid = myRs.getString("phone");
                number.add(gid);
                amount = myRs.getString("username");
                name.add(amount);
            }
            System.out.println(number);
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void insertUser(String user, String userNameFull, String pass, String phonenum, String mail, String stat, String profile) {

        getConnectionToDB();

        String username = user;//"iain woodburn";
        String fullname = userNameFull;
        String password = pass;//"password";
        String phone = phonenum;//1234567;
        String email = mail;//"test@email.com";
        String status = stat;//"yolo";
        String profilep = profile;

        try {
            //System.out.println(myStatement);

            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    " id int(10) NOT NULL AUTO_INCREMENT," +
                    " username varchar(255) NOT NULL," +
                    " fullname varchar(255) NOT NULL," +
                    " password varchar(255) NOT NULL," +
                    " phone varchar(25) NOT NULL," +
                    " email varchar(255) NOT NULL," +
                    " status varchar(255) NOT NULL," +
                    " picture varchar(255) NOT NULL," +
                    " PRIMARY KEY (id))");

            String salt = BCrypt.gensalt(salt_key);
            String hashed_password = BCrypt.hashpw(password, salt);
            //String hashed_password = password;

            myStatement.executeUpdate("INSERT INTO users(username, fullname, password, phone, email, status, picture) VALUES('" + username + "','" + fullname + "','" + hashed_password + "','" + phone + "','" + email + "','" + status + "','" + profilep + "')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertTransaction(int group_id, int req, int payee, String amt, int checkB) {

        getConnectionToDB();
        //int gid = group_id;
        //int id = user_id;
        //String gname = groupname;//"yolo";

        try {

            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS transaction(" +
                    " gid int(10) NOT NULL," +
                    " requester int(10) NOT NULL," +
                    " payee int(10) NOT NULL," +
                    " amount varchar(25) NOT NULL," +
                    " checkB tinyint(1) NOT NULL," +
                    " PRIMARY KEY (requester,payee))");

            myStatement.executeUpdate("INSERT INTO transaction(gid,requester,payee,amount, checkB) VALUES('" + group_id + "','" + req + "','" + payee + "','" + amt + "','" + checkB +"')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void insertPot(int group_id, String amount) {

        getConnectionToDB();

        try {

            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS pot(" +
                    " gid int(10) NOT NULL," + 
                    " pot varchar(25) NOT NULL," +
                    " PRIMARY KEY (gid))");

            myStatement.executeUpdate("INSERT INTO pot(gid, pot) VALUES('" + group_id + "','" + amount +"')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertGroup(int group_id, int user_id, String groupname, int admin) {

        getConnectionToDB();
        int gid = group_id;
        int id = user_id;
        String gname = groupname;//"yolo";

        try {

            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS groups(" +
                    " gid int(10) NOT NULL," +
                    " id int(10) NOT NULL," +
                    " gname varchar(25) NOT NULL," +
                    " admin tinyint(1) NOT NULL," +
                    " PRIMARY KEY (gid,id))");

            myStatement.executeUpdate("INSERT INTO groups(gid,id, gname,admin) VALUES('" + gid + "','" + id + "','" + gname + "','" + admin + "')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<ArrayList> viewTransactions(int requester, int payee) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE payee='" + payee + "' && requester='" + requester + "'");

            String amount = "";
            int check = 0;
            int gid = 0;

            while (myRs.next()) {
                gid = myRs.getInt("gid");
                gidList.add(gid);
                check = myRs.getInt("checkB");
                receiverList.add(check);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(receiverList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }
    
    public static ArrayList<ArrayList> getPayMade(int payee) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE payee='" + payee + "' && checkB != 0");

            String amount = "";
            int requester = 0;
            int gid = 0;

            while (myRs.next()) {
                gid = myRs.getInt("gid");
                gidList.add(gid);
                requester = myRs.getInt("requester");
                receiverList.add(requester);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(receiverList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }
    
    public static ArrayList<ArrayList> getNotifReq(int requester) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE requester='" + requester + "' && checkB != 0");

            String amount = "";
            int payee = 0;
            int gid = 0;

            while (myRs.next()) {
                gid = myRs.getInt("gid");
                gidList.add(gid);
                payee = myRs.getInt("payee");
                receiverList.add(payee);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(receiverList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }
    
    public static ArrayList<ArrayList> getLeftPayment(int requester) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE requester='" + requester + "' && checkB=0");

            String amount = "";
            int payee = 0;
            int gid = 0;

            while (myRs.next()) {
                gid = myRs.getInt("gid");
                gidList.add(gid);
                payee = myRs.getInt("payee");
                receiverList.add(payee);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(receiverList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }
    
    public static ArrayList<ArrayList> getNotifications(int payee) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE payee='" + payee + "' && checkB=0");

            String amount = "";
            int requester = 0;
            int gid = 0;

            while (myRs.next()) {
                gid = myRs.getInt("gid");
                gidList.add(gid);
                requester = myRs.getInt("requester");
                receiverList.add(requester);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(receiverList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }

    public static ArrayList<ArrayList> getAllNotif(int gpId) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> receiverList = new ArrayList<>();
        ArrayList<Integer> payeeList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE gid='" + gpId + "'");

            String amount = "";
            int requester = 0;
            int payee = 0;

            while (myRs.next()) {
                payee = myRs.getInt("payee");
                payeeList.add(payee);
                requester = myRs.getInt("requester");
                receiverList.add(requester);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(receiverList);
            aList.add(payeeList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }

    public static ArrayList<ArrayList> getAllNotifications(int requester) {
        getConnectionToDB();
        ArrayList<String> amountList = new ArrayList<>();
        ArrayList<Integer> gidList = new ArrayList<>();
        ArrayList<Integer> payeeList = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM transaction WHERE requester='" + requester + "'");

            String amount = "";
            int gid = 0;
            int payee = 0;

            while (myRs.next()) {
                payee = myRs.getInt("payee");
                payeeList.add(payee);
                gid = myRs.getInt("gid");
                gidList.add(gid);
                amount = myRs.getString("amount");
                amountList.add(amount);
            }
            aList.add(gidList);
            aList.add(payeeList);
            aList.add(amountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aList;
    }

    /*-------------------------------------------*/


    public static boolean deleteGroup(int gid, int uid) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM groups WHERE id='" + uid + "' AND gid='" + gid + "'");

            int admin = 0;

            while (myRs.next()) {
                admin = myRs.getInt("admin");
            }
            if (admin != 0) return deleteEntireG(gid);
            else return deleteUserinG(gid, uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getAdmin(int gid) {

        getConnectionToDB();
        int i = 0;
        try {

            myRs = myStatement.executeQuery("SELECT * FROM groups WHERE gid='" + gid + "' AND admin<>'" + i + "' ");

            int id = 0;

            while (myRs.next()) {
                id = myRs.getInt("id");
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getUserId(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            int id = 0;

            while (myRs.next()) {
                id = myRs.getInt("id");
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*-------------------------------------------*/


    public static ArrayList<Integer> getGroups(int userId) {
        getConnectionToDB();
        int uid = userId;
        ArrayList<Integer> numberList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT gid FROM groups WHERE id = '" + uid + "'");

            int idU = 0;

            while (myRs.next()) {
                idU = myRs.getInt("gid");
                numberList.add(idU);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberList;
    }


    public static ArrayList<Integer> getUsersFromGroup(int gid) {

        getConnectionToDB();
        int ggid = gid;
        ArrayList<Integer> numberList = new ArrayList<>();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM groups WHERE gid = '" + ggid + "'");


            int idU = 0;

            while (myRs.next()) {

                idU = myRs.getInt("id");
                numberList.add(idU);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberList;
    }

    public static String getUsernameFromPhno(String phno) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE phone='" + phno + "'");

            String uname = "";

            while (myRs.next()) {
                uname = myRs.getString("username");
            }
            return uname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getPicture(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String path = "";

            while (myRs.next()) {
                path = myRs.getString("picture");
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Get path to picture based on phone number*/
    public static String getPicturePhno(String phno) {
        getConnectionToDB();
        String number = "";
        for (int i = 0; i < phno.length(); i++) {
            if (phno.charAt(i) == '(' || phno.charAt(i) == ')' || phno.charAt(i) == ' ' || phno.charAt(i) == '+' || phno.charAt(i) == '-')
                continue;
            number += phno.charAt(i);
        }

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE phone='" + number + "'");

            String path = "";

            while (myRs.next()) {
                path = myRs.getString("picture");
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNameEmail(String email) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE email='" + email + "'");

            String firstName = null;

            while (myRs.next()) {
                firstName = myRs.getString("username");
            }
            return firstName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUsername(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String firstName = null;

            while (myRs.next()) {
                firstName = myRs.getString("username");
            }
            return firstName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUsername(int userId) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE id='" + userId + "'");

            String userName = null;

            while (myRs.next()) {
                userName = myRs.getString("username");
            }
            return userName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getGroupname(int gid) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM groups WHERE gid='" + gid + "'");

            String groupName = null;

            while (myRs.next()) {
                groupName = myRs.getString("gname");
            }
            return groupName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getFullName(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String fullname = "";

            while (myRs.next()) {
                fullname = myRs.getString("fullname");
            }
            return fullname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean updateBoolean(int gid, int requester, int payee) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE transaction SET checkB = 1 WHERE checkB = 0 AND gid='" + gid + "' AND requester='"+requester+"' AND payee='"+payee+"'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static LinkedList getAllUsersG(int groupId) {
        getConnectionToDB();
        int gid = groupId;
        LinkedList<Integer> numberList = new LinkedList<Integer>();
        try {

            myRs = myStatement.executeQuery("SELECT id FROM groups WHERE gid = '" + gid + "'");

            int idU = 0;

            while (myRs.next()) {

                idU = myRs.getInt("id");
                numberList.add(idU);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberList;
    }

    public static LinkedList getAllUsers() {

        getConnectionToDB();

        LinkedList<String> fullnameList = new LinkedList<String>();
        LinkedList<Integer> numberList = new LinkedList<Integer>();
        LinkedList<String> usernameList = new LinkedList<String>();
        LinkedList<LinkedList> linklist = new LinkedList<LinkedList>();

        try {

            myRs = myStatement.executeQuery("SELECT id, fullname, username FROM users");

            int id = 0;
            String name = null;
            String user = null;

            while (myRs.next()) {
                id = myRs.getInt("id");
                numberList.add(id);
                name = myRs.getString("fullname");
                fullnameList.add(name);
                user = myRs.getString("username");
                usernameList.add(user);
            }
            linklist.add(numberList);
            linklist.add(fullnameList);
            linklist.add(usernameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linklist;
    }

    public static String getPassword(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String password = null;

            while (myRs.next()) {
                password = myRs.getString("password");
            }
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPhone(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            int phone = -1;

            while (myRs.next()) {
                phone = Integer.parseInt(myRs.getString("phone"));
            }
            return phone;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getEmail(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String email = "";

            while (myRs.next()) {
                email = myRs.getString("email");
            }
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStatus(String username) {

        getConnectionToDB();

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            String status = "";

            while (myRs.next()) {
                status = myRs.getString("status");
            }
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Gets status based on phone number*/
    public static String getStatusPhno(String phno) {

        getConnectionToDB();
        String number = "";
        for (int i = 0; i < phno.length(); i++) {
            if (phno.charAt(i) == '(' || phno.charAt(i) == ')' || phno.charAt(i) == ' ' || phno.charAt(i) == '+' || phno.charAt(i) == '-')
                continue;
            number += phno.charAt(i);
        }

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE phone='" + number + "'");

            String status = "";

            while (myRs.next()) {
                status = myRs.getString("status");
            }
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateFullName(String username, String newFullName) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE users SET fullname='" + newFullName + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePicture(String username, String newPath) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE users SET picture='" + newPath + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePassword(String username, String newPass) {
        getConnectionToDB();

        String salt = BCrypt.gensalt(salt_key);
        String hashed_password = BCrypt.hashpw(newPass, salt);

        try {
            myStatement.executeUpdate("UPDATE users SET password='" + hashed_password + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePhone(String username, String newNumber) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE users SET phone='" + newNumber + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateEmail(String username, String newEmail) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE users SET email='" + newEmail + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUser(String username) {
        getConnectionToDB();
        try {
            myStatement.executeUpdate("DELETE FROM users WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteEntireG(int gid) {
        getConnectionToDB();
        try {
            myStatement.executeUpdate("DELETE FROM groups WHERE gid='" + gid + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUserinG(int gid, int uid) {
        getConnectionToDB();
        try {
            myStatement.executeUpdate("DELETE FROM groups WHERE gid='" + gid + "' AND id='" + uid + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateStatus(String username, String newStatus) {
        getConnectionToDB();

        try {
            myStatement.executeUpdate("UPDATE users SET status='" + newStatus + "' WHERE username='" + username + "'");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean verifyEmail(String email) {
        getConnectionToDB();
        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE email='" + email + "'");

            if (!myRs.next()) {
                return true; //If there is no such entry in that table
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verifyPhone(String n) {
        getConnectionToDB();

        String number = "";
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == '(' || n.charAt(i) == ')' || n.charAt(i) == ' ' || n.charAt(i) == '+' || n.charAt(i) == '-')
                continue;
            number += n.charAt(i);
        }
        long phno;
        phno = Long.parseLong(number);

        try {

            myRs = myStatement.executeQuery("SELECT * FROM users WHERE phone='" + number + "'");

            if (!myRs.next()) {
                return true; //If there is no such entry in that table. No such number exists
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; //If number exists
    }

    public static void truncateTable(String tableName) {
        getConnectionToDB();
        String tname = tableName;
        try {
            myStatement.executeUpdate("TRUNCATE TABLE " + tname + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(String tableName) {
        getConnectionToDB();
        String tname = tableName;
        try {
            myStatement.executeUpdate("DROP TABLE IF EXISTS " + tname + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}