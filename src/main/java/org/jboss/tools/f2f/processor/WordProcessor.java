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
		data.parallelStream().forEach(w -> save(w));
	}

	private void save(WordData w) {
		dao.save(w);
	}

	private Collection<WordData> getData(Collection<String> texts) {
		StringBuilder sb = new StringBuilder();
		texts.forEach(t -> sb.append(t));
		return processor.process(sb.toString());
	}

}
