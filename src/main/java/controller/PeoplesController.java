package controller;
import data.WriteToFile;
import db.Db;
import models.GeneratePeoples;
import models.Person;
import views.PeoplesView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;

/*

This class will be used to initiate different actions in the program
 */

public class PeoplesController {

    public static final ArrayList<String> countryList = new ArrayList<>();

    public static void initialize ()  {
        try {
            WriteToFile.initializeCollection();
            PeoplesController.createCountryList();
            Db.establishDatabaseConnection();
            Db.createTable();
            int n = PeoplesView.numberOfPeople();
            GeneratePeoples.create(n);
            Db.save(GeneratePeoples.randomPeoples);

            // Save queries to JSON file
            System.out.println("\n>>> We are going to save "+ GeneratePeoples.randomPeoples.size()+ " person(s) to JSON file...");
            for (Person person : GeneratePeoples.randomPeoples) {
               WriteToFile.writeToCollection(person);
            }
            Db.all();
        }
        catch (Exception exception){
            System.out.println(">>> Error:" + exception.getMessage());
        }
    }
/*
// load the countries list from the countries.txt file, and save to arraylist
 */
    public static void createCountryList(){
        try {
            File countryFile = new File("./src/main/java/data/countries.txt");
            Scanner myReader = new Scanner(countryFile);
            countryList.clear();
            while (myReader.hasNextLine()) {
                countryList.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /*
    action list for the program
     */
    public static void DBQuery(int action){
        switch (action) {
            case 1 : {
                Person oldest = Db.findOldest();
                PeoplesView.printOldest(oldest);
                break;
            }
            case 2 : {
                PeoplesView.printGroupByCountry(Objects.requireNonNull(Db.groupByCountry()));
                break;
            }
            case 3 : {
                PeoplesView.printCountryByGroup(Db.countryCountPerAge(PeoplesView.countryToGroupBy()));
                break;
            }
            case 4 : {
                Person youngest = Db.findYoungest();
                PeoplesView.printYoungest(youngest);
                break;
            }
            case 5: {
                Db.closeDatabaseConnection();
                PeoplesController.initialize();
                break;
            }
        }
    }
}
