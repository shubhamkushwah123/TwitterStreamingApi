package org.interview.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintStream;
import org.interview.cons.Constants;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;

public class TestOAuth {

	@Test
	public void verifyOauthAuthorisation() throws TwitterAuthenticationException, IOException{
		
		PrintStream out = new PrintStream(System.out);
		
		TwitterAuthenticator authenticator = new  TwitterAuthenticator(out, Constants.CONSUMER_KEY, Constants.CONSUMER_KEY_SECRET);

		HttpRequestFactory factory = authenticator.getAuthorizedHttpRequestFactory();
		
		assertNotNull(factory);
		
	}

}
