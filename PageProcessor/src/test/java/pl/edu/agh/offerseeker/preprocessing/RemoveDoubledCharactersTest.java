package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveDoubledCharactersTest {

    @Test
    public void preprocessTest() {
        String testPage = "<p>aaa            aa a  aaa";
        RemoveDoubledCharacters rem = new RemoveDoubledCharacters(' ');
        String actualPage = rem.preprocess(testPage);
        String expectedPage = "<p>aaa aa a aaa";
    }

}
