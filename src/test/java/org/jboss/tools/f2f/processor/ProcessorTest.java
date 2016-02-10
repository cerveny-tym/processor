package org.jboss.tools.f2f.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Choice;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.tools.f2f.model.WordData;
import org.junit.Before;
import org.junit.Test;

public class ProcessorTest {

	private DefaultProcessor processor;

	@Before
	public void before() {
		processor = new DefaultProcessor();
	}

	@Test
	public void testProcess() {
		String text = "This is some real test A real real test";
		Collection<WordData> result = processor.process(text);
		Map<String, WordData> map = result.stream().collect(Collectors.toMap(WordData::getWord, Function.identity()));
		assertNotNull(result);
		assertEquals(6, result.size());
		WordData some = map.get("some");
		assertEquals(1, some.getCount());
		assertEquals(0.16, some.getFrequency(), 0.01);
//		assertEquals(1, some.getDensity(), 0.1);

		WordData real = map.get("real");
		assertEquals(3, real.getCount());
		assertEquals(0.5, real.getFrequency(), 0.01);
//		assertEquals(1, real.getDensity(), 0.1);
		
		WordData test = map.get("test");
		assertEquals(2, test.getCount());
//		assertEquals(3, test.getFrequency(), 0.1);
//		assertEquals(1, test.getDensity(), 0.1);
		
		WordData a = map.get("a");
		assertEquals(1, a.getCount());
		
	}

}
