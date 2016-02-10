package org.jboss.tools.f2f.processor;

public class WordProcessorTest {
	
	private WordProcessor wordProcessor;

	public void setUp() {
		wordProcessor = new WordProcessor(new DefaultProcessor(), new MongoDbDataDao());
	}

	public void testProcess() {
		wordProcessor.process();
	}
}
