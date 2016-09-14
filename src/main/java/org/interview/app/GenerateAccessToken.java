package org.interview.app;

import java.io.IOException;
import java.io.PrintStream;
import org.interview.cons.Constants;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;

public class GenerateAccessToken {
	
	public static void main(String args[])
	{
		PrintStream out = new PrintStream(System.out);
		
		TwitterAuthenticator authenticator = new  TwitterAuthenticator(out, Constants.CONSUMER_KEY, Constants.CONSUMER_KEY_SECRET);

		try {
			authenticator.getAuthorizedHttpRequestFactory();
		} 
		catch (TwitterAuthenticationException | IOException e) {
			e.printStackTrace();
		}
	
	}

}
