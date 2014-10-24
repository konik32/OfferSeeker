package pl.edu.agh.offerseeker.preprocessing;

/**
 * Created by bartQH on 2014-10-23.
 */
public class RemoveAllSingleTags implements IPagePreprocessor {

    private StringBuilder builder;

    @Override
    public String preprocess(String page) {
        builder = new StringBuilder();
        boolean cloneContent = true;
        // copy all characters except text inside tags
        for (char c : page.toCharArray()) {
            cloneContent = cloneContent ? c != '<' : c == '>';
            if (cloneContent && c != '>')
                builder.append(c);
        }

        return builder.toString();
    }
}
