package com.urlshortener.generator;

import java.util.Date;

import org.hashids.Hashids;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/** It represents URL resources that generates short URLs
 * @author Tarcicio Franco de Carvalho
 * @version 1.0
 */
public class URLGenerator {
	
	/** Parse the original URL to a shortened one
	 * @param url
	 * @return shortened URL
	 */
	public String getHashUrl(String url) {
		
		Hashids hashids = new Hashids(url);
		String hash = "tfc.vh/" + hashids.encode(123456L);
		
		return hash; 
		
	}
	
	/** Save the shortened URL generation data
	 * @param url 
	 * @param urlHash
	 * @param alias
	 */
	public void persist(String url, String urlHash, String alias) {
		
		// Persist document element on NoSQL
		// Get Mongo object connection
		String dbURI = "mongodb://tarcisiofcarvalho:mongo10mongo@ds231951.mlab.com:31951/url_shortener";
		MongoClient client = new MongoClient(new MongoClientURI(dbURI));
		@SuppressWarnings("deprecation")
		DB database = client.getDB("url_shortener");
		DBCollection collection = database.getCollection("urls");
		
		// Preparing to insert the alias URL, hashUrl and times stamp		
		DBObject dbObject = new BasicDBObject();
		dbObject.put("alias",alias);
		dbObject.put("url", url);
		dbObject.put("urlHash",urlHash);
		dbObject.put("timestamp", new Date());
		
		// Insert URL data
		collection.insert(dbObject);

		// Closing connection
		client.close();	
	
	}

}
