package pl.edu.agh.offerseeker.preprocessing;

import java.util.List;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveAllBlocks implements IPagePreprocessor {

    private String tagName;

    public RemoveAllBlocks(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String preprocess(String page) {
        String openTag = "<" + tagName;
        String closeTag = "</" + tagName + ">";
        List<Integer> openTags = Support.getAllOccurrencesOfString(page, openTag);
        List<Integer> closeTags = Support.getAllOccurrencesOfString(page, closeTag);

        StringBuilder builder = new StringBuilder();

        int tagIndex = 0;
        for (int i = 0; i < page.length(); i++) {
            if (i == closeTags.get(tagIndex) + closeTag.length()) {
                if (tagIndex < openTags.size() - 1)
                    tagIndex++;
            }
            if (i < openTags.get(tagIndex) || i >= closeTags.get(tagIndex) + closeTag.length())
                builder.append(page.charAt(i));
        }

        return builder.toString();
    }
}
