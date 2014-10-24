package pl.edu.agh.offerseeker.preprocessing;

import java.util.List;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveAllExceptBlock implements IPagePreprocessor {

    private String tagName;

    public RemoveAllExceptBlock(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String preprocess(String page) {
        String openTag = "<" + tagName;
        String closeTag = "</" + tagName + ">";
        List<Integer> openTags = Support.getAllOccurrencesOfString(page, openTag);
        List<Integer> closeTags = Support.getAllOccurrencesOfString(page, closeTag);

        StringBuilder builder = new StringBuilder();

        int index = 0;
        while (index < openTags.size()) {
            builder.append(page.substring(openTags.get(index), closeTags.get(index) + closeTag.length() + 1));
            index++;
        }

        return builder.toString();
    }

}
