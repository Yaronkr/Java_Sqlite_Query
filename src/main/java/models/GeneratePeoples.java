package models;

import com.github.javafaker.Faker; // gem to create fake data
import controller.PeoplesController;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;

import static java.lang.System.*;

/*
    Class will generate the random data, countries will be created from the data/countries.txt file
    Othe peoples fields will be generated with the faker gem
 */
public class GeneratePeoples {
    public static final ArrayList<Person> randomPeoples = new ArrayList<>();

    private static final int minAge = 0;// minimum age of generated person
    private static final int maxAge =99; // maximum age of generated person
    static Faker faker = new Faker();
    /*
        Set the country for each Person instance
     */
    private static String getCountry(){
        int randomNum = faker.number().numberBetween(0, PeoplesController.countryList.size()-1);
        return PeoplesController.countryList.get(randomNum);
    }
/*
    Create random instances of the Person class
 */

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
