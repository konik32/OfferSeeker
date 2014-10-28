package pl.edu.agh.offerseeker.service;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.agh.offerseeker.model.Offer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class GumtreePageProcessorTest {

    private String refDescription = "Maleństwa z ulicy pilnie szukają DT lub DS! PILNE! Na podwórku nie mają szans! <br/>Uratujcie je przed zimą! Maluszki mają około 3 miesiące, mają przed sobą całe życie, potrzebują jedynie pomocy! Kociaki znajdują się w Łomiankach. tel:796096481";

    @Test
    public void processPageTest() {
        try {
            URL offerUrl = new URL("http://www.gumtree.pl/cp-koty-i-kocieta/praga-poludnie/puchate-malenstwa-z-ulicy-do-pilnej-adopcji-uratujcie-je-612845532");
            IPageProcessor pageProcessor = new GumtreePageProcessor();

            Offer testOffer = pageProcessor.processPage(offerUrl);
            String testDescription = testOffer.getDescription();
            
            assertEquals(refDescription, testDescription);
            

        } catch (MalformedURLException mExcept) {
            Assert.fail("MalformedURLException has occurred!");
        } catch (IOException ioExcept) {
            Assert.fail("Failed to get page content");
        } catch (Exception eExcept) {
            Assert.fail("Unexpected exception! \n\tMore info: "
                    + eExcept.getMessage());
        }

    }

}