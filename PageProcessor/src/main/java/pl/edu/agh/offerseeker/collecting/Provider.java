package pl.edu.agh.offerseeker.collecting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* Zadaniem jest dostarczanie danych o bazie tesktow */
public class Provider {

    /* Pola prywatne */
    private String _baseDir;
    private String _offerDir;
    private String _antiOfferDir;

    private Map<String, Integer> _docsStats;
    private List<Map<String, Integer>> _docs;

    public Provider(String baseDir) throws IOException {
        _baseDir = baseDir;
        new File(_baseDir + "/db/").mkdirs();
        new File(_baseDir + "/db/offer").mkdirs();
        new File(_baseDir + "/db/anti").mkdirs();
        _offerDir = _baseDir + "/db/offer/";
        _antiOfferDir = _baseDir + "/db/anti/";
    }

    /* Pobranie rozkladu wyrazow ze wszystkich dokumentow */
    public Map<String, Integer> getDocsStats() throws IOException {
        if (_docsStats == null)
            discover();
        return _docsStats;
    }

    /* Pobranie rozkladu wyrazow w poszczegolnych dokumentach */
    public List<Map<String, Integer>> getDocs() throws IOException {
        if (_docs == null)
            discover();
        return _docs;
    }


    /* Zliczenie wszystkich slow we wszystkich plikach i rozdzielenie ich na poszczegolne dokumenty*/
    private void discover() throws IOException {
        _docsStats = new TreeMap<>();
        _docs = new ArrayList<>();

        List<String> paths = getAllPaths(_offerDir);
        paths.addAll(getAllPaths(_antiOfferDir));

        for (String path : paths) {
            Map<String, Integer> doc = new TreeMap<>();
            for (String word : readFile(path).split(" ")) {
                if (doc.containsKey(word)) {
                    doc.put(word, doc.get(word) + 1);
                    _docsStats.put(word, _docsStats.get(word) + 1);
                } else {
                    doc.put(word, 1);
                    _docsStats.put(word, 1);
                }
            }
            _docs.add(doc);
        }
    }

    public boolean isKnown(String processedText) throws IOException {
        int x = 0;
        for (String path : getAntiPaths()) {
            String content = readFile(path);
            if (content.equals(processedText))
                return true;
        }

        return false;
    }

    public List<String> getOfferPaths() {
        return getAllPaths(_offerDir);
    }

    public List<String> getAntiPaths() {
        return getAllPaths(_antiOfferDir);
    }

    /* Pobiera wszystkie sciezki do plikow istniejacych w folderze */
    private List<String> getAllPaths(String dir) {
        List<String> allPaths = new ArrayList<>();
        for (File file : new File(dir).listFiles())
            allPaths.add(dir + file.getName());
        return allPaths;
    }

    /* Wczytuje caly tekst zawarty w pliku i zamienia go na string */
    public String readFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }


}
