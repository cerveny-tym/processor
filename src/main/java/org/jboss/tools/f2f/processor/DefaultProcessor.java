package org.jboss.tools.f2f.processor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.jboss.tools.f2f.model.WordData;

public class DefaultProcessor implements Processor {

	@Override
	public Collection<WordData> process(String text) {
		assert text != null;
		String[] split = text.split(" ");
		int total = split.length;
		Stream<String> allWords = Stream.of(split);
		Map<String, WordData> map = new ConcurrentHashMap<>(total/2);
		allWords.forEach(word -> count(word, map));
		Collection<WordData> results = map.values();
		int nbWords = map.size();
		results.parallelStream().forEach(wd -> analyze(wd, total, nbWords));
		return results;
	}

	//TODO use Java 8 FP
	private void count(String word, Map<String, WordData> map) {
		if (word == null || word.isEmpty()) {
			return;
		}
		word = word.toLowerCase();
		WordData data = map.get(word);
		if (data == null) {
			data = new WordData(word);
			map.put(word, data);
		}
		data.increment();
	}
	
	private void analyze(WordData data, int totalWords, int nbWords) {
		float count = (float)data.getCount();
		data.setDensity( count / totalWords);
		data.setFrequency(count / nbWords);
		System.err.println(data.getWord() + " : " + data.getCount());
	}

}
