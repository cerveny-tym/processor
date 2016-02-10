package org.jboss.tools.f2f.processor;

import java.util.Collection;
import java.util.Map;

import org.jboss.tools.f2f.model.WordData;

public interface Processor {
	
	Collection<WordData> process(String text);

}
