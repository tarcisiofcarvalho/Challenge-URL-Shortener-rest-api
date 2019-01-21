package com.urlshortener.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.urlshortener.statistics.URLStatistics;

/** REST API to generate shortened URL statistics
 * @author Tarcisio Franco de Carvalho
 * @version 1.0
 */
@Path("statistics")
public class URLStatisticsResource {

	/** Forwarded URL Statistic Resource
	 * @param url
	 * @param hashCode
	 * @return Short URL
	 */
	@POST
	@Path("forward")
	@Produces(MediaType.APPLICATION_JSON)
	public Response forward(@FormParam(value = "urlShortened") String urlHash, 
						    @FormParam(value = "fromDate") String fromDate,
						    @FormParam(value = "toDate") String toDate) {
		
		Response result = null;
		
		try {
			
			URLStatistics st = new URLStatistics();
			
			// Get the forward statistic
			int count = st.getURLForwardByPeriod(fromDate, toDate, urlHash);
							
			result = Response.status(Response.Status.OK)
						.entity("{'forwardCount' : '" + count + "', 'fromDate' : '" + fromDate + "', 'toDate' : '" + toDate + "', 'urlHash' : '" + urlHash + "'}")
			            .build();

		}catch (Exception e) {
			result = Response.status(Response.Status.SEE_OTHER)
			.entity("{'stacktrace' : '" + e.getMessage() + "'}")
		    .build();
		}

		return result;  
		
	}

	/** Shortened URL Generation Statistic Resource
	 * @param url
	 * @param hashCode
	 * @return Short URL
	 */
	@POST
	@Path("generation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generation(@FormParam(value = "urlShortened") String urlHash, 
						    @FormParam(value = "fromDate") String fromDate,
						    @FormParam(value = "toDate") String toDate,
						    @FormParam(value = "alias") String alias) {
		
		Response result = null;
		
		try {
			
			URLStatistics st = new URLStatistics();
			
			// Get the forward statistic
			int count = st.getShortURLCreationByPeriod(fromDate, toDate, urlHash, alias);
							
			result = Response.status(Response.Status.OK)
						.entity("{'urlGeneratedCount' : '" + count + "', 'fromDate' : '" + fromDate + "', 'toDate' : '" + toDate + "', 'urlHash' : '" + urlHash + "', 'alias': '" + alias +"'}")
			            .build();

		}catch (Exception e) {
			result = Response.status(Response.Status.SEE_OTHER)
			.entity("{'stacktrace' : '" + e.getMessage() + "'}")
		    .build();
		}

		return result;  
		
	}
}
