package org.jboss.tools.f2f.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.jboss.tools.f2f.model.WordData;

public class DefaultProcessor implements Processor {

	private static final List<String> STOP_WORDS = new ArrayList<>();
	
	static {
		try (BufferedReader  stopwords = new BufferedReader(new InputStreamReader(DefaultProcessor.class.getResourceAsStream("/stopwords.txt"), "UTF-8"))){
			String line = null;
			while ((line = stopwords.readLine()) != null) {
				STOP_WORDS.add(line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
			
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
		if (word == null || word.length() < 4) {
			return;
		}
		word = word.toLowerCase();
		if (STOP_WORDS.contains(word)) {
			return;
		}
		WordData data = map.get(word);
		if (data == null) {
			data = new WordData(word);
			map.put(word, data);
		}
		data.increment();
	}
	
	private void analyze(WordData data, int totalWords, int nbWords) {
		float count = (float)data.getCount();
		data.setDensity( count / nbWords);
		data.setFrequency(count / totalWords);
	}

}
