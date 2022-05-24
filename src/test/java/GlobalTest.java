import data.WriteToFile;
import db.Db;
import models.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/*
    Simple testing to test Db size, json file size, and program queries.
 */
class GlobalTest {

    static ArrayList<Person> people;

    @BeforeAll
    static void init(){
        Db.establishDatabaseConnection();
        Db.createTable();
        Person person1 = new Person(1,"Carminem", "Carter", 29,"AUSTRIA" );
        Person person3 = new Person(2,"Dora", "Collier", 99,"AUSTRIA" );
        Person person2 = new Person(3,"Barbara", "Lind", 1,"URUGUAY" );
        people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Db.save(people);

    }

    @Test
    void getOldest(){
        Assertions.assertEquals(99, Db.findOldest().getage());
    }

    @Test
    void getYoungest(){
        Assertions.assertEquals(1, Db.findYoungest().getage());
    }

    @Test
    void getPersonsSize(){
        System.out.println("Table size: "+Db.getTableSize());
        Assertions.assertEquals(people.size(), Db.getTableSize());
    }

    @Test
    void createTable(){
        Assertions.assertDoesNotThrow(() -> {
            Db.establishDatabaseConnection();
            Db.createTable();
        });
    }

    @Test
    void groupByCountry(){

        Assertions.assertEquals("2",Db.groupByCountry().get("AUSTRIA"));
        Assertions.assertEquals("1",Db.groupByCountry().get("URUGUAY"));
        int minValue = 0;
        int maxValue = 10;
        Assertions.assertTrue( Db.getTableSize() >= minValue && Db.getTableSize() <= maxValue);
    }

    @Test
    void JSONSize(){
      WriteToFile.initializeCollection();
      for (Person person : people) WriteToFile.writeToCollection(person);
      Assertions.assertEquals(people.size(),WriteToFile.countJSONRows());
    }
}


