package pl.edu.agh.offerseeker.service;

import libsvm.svm_problem;
import org.jsoup.nodes.Document;
import pl.edu.agh.offerseeker.WebPagePuller;
import pl.edu.agh.offerseeker.analysing.SVM;
import pl.edu.agh.offerseeker.analysing.VSM;
import pl.edu.agh.offerseeker.collecting.Collector;
import pl.edu.agh.offerseeker.collecting.Provider;
import pl.edu.agh.offerseeker.model.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;
import pl.edu.agh.offerseeker.preprocessing.ContentInSeparateLines;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SVMPageProcessor implements IPageProcessor {

	private Provider provider;
	private List<Map<String, Integer>> docs;
	private Map<String, Integer> docsStats;
	private VSM vsm;
	private Collector collector;

	public SVMPageProcessor(String dbFilesPath) throws IOException {
		provider = new Provider(dbFilesPath);
		docs = provider.getDocs();
		docsStats = provider.getDocsStats();
		vsm = new VSM(provider);
		collector = new Collector(dbFilesPath);
	}

	public SVMPageProcessor() throws IOException {
		this(System.getProperty("user.home") +"/offer_seeker");
	}

	public static void main(String[] args) throws IOException {
		SVMPageProcessor p = new SVMPageProcessor();
		URL url = new URL("http://olx.pl/oferta/krociec-paliwa-fiat-126p-CID5-ID7TbUx.html");
		System.out.println("GUM: " + new GumtreePageProcessor().processPage(url).getDescription());
		Offer offer = p.processPage(url);
		if (offer == null)
			System.out.println("Nie znaleziono!");
		else {
			System.out.println("SVM: " + offer.getDescription() + " << " + offer.getId());
			p.evaluateOffer(offer.getId(), OfferEvaluation.GOOD);
		}
	}

	@Override
	public Offer processPage(URL url) throws IOException {
		List<List<Double>> offerFeatures = vsm.getOfferFeatures();
		List<List<Double>> antiFeatures = vsm.getAntiFeatures();

		List<List<Double>> features = new ArrayList<List<Double>>();
		List<Double> classes = new ArrayList<Double>();

		for (List<Double> curr : offerFeatures) {
			classes.add(-1.0);
			features.add(curr);
		}

		for (List<Double> curr : antiFeatures) {
			classes.add(1.0);
			features.add(curr);
		}

		SVM svm = new SVM();
		svm_problem prob = svm.getProblem(features, classes);

		svm.train(prob);

		WebPagePuller puller = new WebPagePuller();
		Document txt = puller.pullPage(url);
		String page = new ContentInSeparateLines(txt).preprocess();

		String offerSupp = "";
		String offerDescription = "";
		for (String curr : page.split("\n")) {
			if (offerSupp.length() < curr.length()) {
				if (!provider.isKnown(curr)) {
					offerSupp = curr;
				}
			}
			if (svm.predict(vsm.getFeature(curr)) == -1.0) {
				if (!provider.isKnown(curr)) {
					Offer offer = new Offer(curr);
					collector.collect(txt);
					collector.collect(curr, offer.getId());
					return offer;
				}
			}
		}

		if (offerSupp != "") {
			Offer offer = new Offer(offerSupp);
			collector.collect(txt);
			collector.collect(offerSupp, offer.getId());
			return offer;
		}

		return null;
	}

	@Override
	public void evaluateOffer(UUID offerID, OfferEvaluation evaluation) {
		if (evaluation == OfferEvaluation.BAD) {
			collector.moveToAnti(offerID);
		}
	}

}
