package com.socurites.tkt_elasticesarch.lucene.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeFactory;

import com.twitter.penguin.korean.TwitterKoreanProcessor.KoreanSegment;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;

/**
 * Lucene Korean Tokenizer using twitter-korean-text
 * 
 * @author socurites <socurites@gmail.com>
 *
 */
public class TktKoreanTokenizer extends Tokenizer {
	/** Java wrapper for TwitterKoreanProcessor. */
	private TwitterKoreanProcessorJava processor = null;

	/** whether input stream was read as a string. */
	private boolean isInputRead = false;
	/** current index of segment buffers. */
	private int segmentIndex = 0;
	/** segment buffers. */
	List<KoreanSegment> segments = null;

	/** whether to normalize text before tokenization. */
	private boolean disableNormalize = false;
	/** whether to stem text before tokenization. */
	private boolean disableStemmer = false;

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
		
		this.disableNormalize = disableNormalize;
		this.disableStemmer = disableStemmer;
		
		this.processor = new TwitterKoreanProcessorJava.Builder().build();
		
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
			System.out.println(text);
			this.segments = this.processor.tokenizeWithIndex(text);
		}

		if (this.segments == null || this.segments.isEmpty() || segmentIndex >= this.segments.size() ) {
			return false;
		}

		setAttributes(this.segments.get(segmentIndex++));

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
	private void setAttributes(KoreanSegment segment) {
		charTermAttribute.copyBuffer(segment.token().text().toCharArray(), 0, segment.length());
		offsetAttribute.setOffset(segment.start(), segment.start() + segment.length());
		typeAttribute.setType(segment.token().pos().toString());
		
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
		
		if ( this.disableNormalize && this.disableStemmer ) {
			System.out.println(1);
			return text.toString();
		} else if ( !this.disableNormalize && this.disableStemmer ) {
			System.out.println(2);
			return processor.normalize(text.toString());
		} else if ( this.disableNormalize && !this.disableStemmer ) {
			System.out.println(3);
			return processor.stem(text.toString()).text();
		} else {
			System.out.println(4);
			return processor.stem(processor.normalize(text.toString())).text();
		}
		
	}

	/**
	 * Initailze states.
	 */
	private void initializeState() {
		this.isInputRead = false;
		this.segmentIndex = 0;
		this.segments = null;
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
