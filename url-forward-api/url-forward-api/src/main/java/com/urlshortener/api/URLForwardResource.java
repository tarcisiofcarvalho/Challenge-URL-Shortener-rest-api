package com.urlshortener.api;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.urlshortener.forward.URLForward;

/** REST API to forward short URL to original URL
 * @author Tarcisio Franco de Carvalho
 * @version 1.0
 */
@Path("forward")
public class URLForwardResource {

	/** Forward from short URL to original URL
	 * @param url
	 * @param hashCode
	 * @return Will redirect it to the original URL based on the short URL informed
	 */
	@POST
	@Path("data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response forward(@FormParam(value = "shortenedURL") String shortenedURL) {
		Response result = null;
		try {
			URLForward fw = new URLForward();
			if(shortenedURL!=null) {
				String originalURL = fw.forward(shortenedURL);
				if(originalURL!=null) {
					URI uri = new URI(originalURL);
					result = Response.status(Status.MOVED_PERMANENTLY).location(uri).build();
				}
			}
			if(result==null) {
				result = Response.status(Response.Status.NOT_FOUND)
				.entity("{'message' : 'The short url " + shortenedURL + " is not registered'}")
			    .build(); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return result;  
	}
	
}
