package pl.edu.agh.offerseeker.crawler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.offerseeker.WebSpider;
import pl.edu.agh.offerseeker.WebSpider.VisitedUrlsDatabase;
import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.exceptions.CrawlerInternalException;

/**
 * Created by Krzysztof Balon on 2014-10-21.
 */
public class WebSpiderImplTest {

	private static final String ROOT_URL = "http://student.agh.edu.pl/~kostalko/WebSpiderTests";
	private static final String CRAWL_STORAGE_FOLDER_PREFIX = "crawldata";
	private static final int POLITENESS_DELAY = 200;
	private static final int MAX_PAGES_TO_FETCH = -1;
	private static final int NUMBER_OF_CRAWLERS = 7;

	private class CollectionVisitedUrlsDatabase implements VisitedUrlsDatabase {

		private Collection<PossibleOfferLink> visitedUrls = new LinkedList<PossibleOfferLink>();

		public void add(String url) throws MalformedURLException {
			visitedUrls.add(new PossibleOfferLink(url));
		}

		@Override
		public boolean isAlreadyVisited(PossibleOfferLink url) {
			return visitedUrls.contains(url);
		}

	}

	private class ExpectedUrls {

		private Set<PossibleOfferLink> expectedUrls = new HashSet<>();

		public void add(String url) throws MalformedURLException {
			expectedUrls.add(new PossibleOfferLink(url));
		}

		public Set<PossibleOfferLink> toSet() {
			return expectedUrls;
		}

	}

	private boolean deleteDirectoryWithContents(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectoryWithContents(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return directory.delete();
	}

	private boolean deleteDirectoryWithContents(String directoryPath) {
		return deleteDirectoryWithContents(new File(directoryPath));
	}

	@Test
	public void noLinksTest() throws MalformedURLException,
			CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_noLinksTest";
		String testRootUrl = ROOT_URL + "/noLinks";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void oneLinkTest() throws MalformedURLException,
			CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_oneLinkTest";
		String testRootUrl = ROOT_URL + "/oneLink";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void fetchedUrlsCountSmallerThanAvailableUrlsTest()
			throws MalformedURLException, CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_fetchedUrlsCountSmallerThanAvailableUrlsTest";
		String testRootUrl = ROOT_URL + "/fetchedUrlsCount1";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 3;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1.html");
		expected.add(testRootUrl + "/2.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void fetchedUrlsCountBiggerThanAvailableUrlsTest()
			throws MalformedURLException, CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_fetchedUrlsCountBiggerThanAvailableUrlsTest";
		String testRootUrl = ROOT_URL + "/fetchedUrlsCount2";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1.html");
		expected.add(testRootUrl + "/2.html");
		expected.add(testRootUrl + "/3.html");
		expected.add(testRootUrl + "/4.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void maxDepthOfCrawlingSmallerThanAvailableDepthTest()
			throws MalformedURLException, CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_maxDepthOfCrawlingSmallerThanAvailableDepthTest";
		String testRootUrl = ROOT_URL + "/maxDepthOfCrawling1";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = 2;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1.html");
		expected.add(testRootUrl + "/2.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void maxDepthOfCrawlingBiggerThanAvailableDepthTest()
			throws MalformedURLException, CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_maxDepthOfCrawlingBiggerThanAvailableDepthTest";
		String testRootUrl = ROOT_URL + "/maxDepthOfCrawling2";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1.html");
		expected.add(testRootUrl + "/2.html");
		expected.add(testRootUrl + "/3.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void alreadyVisitedUrlsTest() throws MalformedURLException,
			CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_alreadyVisitedUrlsTest";
		String testRootUrl = ROOT_URL + "/alreadyVisitedUrls";
		String seedUrl = testRootUrl + "/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 10;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();
		visited.add(testRootUrl + "/2b.html");
		visited.add(testRootUrl + "/3a.html");
		visited.add(testRootUrl + "/4a.html");
		visited.add(testRootUrl + "/4b.html");

		ExpectedUrls expected = new ExpectedUrls();
		expected.add(testRootUrl + "/");
		expected.add(testRootUrl + "/1a.html");
		expected.add(testRootUrl + "/1b.html");
		expected.add(testRootUrl + "/2a.html");
		expected.add(testRootUrl + "/3b.html");

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(expected.toSet(), actual);
	}

	@Test
	public void realOfferSiteFetchedUrlsCountMetTest()
			throws MalformedURLException, CrawlerInternalException {
		String crawlStorageFolder = CRAWL_STORAGE_FOLDER_PREFIX
				+ "_realOfferSiteFetchedUrlsCountMetTest";
		String seedUrl = "http://www.ebay.com/";
		int maxDepthOfCrawling = -1;
		int fetchedUrlsCount = 100;

		CollectionVisitedUrlsDatabase visited = new CollectionVisitedUrlsDatabase();

		// Run the test.
		deleteDirectoryWithContents(crawlStorageFolder);
		WebSpider webSpider = new WebSpiderImpl(crawlStorageFolder,
				POLITENESS_DELAY, maxDepthOfCrawling, MAX_PAGES_TO_FETCH,
				NUMBER_OF_CRAWLERS, visited);
		Set<PossibleOfferLink> actual = webSpider.crawl(new URL(seedUrl),
				fetchedUrlsCount);

		Assert.assertEquals(fetchedUrlsCount, actual.size());
	}

}
