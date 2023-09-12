package de.lisa.studiumsorganisation.webscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;

public class Webscraper {

    private static final Path DOWNLOAD_DIR = Paths.get("downloads");
    public static HashSet<String> studiengaenge = new HashSet<>();

    public static void scrapeWebPage(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element div = document.getElementById("kesearch_results");
            if (div != null) {
                Elements links = div.select("a[href]");
                for (Element link : links) {
                    System.out.println("Links: " + link.attr("abs:href"));
                    studiengaenge.add(link.attr("abs:href"));
                }
            } else {
                System.out.println("Div with id kesearch_results not found");
            }

            for (String studiengangUrl : studiengaenge) {
                Document studiengangDoc = Jsoup.connect(studiengangUrl).get();
                Elements pdfLinks = studiengangDoc.select("a[href]");
                for (Element pdfLink : pdfLinks) {
                    String pdfUrl = pdfLink.attr("abs:href");
                    String linkText = pdfLink.text().toLowerCase(Locale.ROOT);
                    if (pdfUrl.endsWith("pdf") && (linkText.contains("studienverlauf") || pdfUrl.contains("studienverlauf"))) {
                        downloadPdf(pdfUrl);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadPdf(String pdfUrl) throws IOException {
        URLConnection connection = new URL(pdfUrl).openConnection();
        String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
        Path file = DOWNLOAD_DIR.resolve(fileName);

        try (InputStream in = connection.getInputStream()) {
            Files.createDirectories(file.getParent());
            try {
                Files.copy(in, file);
                System.out.println("Downloaded file: " + file);
            } catch (IOException e) {
                System.out.println("Datei " + file + " existiert bereits.");
            }
        }
    }
}
