package pl.edu.agh.offerseeker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;

public class WebPagePuller implements IWebPagePuller {
    private static final int CONNECTION_RETRY_LIMIT = 5;
    private static final int CONNECTION_TIMEOUT = 3000;

    private InputStreamReader inputStream;

    @Override
    public Document pullPage(URL url) throws IOException {
        int retryCounter = 0;
        do {
            try {
                return Jsoup.parse(url, CONNECTION_TIMEOUT);
            } catch (SocketTimeoutException e) {
                System.out.println(retryCounter);
                if(retryCounter++ >= CONNECTION_RETRY_LIMIT) {
                    throw e;
                }
            }
        } while (true);
    }

}
