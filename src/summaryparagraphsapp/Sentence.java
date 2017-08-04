package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 */

import java.util.ArrayList;

public class Sentence {
	private String sentenceText;
	private int sentencePos;
	private ArrayList<Word> wordsInSentence;
	private double sentenceImportance;
	
	Sentence(){
		sentenceText = "";
		sentencePos = -1;
		wordsInSentence = new ArrayList<Word>();
		sentenceImportance = 0;
	}
	
	Sentence(String _sentenceText, int _sentencePos){
		sentenceText = _sentenceText;
		sentencePos = _sentencePos;
		wordsInSentence = new ArrayList<Word>();
		sentenceImportance = 0;
	}
	
	void setSentenceText(String _sentenceText) {
		sentenceText = _sentenceText;
	}
	
	void setSentencePos (int _sentencePos) {
		sentencePos = _sentencePos;
	}
	
	void addWordInSentence(Word wordInSentence) {
		wordsInSentence.add(wordInSentence);
	}
	
	void showWordsInSentence() {
		for (Word wordInSentence : wordsInSentence) {
			System.out.println(wordInSentence.getWordText() + " " + wordInSentence.getSentencePos());
		}
	}
	
	void setSentenceImportance(double importance) {
		sentenceImportance = importance;
	}
	String getSentenceText() {
		return sentenceText;
	}
	
	int getSentencePos() {
		return sentencePos;
	}
	
	ArrayList<Word> getWordsInSentence(){
		return wordsInSentence;
	}
	
	double getSentenceImportance() {
		return sentenceImportance;
	}
	
}
