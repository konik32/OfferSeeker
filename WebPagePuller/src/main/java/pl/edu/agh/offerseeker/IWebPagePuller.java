package pl.edu.agh.offerseeker;

import java.io.IOException;
import java.net.URL;

/**
 * Created by bartQH on 2014-10-22.
 */
public interface IWebPagePuller {
    /**
     * @param url of page to be pulled
     * @return pulled page converted to string
     */
    String pullPage(URL url) throws IOException;
}
