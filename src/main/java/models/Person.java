package models;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
@Document(collection = "queries", schemaVersion= "1.0")

public class Person {
    @Id
    private int ID;
    private String firstName;
    private String lastName;
    private int age;
    private String country;

    //Getters
    public String getfirstName() {return firstName;}
    public String getlastName() {return lastName;}
    public int getage() {return age;}
    public String getcountry() {return country;}
    public int getID() {return ID;}

    // Setters
    public void setfirstName(String firstName){ this.firstName = firstName;}
    public void setlastName(String lastName){ this.lastName = lastName;}
    public void setage(int age){ this.age = age;}
    public void setcountry(String country){ this.country = country;}
    public void setID(int ID){ this.ID = ID;}

    @Override
    public String toString() {
        return age + " years old " + firstName + " " + lastName + " from " + country;
    }
}
