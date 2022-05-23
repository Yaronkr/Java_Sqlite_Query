package models;

import com.github.javafaker.Faker;
import controller.PeoplesController;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

import static java.lang.System.*;

public class GeneratePeoples {
    public static final ArrayList<Person> randomPeoples = new ArrayList<>();

    private static final int minAge = 0;// minimum age of generated person
    private static final int maxAge =99; // maximum age of generated person
    static Faker faker = new Faker();

    private static String getCountry(){
        int randomNum = faker.number().numberBetween(0, PeoplesController.countryList.size()-1);
        return PeoplesController.countryList.get(randomNum);
    }


    public static void create(int numOfPeoples){
        out.println("We going to create " + numOfPeoples + " of peoples");
        randomPeoples.clear();
        for (int i =0; i < numOfPeoples; i++){
            Person newPerson = new Person();
            newPerson.setID(i+1);
            newPerson.setfirstName(faker.name().firstName());
            newPerson.setlastName(faker.name().lastName());
            newPerson.setage(faker.number().numberBetween(minAge,maxAge));
            newPerson.setcountry( getCountry());
            randomPeoples.add(newPerson);
        }
    }
}
