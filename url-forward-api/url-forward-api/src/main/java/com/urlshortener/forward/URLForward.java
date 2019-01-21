package com.urlshortener.forward;

import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/** It represents URL forward resources 
 * @author Tarcicio Franco de Carvalho
 * @version 1.0
 *
 */
public class URLForward {
	
	/** Processes short URL and register the action
	 * @param urlHash
	 * @return string with the original URL 
	 */
	public String forward(String urlHash) {
		
		String str = getOriginalURL(urlHash);
		
		registerForward(urlHash);
		
		return str;
	}
	
	/** Retrieves from database the original URL
	 * @param urlHash
	 * @return return a string with the original URL
	 */
	private String getOriginalURL(String urlHash){
		
		// Stores the originalURL
		String originalURL = null;
		
		// Query original URL on NoSQL
		try {
			// Get Mongo object connection
			String dbURI = "mongodb://tarcisiofcarvalho:mongo10mongo@ds231951.mlab.com:31951/url_shortener";
			MongoClient client = new MongoClient(new MongoClientURI(dbURI));
			@SuppressWarnings("deprecation")
			DB database = client.getDB("url_shortener");
			DBCollection collection = database.getCollection("urls");
			
			// Preparing the query
			DBObject q = new BasicDBObject("urlHash",urlHash);
			DBCursor c = collection.find(q);
			originalURL = c.one().get("url").toString();

			// Closing connection
			client.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return originalURL;
	}

	/** Registers the forward request
	 * @param urlHash
	 */
	private void registerForward(String urlHash) {
				
		// Persist document element on NoSQL
		try {
			// Get Mongo object connection
			String dbURI = "mongodb://tarcisiofcarvalho:mongo10mongo@ds231951.mlab.com:31951/url_shortener";
			MongoClient client = new MongoClient(new MongoClientURI(dbURI));
			@SuppressWarnings("deprecation")
			DB database = client.getDB("url_shortener");
			DBCollection collection = database.getCollection("forwards");
			
			// Preparing to insert the forward register and time stamp		
			DBObject dbObject = new BasicDBObject();
			dbObject.put("urlHash", urlHash);
			dbObject.put("timestamp", new Date());
			
			// Insert URL data
			collection.insert(dbObject);

			// Closing connection
			client.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
