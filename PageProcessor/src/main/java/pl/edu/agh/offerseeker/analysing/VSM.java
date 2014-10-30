package pl.edu.agh.offerseeker.analysing;

import pl.edu.agh.offerseeker.collecting.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* Implementacja VectorSpaceModel */
public class VSM {

    /* Pola prywatne */
    private Provider _provider;
    private Map<String, Integer> _docsStats;
    private List<Map<String, Integer>> _docs;

    public VSM(Provider provider) throws IOException {
        _provider = provider;
        _docsStats = _provider.getDocsStats();
        _docs = _provider.getDocs();
    }

    /* Ma za zadanie pobrac cechy podanego tekstu na podstawie bazy */
    public List<Double> getFeature(String text) {
        Map<String, Double> featureMap = new TreeMap<>();
        for (String key : _docsStats.keySet())
            featureMap.put(key, 0.0);

        for (String word : text.split(" ")) {
            if (_docsStats.containsKey(word))
                featureMap.put(word, inverseDocumentFrequency(word));
        }

        List<Double> feature = new ArrayList<Double>();
        for (Double val : featureMap.values())
            feature.add(val);

        return feature;
    }

    /* Pobranie odwrotnej czestotliwosci wystepowania danego wyrazu we wszystich dokumentach */
    private double inverseDocumentFrequency(String word) {
        int docsNum = _docs.size();
        int wordFreq = 0;

        for (Map<String, Integer> doc : _docs) {
            if (doc.containsKey(word))
                wordFreq++;
        }

        return Math.log10(docsNum / wordFreq);
    }

}
