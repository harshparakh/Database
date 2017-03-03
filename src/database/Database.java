/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 *
 * @author Iain Woodburn
 */
public class Database {

    private static Connection myConn;
    private static Statement myStatement;
    private static ResultSet myRs;
    private static final String URL = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9161251";
    private static final String USERNAME = "sql9161251";//"2309296_database";
    private static final String PASSWORD = "IWlKpzdRvp";
   
    protected static int salt_key = 5;

    public Database(){
        //Default Constructor
    }

    /**
     * Gets a connection to the database
     */
    private static void getConnectionToDB(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection(URL, USERNAME , PASSWORD);
            //Creates a statement
            myStatement = myConn.createStatement();
            //System.out.println(myStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e){
            
        }

    } //end getConnectionToDB
/*$host="fdb16.biz.nf";
$port=3306;
$socket="";
$user="2309296_database";
$password="Parakh@6054";
$dbname="2309296_database";*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Insert user hashed pass
        //insertUser("harshparakh","Harsh Parakh","password",12456789,"hparakh@purdue.edu","Available");
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scan.nextLine();
        
        System.out.print("Enter your password: ");
        String password = scan.nextLine();
        
        if(BCrypt.checkpw(password , getPassword(username))){//password.equals(getPassword(username))){ //replace with BCrypt method checkpw after getting hashed password
            System.out.println("Correct! Logging in...");
         
       
       //System.out.println(getStatus(username));
       //updateStatus(username,"New Status");
       //System.out.println(getStatus(username));
       }
        
       //System.out.println(deleteUser(username));
       //System.out.println(getEmail(username));
    }
    
    public static void insertUser(String user, String userNameFull, String pass, int phonenum, String mail, String stat){

        getConnectionToDB();

        String username = user;//"iain woodburn";
        String fullname = userNameFull;
        String password = pass;//"password";
        int phone = phonenum;//1234567;
        String email = mail;//"test@email.com";
        String status = stat;//"yolo";

        try{
        //System.out.println(myStatement);
        myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
" user_id int(10) NOT NULL AUTO_INCREMENT," +
" user_name varchar(255) NOT NULL," +
" user_full_name varchar(255) NOT NULL," +
" user_password varchar(255) NOT NULL," +
" user_phone int(10) NOT NULL," +
" user_email varchar(255) NOT NULL," +
" user_status varchar(255) NOT NULL," +
" PRIMARY KEY (user_id))");

        String salt = BCrypt.gensalt(salt_key);
	String hashed_password = BCrypt.hashpw(password, salt);

        
        myStatement.executeUpdate("INSERT INTO users(user_name, user_full_name, user_password, user_phone, user_email, user_status) VALUES('"+username+"','"+fullname+"','"+hashed_password+"','"+phone+"','"+email+"','"+status+"')");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getUsername(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username+"'");

            String firstName = "";

            while(myRs.next()){
                firstName = myRs.getString("user_name");
            }
        return firstName;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getFullName(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username+"'");

            String fullname = "";

            while(myRs.next()){
                fullname = myRs.getString("user_full_name");
            }
        return fullname;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean updateFullName(String username, String newFullName){
    getConnectionToDB();
    
    try{
        myStatement.executeUpdate("UPDATE users SET user_full_name='" + newFullName + "' WHERE user_name='" + username + "'");
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }

    public static String getPassword(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username + "'");

            String password = "";

            while(myRs.next()){
                password = myRs.getString("user_password");
            }
        return password;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean updatePassword(String username, String newPass){
    getConnectionToDB();
    
    String salt = BCrypt.gensalt(salt_key);
    String hashed_password = BCrypt.hashpw(newPass, salt);
        
    try{
        myStatement.executeUpdate("UPDATE users SET user_password='" + hashed_password + "' WHERE user_name='" + username + "'");
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }

    public static int getPhone(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username + "'");

            int phone = -1;

            while(myRs.next()){
                phone = Integer.parseInt(myRs.getString("user_phone"));
            }
        return phone;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    
    public static boolean updatePhone(String username, int newNumber){
    getConnectionToDB();
    
    try{
        myStatement.executeUpdate("UPDATE users SET user_phone='" + newNumber + "' WHERE user_name='" + username + "'");
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }

    public static String getEmail(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username+"'");

            String email = "";

            while(myRs.next()){
                email = myRs.getString("user_email");
            }
        return email;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean updateEmail(String username, String newEmail){
    getConnectionToDB();
    
    try{
        myStatement.executeUpdate("UPDATE users SET user_email='" + newEmail + "' WHERE user_name='" + username + "'");
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }
    
    public static boolean verifyEmail(String email){
        getConnectionToDB();
        try{
            
           myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_email='" + email +"'");

if(!myRs.next()){
return true; //If there is no such entry in that table
}
 
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean deleteUser(String username){
        
        try{
            myStatement.executeUpdate("DELETE FROM users WHERE user_name='"+username+"'");
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
     return false;   
    }
    
    public static String getStatus(String username){

        getConnectionToDB();

        try{

        myRs = myStatement.executeQuery("SELECT * FROM users WHERE user_name='" + username+"'");

            String status = "";

            while(myRs.next()){
                status = myRs.getString("user_status");
            }
        return status;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
      
    public static boolean updateStatus(String username, String newStatus){
    getConnectionToDB();
    
    try{
        myStatement.executeUpdate("UPDATE users SET user_status='" + newStatus + "' WHERE user_name='" + username + "'");
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return false;
    }
}