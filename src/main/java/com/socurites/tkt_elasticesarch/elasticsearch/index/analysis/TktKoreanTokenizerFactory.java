package com.socurites.tkt_elasticesarch.elasticsearch.index.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.settings.IndexSettings;

import com.socurites.tkt_elasticesarch.lucene.tokenizer.TktKoreanTokenizer;

/**
 * Elasticsearch Korean Tokenizer Factory using twitter-korean-text
 * 
 * @author socurites <socurites@gmail.com>
 *
 */
public class TktKoreanTokenizerFactory extends AbstractTokenizerFactory {
	/** whether to normalize text before tokenization. */
	private boolean disableNormalize = false;
	/** whether to stem text before tokenization. */
	private boolean disableStemmer = false;

	/**
	 * Creator.
	 * 
	 * @param index
	 * @param indexSettings
	 * @param env
	 * @param name
	 * @param settings
	 */
	@Inject
	public TktKoreanTokenizerFactory(Index index,
			@IndexSettings Settings indexSettings, Environment env,
			@Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
		
		configureTktTokenizer(env, settings);
	}

	/**
	 * Configure TktTokenizer.
	 * 
	 * @param env
	 * @param settings
	 */
	private void configureTktTokenizer(Environment env, Settings settings) {
		this.disableNormalize = settings.getAsBoolean("disableNormalize", false);
		this.disableStemmer = settings.getAsBoolean("disableStemmer", false);
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.index.analysis.TokenizerFactory#create(java.io.Reader)
	 */
	@Override
	public Tokenizer create(Reader reader) {
		Tokenizer tokenizer = new TktKoreanTokenizer(reader, this.disableNormalize, this.disableStemmer);

		return tokenizer;
	}
}
