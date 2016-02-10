package org.jboss.tools.f2f.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.jboss.tools.f2f.model.WordData;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class MongoDbDataDao implements WordDataDao {
	
	private String username = System.getProperty("MONGODB_USERNAME", "redteam");
	private String password = System.getProperty("MONGODB_PASSWORD");
	private String mongoUrl = System.getProperty("MONGODB_URL", "ds061415.mongolab.com:61415");
	private MongoClientURI uri;
	
	public MongoDbDataDao() {
		uri = new MongoClientURI("mongodb://"+username+":"+password+"@"+mongoUrl+"/buzzword");
	}
	
	@Override
	public Collection<String> getAll() {
		MongoClient mg = new MongoClient(uri);
		List<String> texts;
		try {
			DBCollection coll = mg.getDB("buzzword").getCollection("raw");
			DBCursor cur = coll.find();
			texts = new ArrayList<>();
			while (cur.hasNext()) {  
				 String jsonWord = JSON.serialize(cur.next().get("data"));
				 texts.add(jsonWord);
			}
		} finally {
			if (mg != null) {
				mg.close();
			}
		}
		return texts;
	}

	@Override
	public void save(Collection<WordData> data) {
		MongoClient mg = new MongoClient(uri);
		MongoDatabase db = mg.getDatabase("buzzword");
		List<Document> payload = data.stream().map(wd -> toDocument(wd)).collect(Collectors.toList());
		MongoCollection<Document> stats = db.getCollection("stats");
		stats.drop();
		stats.insertMany(payload);
		mg.close();
	}

	private Document toDocument(WordData data) {
		Document d = new Document().append("word", data.getWord())
				.append("count", data.getCount())
				.append("density", data.getDensity())
				.append("frequency", data.getFrequency())
				;
		return d;
	}
}
