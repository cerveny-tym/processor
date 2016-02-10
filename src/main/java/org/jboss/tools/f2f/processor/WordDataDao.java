package org.jboss.tools.f2f.processor;

import java.util.Collection;

import org.jboss.tools.f2f.model.WordData;

public interface WordDataDao {
	
	public Collection<String> getAll();
	
	public void save(Collection<WordData> data); 
}
