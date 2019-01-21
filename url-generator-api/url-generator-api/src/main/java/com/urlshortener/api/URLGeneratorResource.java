package com.urlshortener.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.urlshortener.generator.URLGenerator;

/** REST API to generate short URL from large ones
 * @author Tarcisio Franco de Carvalho
 * @version 1.0
 */
@Path("generator")
public class URLGeneratorResource {

	/** Generator from original URL to shortened URL
	 * @param url
	 * @param hashCode
	 * @return Short URL
	 */
	@POST
	@Path("data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generator(@FormParam(value = "url") String url, @FormParam(value = "alias") String alias) {
		Response result = null;
		try {
			URLGenerator gen = new URLGenerator();
			if(url!=null && alias!=null) {
				
				// Generate shortened URL
				String newURL = gen.getHashUrl(url);
				
				// Persist original URL, new URL and alias
				gen.persist(url, newURL, alias);
				
				result = Response.status(Response.Status.OK)
						.entity("{'newURL' : '" + newURL + "'}")
					     .build();
			}

		}catch (Exception e) {
			result = Response.status(Response.Status.SEE_OTHER)
			.entity("{'stacktrace' : '" + e.getMessage() + "'}")
		     .build();
		}

		return result;  
		
	}
	
}
