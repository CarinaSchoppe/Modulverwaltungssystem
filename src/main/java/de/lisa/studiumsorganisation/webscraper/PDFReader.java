package de.lisa.studiumsorganisation.webscraper;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.util.Utility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class PDFReader {
    public static HashSet<String> allModules = new HashSet<>();

    public static void analysePDFFiles() {
        File folder = new File("downloads");
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                allModules.addAll(extractModuleNames(fileEntry));
            }

        }
        System.out.println("All module names:");
        for (String moduleName : allModules) {
            System.out.println(moduleName);
        }
    }

    public static List<String> extractModuleNames(File file) {
        List<String> moduleNames = new ArrayList<>();
        try {
            //create pdf document object from file
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            // Matches "CVH-* SPACE *Prof." and captures the *.
            Pattern pattern = Pattern.compile("(-|\\d)\\s(Pr\\(\\d\\)\\sT\\(\\d\\)|Pr\\(\\d\\)\\s-|-\\sT\\(\\d\\)|P\\(\\d\\)\\sT\\(\\d\\)|Pr\\(\\d\\)\\s-|-\\s-|Pr\\(\\d\\)\\s-|Pr\\(\\d\\)\\s-|Pr\\(\\d\\)\\s-)\\s([A-Za-zäöüß *]+)\\s\\d\\s(\\d+)");
            var matcher = pattern.matcher(text);
            while (matcher.find()) {
                String modulName = matcher.group(3).trim();
                String ects = matcher.group(4).trim();
                var modul = new Modul(Modul.getModulCounter(), modulName, false, 0);
                Utility.getInstance().getModule().add(modul);
                moduleNames.add(modulName);
                var Fach = new Fach(de.lisa.studiumsorganisation.model.Fach.getFachCounter(), modul.getName(), 0, false, Integer.parseInt(ects), modul.getID());
                Utility.getInstance().getFächer().add(Fach);

            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return moduleNames;
    }


}
