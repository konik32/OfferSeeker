package pl.edu.agh.offerseeker.service;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.agh.offerseeker.model.Offer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-11-08.
 */
public class SVMPageProcessorTest {

    private String expectedfDescription = "odstąpie tygodniowy darmowy pobyt coroczny na teneryfie w ekskluzywnym apartamencie w regency club. pobyt przypada zawsze w 42 tygodniu roku, ale istnieje mozliwość zamiany. apartament znajduje się w najpiękniejszej części teneryfy w los americas. istnieje mozliwość zamaiany tygodniowego pobytu z teneryfy na 2-tygodniowy darmowy pobyt w innych eksklizywnych ośrodkach na świecje np. polecam toskanię lub meksyk.przed ewentualną tranzakcją darmowy 7-dniowy pobyt na teneryfie";

    @Test
    public void processPageTest() {
        try {
            URL offerUrl = new URL("http://olx.pl/oferta/teneryfa-CID767-ID6blAD.html#5aca508353;promoted");
            IPageProcessor pageProcessor = new SVMPageProcessor();

            Offer testOffer = pageProcessor.processPage(offerUrl);
            String testDescription = testOffer.getDescription();
            System.out.println(testDescription);
            assertEquals(expectedfDescription, testDescription);
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
