package org.jboss.tools.f2f.processor;

import org.junit.Before;
import org.junit.Test;

public class WordProcessorTest {
	
	private WordProcessor wordProcessor;

	@Before
	public void setUp() {
		wordProcessor = new WordProcessor(new DefaultProcessor(), new MongoDbDataDao());
	}

	@Test
	public void testProcess() {
		wordProcessor.process();
	}
}
