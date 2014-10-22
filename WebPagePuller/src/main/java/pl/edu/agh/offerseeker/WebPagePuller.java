package pl.edu.agh.offerseeker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebPagePuller implements IWebPagePuller {

    private InputStreamReader inputStream;

    @Override
    public String pullPage(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        inputStream = new InputStreamReader(conn.getInputStream());
        return inputStreamToString(inputStream);
    }

    /**
     * @param inputStream Stream that will be converted to plain String
     * @return Char's from stream in plain String
     */
    private String inputStreamToString(InputStreamReader inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(inputStream);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

}
