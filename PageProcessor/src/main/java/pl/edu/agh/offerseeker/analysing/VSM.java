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

        String[] words = text.split(" ");

        Map<String, Integer> tf = new TreeMap<String, Integer>();
        for (String word : words) {
            if (tf.containsKey(word))
                tf.put(word, tf.get(word) + 1);
            else
                tf.put(word, 1);
        }


        double max = -1;
        for (String word : words) {
            if (_docsStats.containsKey(word)) {
                double val = tf.get(word) * inverseDocumentFrequency(word);
                if (val > max)
                    max = val;
            }
        }

        max = max == 0 ? 1 : max;

        for (String word : words) {
            if (_docsStats.containsKey(word))
                featureMap.put(word, tf.get(word) * inverseDocumentFrequency(word) / max);
        }

        List<Double> feature = new ArrayList<Double>();
        for (Double val : featureMap.values())
            feature.add(val);

        return feature;
    }

    public List<List<Double>> getOfferFeatures() throws IOException {
        List<List<Double>> features = new ArrayList<List<Double>>();

        List<String> paths = _provider.getOfferPaths();
        for (String path : paths) {
            String content = _provider.readFile(path);
            features.add(getFeature(content));
        }

        return features;
    }

    public List<List<Double>> getAntiFeatures() throws IOException {
        List<List<Double>> features = new ArrayList<List<Double>>();

        List<String> paths = _provider.getAntiPaths();
        for (String path : paths) {
            String content = _provider.readFile(path);
            features.add(getFeature(content));
        }

        return features;
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
