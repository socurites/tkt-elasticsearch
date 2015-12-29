package com.socurites.tkt_elasticesarch.elasticsearch.plugin.analysis;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

import com.socurites.tkt_elasticesarch.elasticsearch.index.analysis.TktKoreanTokenizerFactory;

/**
 * Elasticsearch Korean Tokenizer Plugin using twitter-korean-text
 * 
 * @author socurites <socurites@gmail.com>
 *
 */
public class TktKoreanTokenizerPlugin extends AbstractPlugin {
	@Override
	public String name() {
		return "tkt-korean-tokenizer";
	}

	@Override
	public String description() {
		return "tkt-korean-tokenizer based on twitter-korean-text";
	}

	public void onModule(AnalysisModule module) {
		module.addTokenizer("tkt-korean-tokenizer",	TktKoreanTokenizerFactory.class);
	}
}
