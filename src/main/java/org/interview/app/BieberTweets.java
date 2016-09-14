package org.interview.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.interview.dto.Message;
import org.interview.service.DataProcessingService;
import org.interview.service.TwitterStreamingService;
import twitter4j.Status;


/**
 * This is the main entry point for Twitter Streaming program. It calls the twitter streaming service to get the tweets and
 * data is rendered by the using Data Processing Service.
 * 
 * @author SHUBHAM SINGH KUSHWAH
 */


public class BieberTweets {
	
static Logger log = Logger.getLogger(BieberTweets.class);
	
	public static void main(String args[]) throws IOException, InterruptedException
	{
		
		log.info("Streaming program executes starts");
		
		DataProcessingService dataService = new DataProcessingService();
		ArrayList<Message> messageList = new ArrayList<Message>();
		
		TwitterStreamingService streamingService = new TwitterStreamingService();
		ArrayList<Status> statusList = streamingService.getTwitterMessageStream();
		
		if(statusList!=null){	
			
			messageList = dataService.getMessageList(statusList);
			LinkedHashMap<Long,ArrayList<Message>> msgMap = dataService.sortAndGroupMessage(messageList);
			dataService.writeAsPerUserWish(msgMap);
		}
		log.info("Streaming program finished.");
		
	}

}
