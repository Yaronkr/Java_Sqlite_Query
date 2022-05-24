package db;

import models.Person;
import views.PeoplesView;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
    Class will handle all DataBase related actions
 */
public class Db {

    /*
        Will connect to the Database people.db, if not exist it will create one.
     */
    public static Connection connection;

    public static void establishDatabaseConnection(){
        System.out.println(">>> Trying to connect to database...");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./src/main/java/db/people.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println(">>> Opened database successfully");
    }
    /*
        create Person table with the following fieldsL ID, FIRSTNAME, LASTNAME, AGE, COUNTRY
     */
    public static void createTable(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE Person");
            System.out.println(">>> Creating new Table...");
            String sql = "CREATE TABLE IF NOT EXISTS Person " +
                    "(ID INTEGER PRIMARY KEY       NOT NULL   ," +
                    " FIRSTNAME       CHAR(50)    NOT NULL, " +
                    " LASTNAME        CHAR(50), " +
                    " AGE             INT          NOT NULL, " +
                    " COUNTRY         CHAR(50) )";

            stmt.executeUpdate(sql);
            System.out.println(">>> Table created successfully");
            stmt.close();
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage()    );
        }
    }
    /*
        Close connection to DB at the end of the program
     */
    public static void closeDatabaseConnection(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /*
       Save all values in the randomPeoples list into the DB
    */
    public static void save (ArrayList<Person> randomPeoples) {
        try {
            Statement stmt = connection.createStatement();
            for (Person person : randomPeoples) {
                String sql = String.format(
                        "INSERT INTO Person (FIRSTNAME,LASTNAME,AGE,COUNTRY) " +
                                "VALUES ('%s', '%s', %d, '%s' );",person.getfirstName(),person.getlastName(),person.getage(),person.getcountry() );
                stmt.executeUpdate(sql);
                stmt.close();
            }
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    /*
         Print DB content
     */
    public static void all(){
        try {
            System.out.println("\n>>> Printing DATABASE");
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM Person";
            ResultSet rs = stmt.executeQuery(query);
            Person printPerson = new Person();
            while (rs.next()){
                printPerson.setID( rs.getInt("ID"));
                printPerson.setfirstName( rs.getString("FIRSTNAME"));
                printPerson.setlastName(rs.getString("LASTNAME"));
                printPerson.setage(rs.getInt("AGE"));
                printPerson.setcountry(rs.getString("COUNTRY"));
                PeoplesView.printPerson(printPerson);
            }
            System.out.println("\n");
        }catch (Exception e){
            System.out.println("Error :" + e.getMessage());
        }
    }
/*
    Retunr person table size
 */
    public static int getTableSize(){
        try {
            System.out.println("\n>>> Printing DATABASE");
            Statement stmt = connection.createStatement();
            String query = "SELECT count(*) FROM Person";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int size = rs.getInt(1);

            return size;
        }catch (Exception e){
            System.out.println("Error :" + e.getMessage());
        }
        return -1;
    }
    /*
       find the oldest person in the DB.
       Further improvements: add to a list of old peoples in case there is more than 1
    */
    public static Person findOldest() {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM Person order by AGE DESC limit 1";
            Person oldestPerson = new Person();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                oldestPerson.setID( rs.getInt("ID"));
                oldestPerson.setfirstName( rs.getString("FIRSTNAME"));
                oldestPerson.setlastName(rs.getString("LASTNAME"));
                oldestPerson.setage(rs.getInt("AGE"));
                oldestPerson.setcountry(rs.getString("COUNTRY"));
            }
            return oldestPerson;
        }catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
        return null;
    }
    /*
       find the youngest person in the DB.
       Further improvements: add to a list of young peoples in case there is more than 1
    */
    public static Person findYoungest() {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM Person order by AGE limit 1";
            Person youngestPerson = new Person();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                youngestPerson.setID( rs.getInt("ID"));
                youngestPerson.setfirstName( rs.getString("FIRSTNAME"));
                youngestPerson.setlastName(rs.getString("LASTNAME"));
                youngestPerson.setage(rs.getInt("AGE"));
                youngestPerson.setcountry(rs.getString("COUNTRY"));
            }
            return youngestPerson;
        }catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
        return null;
    }
    /*
        Count peoples in the DB by given country
     */
    public static HashMap<String, String> groupByCountry() {
        try {
            HashMap<String, String> groupByCountry = new HashMap<String, String>();
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNTRY ,COUNT(*) COUNTRY FROM Person GROUP BY COUNTRY";
            ResultSet rs = stmt.executeQuery(query);
            int columnsNumber = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i+=2) {
                    String country = rs.getString(i);
                    String countryCount = rs.getString(i+1);
                    groupByCountry.put(country, countryCount);
                }
            }
            return groupByCountry;
        }catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
        return null;
    }
    /*
        For a given country group by age ranges: 0 -> 10, 10 -> 20 etc
     */
    public static ArrayList<String> countryCountPerAge(String country) {
        ArrayList<String> CountryAgeGroup = new ArrayList<String>();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT AGE FROM Person WHERE COUNTRY LIKE '%" + country + "%' ORDER BY AGE";
            ResultSet rs = stmt.executeQuery(query);
            int columnsNumber = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String age = rs.getString(i);
                    CountryAgeGroup.add(age);
                }
            }
            return CountryAgeGroup;
        }catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
        return CountryAgeGroup;
    }
}
