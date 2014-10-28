package pl.edu.agh.offerseeker.preprocessing;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveDoubledCharacters implements IPagePreprocessor {
    private char doubledChar;

    public RemoveDoubledCharacters(char doubledChar) {
        this.doubledChar = doubledChar;
    }

    @Override
    public String preprocess(String page) {
        StringBuilder builder = new StringBuilder();
        boolean doubled = false;
        for (int i = 0; i < page.length(); i++) {
            if (doubledChar == page.charAt(i)) {
                if (!doubled) {
                    doubled = true;
                    builder.append(page.charAt(i));
                }
            } else {
                doubled = false;
                builder.append(page.charAt(i));
            }
        }

        return builder.toString();
    }
}
