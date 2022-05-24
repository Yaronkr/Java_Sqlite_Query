package data;
import io.jsondb.JsonDBTemplate;
import models.Person;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
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
    public static void  initializeCollection(){
        try {
            if ( jsonDBTemplate.collectionExists("queries")){
                //wipe collection to clear previous saved data
                jsonDBTemplate.dropCollection("queries");
                TimeUnit.SECONDS.sleep(2);
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
/*
    Counting number of rows inside Json file, only for testing
 */
    public static int countJSONRows() {
        try{
            int rows = 0;
            BufferedReader reader = new BufferedReader(new FileReader(dbFileLocation+"/queries.json"));
            while(reader.readLine() != null ){
                rows ++;

            }
            return rows - 1;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
