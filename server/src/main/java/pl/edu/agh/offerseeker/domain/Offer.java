package pl.edu.agh.offerseeker.domain;

import java.net.URL;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.solr.analysis.HunspellStemFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StempelPolishStemFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import pl.edu.agh.offerseeker.commons.model.AbstractPersistable;

/**
 * @author Szymon Konicki
 *
 */
@Entity
@Table(name = "offers")
@Indexed
@AnalyzerDef(name = "polishAnalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = { @TokenFilterDef(factory = HunspellStemFilterFactory.class, params = {
		@Parameter(name = "affix", value = "pl_PL.aff"), @Parameter(name = "dictionary", value = "pl_PL.dic"), @Parameter(name = "ignoreCase", value = "true") }) })
public class Offer extends AbstractPersistable<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1572099896813035456L;

	@Id
	private UUID id;

	@Column(length = 16384)
	@Field
	@Analyzer(definition = "polishAnalyzer")
	private String description;

	private URL url;

	public Offer(UUID id, String description, URL url) {
		super();
		this.id = id;
		this.description = description;
	}

	public Offer() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public boolean isNew() {
		return true;
	}

}
