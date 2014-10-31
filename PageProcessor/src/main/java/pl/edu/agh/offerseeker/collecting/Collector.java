package pl.edu.agh.offerseeker.collecting;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/* Klasa zarzadzajaca zbieraniem tekstu, zapisywaniem go i przydzielaniem do odpowiednich grup */
public class Collector {

    /* Pola prywatne */
    private String _baseDir;
    private String _offerDir;
    private String _antiOfferDir;
    private String _origDir;

    public Collector(String baseDir) {
        _baseDir = baseDir;
        _offerDir = _baseDir + "/db/offer/";
        _antiOfferDir = _baseDir + "/db/anti/";
        _origDir = _baseDir + "/db/orig/";
    }

    /* Zapisuje pojedyncza linijke tekstu do pliku do miejsca o  podanej sciezce */
    public static void writeToFile(String content, String path) throws IOException {
        PrintWriter writer = new PrintWriter(path + ".dat", "UTF-8");
        writer.write(content);
        writer.close();
    }

    /* Zapisuje linijke tekstu, ktora jest ogloszeniem o podanym UUID */
    public void collect(String content, UUID offerId) throws IOException {
        File dir = new File(_offerDir);
        dir.mkdirs();
        String path = _offerDir + offerId.toString();
        writeToFile(content, path);
    }

    public void moveToAnti(UUID offerId) {
        String path = _offerDir + "/" + offerId.toString() + ".dat";
        try {
            File file = new File(path);
            file.renameTo(new File(_antiOfferDir + "/" + offerId.toString() + ".dat"));
        } catch (Exception e) {
            System.err.println("File does not exists");
        }
    }

    /* Zapisuje linijke tekstu, ktora wydaje sie nie byc ogloszeniem */
    public void collect(String content) throws IOException {
        File dir = new File(_antiOfferDir);
        dir.mkdirs();
        int fileNum = dir.listFiles().length;
        String path = _antiOfferDir + Integer.toString(fileNum + 1);
        writeToFile(content, path);
    }

    /* Zapisuje całą stronę do pliku */
    public void collect(Document page) throws IOException {
        File dir = new File(_origDir);
        dir.mkdirs();
        int fileNum = dir.listFiles().length;
        String path = _origDir + Integer.toString(fileNum + 1);
        writeToFile(page.toString(), path);
    }

}
