package pl.edu.agh.offerseeker.preprocessing;

import java.util.List;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveSingleTags implements IPagePreprocessor {
    private String tagName;

    public RemoveSingleTags(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String preprocess(String page) {
        StringBuilder builder = new StringBuilder();
        List<Integer> indices = Support.getAllOccurrencesOfString(page, "<" + tagName);
        int tagIndex = 0;
        int i = 0;
        while (tagIndex < indices.size()) {
            while (i != indices.get(tagIndex))
                builder.append(page.charAt(i++));
            while (page.charAt(i) != '>')
                i++;
            i++;
            tagIndex++;
        }

        for (int j = i; j < page.length(); j++)
            builder.append(page.charAt(j));

        return builder.toString();
    }
}
