package com.urlshortener.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/** Shortened URL statistics
 * @author Tarcicio Franco de Carvalho
 * @version 1.0
 */
public class URLStatistics {
	

	/** Get forwarded URL statistic
	 * @param fromDate Date format expected <yyy-MM-dd>
	 * @param toDate Date format expected <yyy-MM-dd>
	 * @param urlHash Shortened URL
	 * @return number of Forwarded URL for a given period
	 * @throws ParseException 
	 */
	public int getURLForwardByPeriod(String fromDate, String toDate, String urlHash) throws ParseException{
		
		// Forwarded count
		int count = 0;
		
		// Original URL query on NoSQL

		// Get Mongo object connection
		String dbURI = "mongodb://tarcisiofcarvalho:mongo10mongo@ds231951.mlab.com:31951/url_shortener";
		MongoClient client = new MongoClient(new MongoClientURI(dbURI));
		@SuppressWarnings("deprecation")
		DB database = client.getDB("url_shortener");
		DBCollection collection = database.getCollection("forwards");
		
		// Formating dates for query
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:MM:ss");
		Date startDate = simpleDateFormat.parse(fromDate.concat(" 00:00:00"));
		Date endDate = simpleDateFormat.parse(toDate.concat(" 23:59:59"));
		
		// Query definition
		BasicDBObject query = new BasicDBObject("timestamp", new BasicDBObject("$gte",startDate).append("$lt",endDate));
		if(urlHash!=null)
			if(!urlHash.equals(""))
				query.append("urlHash", urlHash);
					
		// Query execution
		DBCursor c = collection.find(query);
		count = c.count();
		
		// Closing connection
		client.close();
		
		return count;
	}


	/** Get shortened URL generation statistic
	 * @param fromDate Date format expected <yyy-MM-dd>
	 * @param toDate Date format expected <yyy-MM-dd>
	 * @param urlHash Shortened URL
	 * @return number of Forwarded URL for a given period
	 * @throws ParseException 
	 */
	public int getShortURLCreationByPeriod(String fromDate, String toDate, String urlHash, String alias) throws ParseException{
		
		// Shortened URL generated count
		int count = 0;
		
		// Query original URL on NoSQL

		// Get Mongo object connection
		String dbURI = "mongodb://tarcisiofcarvalho:mongo10mongo@ds231951.mlab.com:31951/url_shortener";
		MongoClient client = new MongoClient(new MongoClientURI(dbURI));
		@SuppressWarnings("deprecation")
		DB database = client.getDB("url_shortener");
		DBCollection collection = database.getCollection("urls");
		
		// Formating dates for query
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(fromDate);
		Date endDate = simpleDateFormat.parse(toDate);
		
		// Query definition
		BasicDBObject query = new BasicDBObject("timestamp", new BasicDBObject("$gte",startDate).append("$lt",endDate));
		if(urlHash!=null)
			if(!urlHash.equals(""))
				query.append("urlHash", urlHash);
				
		if(alias!=null)
			if(!alias.equals(""))
				query.append("alias", alias);
		
				
		// Query execution
		DBCursor c = collection.find(query);
		count = c.count();
		
		// Closing connection
		client.close();
			
		
		return count;
	}

}
