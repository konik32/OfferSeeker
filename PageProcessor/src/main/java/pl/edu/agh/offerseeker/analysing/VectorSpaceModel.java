package pl.edu.agh.offerseeker.analysing;

import pl.edu.agh.offerseeker.preprocessing.RemoveDoubledCharacters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bartQH on 2014-10-26.
 */
public class VectorSpaceModel {

    public static Map<String, Integer> TermFrequency(String document) {
        HashMap<String, Integer> frequency = new HashMap<String, Integer>();

        document = new RemoveDoubledCharacters(' ').preprocess((document));
        String[] words = document.split(" ");
        for (String word : words) {
            if (frequency.containsKey(word)) {
                frequency.put(word, frequency.get(word) + 1);
            } else {
                frequency.put(word, 1);
            }
        }

        return frequency;
    }

}
