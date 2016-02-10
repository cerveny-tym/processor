package org.jboss.tools.f2f.model;

public class WordData {

	private String word;
	private int count;
	private float frequency;
	private float density;

	public WordData(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int increment() {
		return ++count;
	}
}
