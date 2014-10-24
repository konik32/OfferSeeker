package pl.edu.agh.offerseeker.preprocessing;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bartQH on 2014-10-24.
 */
public class Support {
    public static List<Integer> getAllOccurrencesOfString(String page, String text) {
        List<Integer> indices = new LinkedList<Integer>();
        int index = page.indexOf(text);
        while (index >= 0) {
            indices.add(index);
            index = page.indexOf(text, index + 1);
        }
        return indices;
    }
}
