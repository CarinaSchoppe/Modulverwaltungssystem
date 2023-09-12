package de.lisa.studiumsorganisation;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.view.MainUI;
import de.lisa.studiumsorganisation.webscraper.PDFReader;

/**
 * The Main class is responsible for launching the application.
 * It loads the database and starts the user interface.
 */
public class Main {

    /**
     * Represents the dummy launch flag.
     * <p>
     * This flag determines whether the application is launched in a dummy mode or not.
     * When the flag is set to true, the application is launched in dummy mode, otherwise it is launched in regular mode.
     * <p>
     * The dummy mode is used for testing or simulation purposes, where certain functionalities or components are
     * substituted or disabled. It allows for safe and controlled testing without affecting the production environment.
     * <p>
     * Usage Example:
     * <p>
     * if (dummyLaunch) {
     * // Execute dummy mode logic
     * ...
     * } else {
     * // Execute regular mode logic
     * ...
     * }
     */
    private static final boolean dummyLaunch = true;

    /**
     * Checks if the current launch is a dummy launch.
     *
     * @return true if the current launch is a dummy launch, false otherwise
     */
    public static boolean isDummyLaunch() {
        return dummyLaunch;
    }


    /**
     * The main method of the application.
     * <p>
     * This method loads the database and starts the user interface (UI).
     * If the dummyLaunch flag is set to true, it prints a message indicating that dummy data is loaded instead of loading the actual data from the database.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
/*        Webscraper.scrapeWebPage("https://www.hochschule-bochum.de/studium-lehre/studienangebote/masterstudiengaenge/");
        Webscraper.scrapeWebPage("https://www.hochschule-bochum.de/ba-studium/");*/
        PDFReader.analysePDFFiles();
        System.out.println(PDFReader.allModules.size());
        //lade Datenbank

        if (dummyLaunch) {
            System.out.println("Dummy data loaded");
        }
        else
            Database.getInstance().loadAllData();
        //starte UI
        MainUI.start(args);

    }


}
