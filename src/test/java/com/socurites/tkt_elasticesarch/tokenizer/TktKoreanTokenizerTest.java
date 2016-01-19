package com.socurites.tkt_elasticesarch.tokenizer;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;

import com.socurites.tkt_elasticesarch.lucene.tokenizer.TktKoreanTokenizer;

public class TktKoreanTokenizerTest {
	private String TEST_LINE = "한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ";
	
	private Tokenizer createTokenizer(StringReader reader, boolean enableNormalize, boolean enableStemmer, boolean enablePhrase) {
		Tokenizer tokenizer = new TktKoreanTokenizer(reader, enableNormalize, enableStemmer, enablePhrase);
		
		return tokenizer;
	}
	
	@Test
	public void tokenize_disableNormalize_disableStemmer() throws Exception {
		Tokenizer tokenizer = createTokenizer(new StringReader(TEST_LINE), false, false, false);
		
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttribute = tokenizer.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAttribute = tokenizer.addAttribute(OffsetAttribute.class);
		
		StringBuilder result = new StringBuilder();
		tokenizer.reset();
		while ( tokenizer.incrementToken() == true ) {
			result.append(new String(charTermAttribute.buffer(), 0, charTermAttribute.length()) + "/");
			result.append(typeAttribute.type() + "/");
			result.append(offsetAttribute.startOffset() + ":");
			result.append(offsetAttribute.endOffset());
			result.append(", ");
		}
		tokenizer.end();
		
		System.out.println(result.toString());
	}
	
	@Test
	public void tokenize_enableNormalize_disableStemmer() throws Exception {
		Tokenizer tokenizer = createTokenizer(new StringReader(TEST_LINE), true, false, false);
		
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttribute = tokenizer.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAttribute = tokenizer.addAttribute(OffsetAttribute.class);
		
		StringBuilder result = new StringBuilder();
		tokenizer.reset();
		while ( tokenizer.incrementToken() == true ) {
			result.append(new String(charTermAttribute.buffer(), 0, charTermAttribute.length()) + "/");
			result.append(typeAttribute.type() + "/");
			result.append(offsetAttribute.startOffset() + ":");
			result.append(offsetAttribute.endOffset());
			result.append(", ");
		}
		tokenizer.end();
		
		System.out.println(result.toString());
	}
	
	@Test
	public void tokenize_disableNormalize_enableStemmer() throws Exception {
		Tokenizer tokenizer = createTokenizer(new StringReader(TEST_LINE), false, true, false);
		
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttribute = tokenizer.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAttribute = tokenizer.addAttribute(OffsetAttribute.class);
		
		StringBuilder result = new StringBuilder();
		tokenizer.reset();
		while ( tokenizer.incrementToken() == true ) {
			result.append(new String(charTermAttribute.buffer(), 0, charTermAttribute.length()) + "/");
			result.append(typeAttribute.type() + "/");
			result.append(offsetAttribute.startOffset() + ":");
			result.append(offsetAttribute.endOffset());
			result.append(", ");
		}
		tokenizer.end();
		
		System.out.println(result.toString());
	}
	
	@Test
	public void tokenize_enableNormalize_enableStemmer() throws Exception {
		Tokenizer tokenizer = createTokenizer(new StringReader(TEST_LINE), true, true, false);
		
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttribute = tokenizer.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAttribute = tokenizer.addAttribute(OffsetAttribute.class);
		
		StringBuilder result = new StringBuilder();
		tokenizer.reset();
		while ( tokenizer.incrementToken() == true ) {
			result.append(new String(charTermAttribute.buffer(), 0, charTermAttribute.length()) + "/");
			result.append(typeAttribute.type() + "/");
			result.append(offsetAttribute.startOffset() + ":");
			result.append(offsetAttribute.endOffset());
			result.append(", ");
		}
		tokenizer.end();
		
		System.out.println(result.toString());
	}
	
	@Test
	public void tokenize_enableNormalize_enableStemmer_enablePharse() throws Exception {
		Tokenizer tokenizer = createTokenizer(new StringReader(TEST_LINE), true, true, true);
		
		CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttribute = tokenizer.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAttribute = tokenizer.addAttribute(OffsetAttribute.class);
		
		StringBuilder result = new StringBuilder();
		tokenizer.reset();
		while ( tokenizer.incrementToken() == true ) {
			result.append(new String(charTermAttribute.buffer(), 0, charTermAttribute.length()) + "/");
			result.append(typeAttribute.type() + "/");
			result.append(offsetAttribute.startOffset() + ":");
			result.append(offsetAttribute.endOffset());
			result.append(", ");
		}
		tokenizer.end();
		
		System.out.println(result.toString());
	}
}
