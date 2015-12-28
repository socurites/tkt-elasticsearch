package com.melon.elasticesarch.korean.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.bitbucket.eunjeon.mecab_ko_lucene_analyzer.Pos;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;


public class MelonEsKoreanTokenizer extends Tokenizer {
	private TwitterKoreanProcessorJava processor = null;
	private CharTermAttribute charTermAtt = null;
	
	private boolean isDocumentLoaded = false;
	
	List<KoreanToken> tokens = null;
	

	protected MelonEsKoreanTokenizer(Reader input) {
		super(input);
		
		processor = new TwitterKoreanProcessorJava.Builder().build();
		charTermAtt = addAttribute(CharTermAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		if ( this.isDocumentLoaded ) {
			this.isDocumentLoaded = true;
			this.tokens = this.processor.tokenize(getDocument());
		}
		
		if ( this.tokens == null || this.tokens.isEmpty() ) {
			return false;
		}
		
//		setAttributes(this.tokens.get(index));
		
		return true;
	}
	
//	  private void setAttributes(List<KoreanToken> token) {
//		    posIncrAtt.setPositionIncrement(token.getPositionIncr());
//		    posLenAtt.setPositionLength(token.getPositionLength());
//		    offsetAtt.setOffset(
//		        correctOffset(token.getStartOffset()),
//		        correctOffset(token.getEndOffset()));
//		    charTermAtt.copyBuffer(
//		        token.getSurface().toCharArray(), 0, token.getSurfaceLength());
//		    typeAtt.setType(token.getPosId().toString());
//		    posAtt.setPartOfSpeech(token.getMophemes());
//		    semanticClassAtt.setSemanticClass(token.getSemanticClass());
//		  }
	
	private String getDocument() throws IOException {
		StringBuilder document = new StringBuilder();
	    char[] tmp = new char[1024];
	    int len = -1;
	    while ((len = input.read(tmp)) != -1) {
	      document.append(new String(tmp, 0, len));
	    }
	    return document.toString();
	}

}
