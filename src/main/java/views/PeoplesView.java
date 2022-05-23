package views;

import controller.PeoplesController;
import db.Db;
import models.GeneratePeoples;
import models.Person;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PeoplesView {

    private static final Scanner userAction = new Scanner(System.in);
    private static String NumOfPeople = null;
    private static String action = null;
    private static final String menu = "Which Database Query would you like to do? \n"  +
            "1: Find the oldest person \n" +
            "2: Group by country and return count \n" +
            "3: For a given country group by age ranges \n" +
            "4: Find the youngest person \n" +
            "5: Start Program Again\n" +
            "6: exit program";
    private static int validation (String num){
        try {
            int n = Integer.parseInt(num);
            if (n > 0 && n <= 100 ){
                return n;
            } else {
                System.out.println("Invalid, please enter number between 1 to 100");
                NumOfPeople = userAction.nextLine();
                return validation(NumOfPeople);
            }
        }
        catch (Exception err) {
            System.out.println("Invalid, please enter number between 1 to 100");
            NumOfPeople = userAction.nextLine();
            return validation(NumOfPeople);
        }
    }

    private static int QueryValidation (String num){
        try {
            int n = Integer.parseInt(num);
            if (n >= 1 && n <= 6 ){
                return n;
            } else {
                System.out.println("Invalid input \n" + menu);
                action = userAction.nextLine();
                return QueryValidation(action);
            }
        }
        catch (Exception err) {
            System.out.println("Not number, please try again \n" + menu);
            action = userAction.nextLine();
            return QueryValidation(action);
        }
    }

    public static int numberOfPeople(){
        action = null;
        System.out.println("*********** PROGRAM START ***********");
        System.out.println("How many random people would you like to create? please enter a number between 1 to 100");
        action = userAction.nextLine();
        return validation(action);
    }

    public static int menu() {
        System.out.println(menu);
        action = userAction.nextLine();
        return QueryValidation(action);
    }

    public static void printOldest(Person oldest){
        System.out.println("The oldest person is:\n");
        System.out.println("ID: " + oldest.getID() + " - Name: " + oldest.getfirstName() + " " +
                oldest.getlastName() + " - country: " +
                oldest.getcountry() + " - age: " + oldest.getage() +"\n");
    }

    public static void printYoungest(Person youngest){
        System.out.println("The youngest person is:\n");
        System.out.println("ID: " + youngest.getID() + " - Name: " + youngest.getfirstName() + " " +
                youngest.getlastName() + " - country: " +
                youngest.getcountry() + " - age: " + youngest.getage() +"\n");
    }

    public static void printGroupByCountry(@NotNull HashMap<String, String> groupByCountry){
        System.out.println("Number of people per country:\n");
        for (String i : groupByCountry.keySet()) {
            System.out.println("Country: " + i + " Number of peoples: " + groupByCountry.get(i));
        }
        System.out.println("\n");
    }

    public static String countryToGroupBy(){
        System.out.println("Which country would you like to group by?");
        String country = userAction.nextLine();
        country = country.toUpperCase();
        if (PeoplesController.countryList.contains(country)) {
            return country;
        }
            System.out.println("Country not in the list, try again");
            return countryToGroupBy();
    }

    public static void printCountryByGroup (ArrayList<String> CountryAgeGroup) {
        int minRange = 0;
        int maxRange = 10;
        int counter = 0;
        boolean found = false;
        for (String age : CountryAgeGroup){
            int intAge = Integer.parseInt(age);
            found = false;
            while (!found) {
                if (intAge >= minRange && intAge <= maxRange) {
                    counter++;
                    found = true;
                } else {
                    System.out.println("Age Group:" + minRange + " - " + maxRange + " | " + counter);
                    counter = 0;
                    minRange += 10;
                    maxRange += 10;
                }
            }

        }
        System.out.println("Age Group:" + minRange + " - " + maxRange + " | " + counter);
    }
}
