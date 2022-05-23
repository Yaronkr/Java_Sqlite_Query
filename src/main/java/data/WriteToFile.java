package data;
import io.jsondb.JsonDBTemplate;
import models.Person;
/*
    Class will handle all the queries.json file  actions
 */

public class WriteToFile {

    private final static String dbFileLocation = System.getProperty("user.dir") + "/src/main/java/data";
    private static final String baseScanPackage = "models";
    private static final JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(dbFileLocation, baseScanPackage);
    /*
        If queries.json not exist it will create it, if exist it will delete the content.
        and create new collection.
     */
    public static void initializeCollection(){
        try {
            if ( jsonDBTemplate.collectionExists("queries")){
                //wipe collection to clear previous saved data
                jsonDBTemplate.dropCollection("queries");
            }
            // Create new collection
            jsonDBTemplate.createCollection(Person.class);
        }
        catch (Exception exception){
            System.out.println(">>> Error: "+ exception.getMessage());
        }
    }
/*
    Will insert new instance of the Person class into the queries.json file
 */
    public static void writeToCollection(Person person){
        try {
            jsonDBTemplate.insert(person);
        }
        catch (Exception exception){
            System.out.println(">>> Error: " + exception.getMessage());
        }
    }

}
