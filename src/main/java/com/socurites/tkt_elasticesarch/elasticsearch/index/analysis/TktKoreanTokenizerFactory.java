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
	private boolean enableNormalize = true;
	/** whether to stem text before tokenization. */
	private boolean enableStemmer = true;
	/** whtere to enable phrase parsing. */
	private boolean enablePhrase = false;

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
		this.enableNormalize = settings.getAsBoolean("enableNormalize", true);
		this.enableStemmer = settings.getAsBoolean("enableStemmer", true);
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.index.analysis.TokenizerFactory#create(java.io.Reader)
	 */
	@Override
	public Tokenizer create(Reader reader) {
		Tokenizer tokenizer = new TktKoreanTokenizer(reader, this.enableNormalize, this.enableStemmer, this.enablePhrase);

		return tokenizer;
	}
}
