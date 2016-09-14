package org.interview.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import junit.framework.Assert.*;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TestTwitterStream {
	
	@Test
	public void testTwitterSteam()
	{
		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		
		assertNotNull(twitterStream);
		
		
		
	}

}
