package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 */

import java.util.ArrayList;

public class Word {

	private String wordText;
	private int sentencePos;
	private ArrayList<Double> wordImportanceInEachSentence;
	private ArrayList<Integer> appearInEachSentence;
	
	Word(){
		wordText = "";
		sentencePos = -1;
		wordImportanceInEachSentence = new ArrayList<Double>();
		appearInEachSentence = new ArrayList<Integer>();
	}
	
	Word(String _wordText, int _sentencePos){
		wordText = _wordText;
		sentencePos = _sentencePos;
		wordImportanceInEachSentence = new ArrayList<Double>();
		appearInEachSentence = new ArrayList<Integer>();
	}
	
	void setWordText(String _wordText) {
		wordText = _wordText;
	}
	
	void setSentencePos(int _sentencePos) {
		sentencePos = _sentencePos;
	}
	
	void setAppearInEachSentence(int _count) {
		appearInEachSentence.add(_count);
	}
	
	void setWordImportanceInEachSentence(double importance) {
		wordImportanceInEachSentence.add(importance);
	}
	
	String getWordText() {
		return wordText;
	}
	
	int getSentencePos() {
		return sentencePos;
	}
	
	ArrayList<Double> getWordImportanceInEachSentence(){
		return wordImportanceInEachSentence;
	}
	
	ArrayList<Integer> getAppearInEachSentence(){
		return appearInEachSentence;
	}
}
