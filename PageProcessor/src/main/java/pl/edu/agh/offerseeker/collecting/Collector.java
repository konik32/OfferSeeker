package pl.edu.agh.offerseeker.collecting;

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

    public Collector(String baseDir) {
        _baseDir = baseDir;
        _offerDir = _baseDir + "/db/offer/";
        _antiOfferDir = _baseDir + "/db/orig/";
    }

    /* Zapisuje linijke tekstu, ktora jest ogloszeniem o podanym UUID */
    public void collect(String content, UUID offerId) throws IOException {
        File dir = new File(_offerDir);
        dir.mkdirs();
        String path = _offerDir + offerId.toString();
        writeToFile(content, path);
    }

    /* Zapisuje linijke tekstu, ktora wydaje sie nie byc ogloszeniem */
    public void collect(String content) throws IOException {
        File dir = new File(_antiOfferDir);
        dir.mkdirs();
        int fileNum = dir.listFiles().length;
        String path = _antiOfferDir + Integer.toString(fileNum + 1);
        writeToFile(content, path);
    }

    /* Zapisuje pojedyncza linijke tekstu do pliku do miejsca o  podanej sciezce */
    private void writeToFile(String content, String path) throws IOException {
        PrintWriter writer = new PrintWriter(path + ".dat", "UTF-8");
        writer.write(content);
        writer.close();
    }

}
