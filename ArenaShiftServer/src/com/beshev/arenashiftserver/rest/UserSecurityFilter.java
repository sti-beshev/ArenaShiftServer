package com.beshev.arenashiftserver.rest;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import com.beshev.arenashiftserver.user.UserAuth;

@Provider
@UserSecure
public class UserSecurityFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws WebApplicationException {
		
		String authCredentials = requestContext.getHeaderString("Authorization");

		boolean authenticationStatus = checkAuthentication(authCredentials);

		if (!(authenticationStatus)) {
			
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
	}
	
	private boolean checkAuthentication(String authCredentials) {
		
		if (null == authCredentials) return false;
		
		final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
		String usernameAndPassword = null;
		
		try {
			
			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
			
		} catch (IOException e) {
			
			return false;
		}
		
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		boolean authenticationStatus = UserAuth.checkUserCredentials(username, password);
		
		return authenticationStatus;
		
	}

}
