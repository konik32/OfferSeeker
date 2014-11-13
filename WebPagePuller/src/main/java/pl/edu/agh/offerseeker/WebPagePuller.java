package pl.edu.agh.offerseeker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebPagePuller implements IWebPagePuller {

    private InputStreamReader inputStream;

    @Override
    public Document pullPage(URL url) throws IOException {
        return Jsoup.parse(url, 1000);
    }

}
