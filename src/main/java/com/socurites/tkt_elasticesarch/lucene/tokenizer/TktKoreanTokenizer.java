package com.socurites.tkt_elasticesarch.lucene.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeFactory;

import scala.collection.Seq;

import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;

/**
 * Lucene Korean Tokenizer using twitter-korean-text
 * 
 * @author socurites <socurites@gmail.com>
 *
 */
public class TktKoreanTokenizer extends Tokenizer {
	/** whether input stream was read as a string. */
	private boolean isInputRead = false;
	/** current index of token buffers. */
	private int tokenIndex = 0;
	/** token buffers. */
	List<KoreanTokenJava> tokenBuffer = null;

	/** whether to normalize text before tokenization. */
	private boolean enableNormalize = true;
	/** whether to stem text before tokenization. */
	private boolean enableStemmer = true;

	private CharTermAttribute charTermAttribute = null;
	private OffsetAttribute offsetAttribute = null;
	private TypeAttribute typeAttribute = null;
	
	public TktKoreanTokenizer(Reader input) {
		this(input, false, false);
	}

	/**
	 * Constructor
	 * 
	 * @param input
	 */
	public TktKoreanTokenizer(Reader input, boolean disableNormalize, boolean disableStemmer) {
		super(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, input);
		
		this.enableNormalize = disableNormalize;
		this.enableStemmer = disableStemmer;
		
		initAttributes();
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public final boolean incrementToken() throws IOException {
		clearAttributes();

		if (this.isInputRead == false) {
			this.isInputRead = true;
			CharSequence text = readText();
			Seq<KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(text);
			
			if ( this.enableStemmer ) {
				tokens  = TwitterKoreanProcessorJava.stem(tokens);
			}
			
			this.tokenBuffer = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(tokens);
		}
		
		if (this.tokenBuffer == null || this.tokenBuffer.isEmpty() || tokenIndex >= this.tokenBuffer.size() ) {
			return false;
		}

		setAttributes(this.tokenBuffer.get(tokenIndex++));

		return true;
	}

	/**
	 * Add attributes
	 * 
	 */
	private void initAttributes() {
		this.charTermAttribute = addAttribute(CharTermAttribute.class);
		this.offsetAttribute = addAttribute(OffsetAttribute.class);
		this.typeAttribute = addAttribute(TypeAttribute.class);
	}
	
	/**
	 * Set attributes
	 * 
	 * @param token
	 */
	private void setAttributes(KoreanTokenJava token) {
		charTermAttribute.append(token.getText());
		offsetAttribute.setOffset(token.getOffset(), token.getOffset() + token.getLength());
		typeAttribute.setType(token.getPos().toString());
		
	}

	/**
	 * Read string from input reader.
	 * 
	 * @return
	 * @throws IOException
	 */
	private CharSequence readText() throws IOException {
		StringBuilder text = new StringBuilder();
		char[] tmp = new char[1024];
		int len = -1;
		while ((len = input.read(tmp)) != -1) {
			text.append(new String(tmp, 0, len));
		}
		
		if ( this.enableNormalize ) {
			return TwitterKoreanProcessorJava.normalize(text.toString());
		} else {
			return text.toString();
		}
		
	}

	/**
	 * Initailze states.
	 */
	private void initializeState() {
		this.isInputRead = false;
		this.tokenIndex = 0;
		this.tokenBuffer = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.lucene.analysis.Tokenizer#close()
	 */
	@Override
	public void close() throws IOException {
		super.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.lucene.analysis.Tokenizer#reset()
	 */
	@Override
	public void reset() throws IOException {
		super.reset();

		initializeState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.lucene.analysis.TokenStream#end()
	 */
	@Override
	public void end() throws IOException {
		super.end();
	}
}
