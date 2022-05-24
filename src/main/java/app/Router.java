package app;

/*
  Main Class for running the app
 */

import controller.PeoplesController;
import data.WriteToFile;
import db.Db;
import views.PeoplesView;

public class Router {

    private static boolean exit = false;
/*
main class, run until the user choose 6 - to exit
 */

    public static void main(String[] args) {

        WriteToFile.initializeCollection();// initialize  JSON File
        PeoplesController.initialize(); // initialize DB
        while(!exit) {
            int action = PeoplesView.menu();
            if (action == 6){
                exit = true;
            } else {
                PeoplesController.DBQuery(action);
            }
        }
        Db.closeDatabaseConnection();
    }
}