package pl.edu.agh.offerseeker.analysing;

/**
 * Created by bartQH on 2014-10-26.
 */
public class VectorSpaceModelTest {
    /*@Test
    public void TermFrequencyTest() {
        String testString = "this is test this test test";
        String expectedString = "is:1 test:3 this:2 ";
        String actualString = "";

        Map<String, Integer> frequency = VectorSpaceModel.TermFrequency(testString);

        for (String key : frequency.keySet()) {
            actualString += key + ":" + frequency.get((key)) + " ";
        }

        assertEquals(expectedString, actualString);
    }

    @Test
    public void InverseDocumentFrequencyTest() {
        List<Map<String, Integer>> documents = new LinkedList<Map<String, Integer>>();
        Map<String, Integer> d1 = new HashMap<String, Integer>();
        d1.put("this", 1);
        d1.put("is", 1);
        d1.put("a", 2);
        d1.put("sample", 1);
        documents.add(d1);
        Map<String, Integer> d2 = new HashMap<String, Integer>();
        d2.put("this", 1);
        d2.put("is", 1);
        d2.put("another", 2);
        d2.put("example", 3);
        documents.add(d2);

        assertEquals(0.0, VectorSpaceModel.InverseDocumentFrequency("this", documents), 0.0001);
        assertEquals(0.3010, VectorSpaceModel.InverseDocumentFrequency("example", documents), 0.0001);

        double tf_idf = d2.get("example") * VectorSpaceModel.InverseDocumentFrequency("example", documents);
        assertEquals(0.9030, tf_idf, 0.0001);
    }
*/
}
