package org.jboss.tools.f2f.processor;

import java.util.Collection;

import org.jboss.tools.f2f.model.WordData;

public class WordProcessor {
	
	private WordDataDao dao;
	private Processor processor;

	public WordProcessor(Processor processor, WordDataDao dao) {
		this.processor = processor;
		this.dao = dao;
	}
	
	public void process() {
		Collection<WordData> data = getData(dao.getAll());
		dao.save(data);
	}

	private Collection<WordData> getData(Collection<String> texts) {
		StringBuilder sb = new StringBuilder();
		texts.forEach(t -> sb.append(t));
		return processor.process(sb.toString());
	}

	
	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor(new DefaultProcessor(), new MongoDbDataDao());
		wordProcessor.process();
	}
	
}
