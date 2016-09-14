package org.interview.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.interview.cons.Constants;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * It make user of Twitter Streaming api to connect to twitter and retrieves realtime tweets.
 *
 *@author SHUBHAM
 */

public class TwitterStreamingService extends Thread{
	
	static Logger log = Logger.getLogger(TwitterStreamingService.class);
	public long conOpenTime=0;
	public long logTime=0;
	
	
	/**
     * This methods performs authorise the twitter streaming request and open the http connection
     * to receive the tweets that matches the filter query.
     * @return This method returns a list of tweets received over the time period for "bieber"
     */
	
	public ArrayList<Status> getTwitterMessageStream() 
	{
		
		ArrayList<Status> messageList = new ArrayList<Status>();
		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		ConnectionLifeCycleListener connLifeCycleListener = new ConnectionLifeCycleListener() {
			
				@Override
				public void onDisconnect() {
					log.info(Constants.DISCONNECT);
					System.out.println("Disconnecting...");
				}
				
				@Override
				public void onConnect() {
					conOpenTime = System.currentTimeMillis();
					logTime=conOpenTime;
					log.info(Constants.CONNECT);
					System.out.println("Connecting... at " + conOpenTime);
				}
		
				@Override
				public void onCleanUp() {
					log.info(Constants.CLEANUP);
					System.out.println("Cleaning up");
					
				}
		};
		
		StatusListener listener = new StatusListener() {
			 	
				int count=0;
				long time=0;
				long interval=0;
				
		        public void onStatus(Status status) {
				 	 count++;
		        	 messageList.add(status);
			         checkCountAndTimeLimit();
			         logStatistics(logTime,time);
			    }
		        
		        public void onException(Exception ex) {
		        	log.error(Constants.EXCEPTION);
		            ex.printStackTrace();
		        }

				@Override
				public void onStallWarning(StallWarning arg0) {
					log.info("STALL_WARNINGS");
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice arg0) {
					
				}

				@Override
				public void onScrubGeo(long arg0, long arg1) {
					
				}

				@Override
				public void onTrackLimitationNotice(int arg0) {
					
				}
				//Log statistics at every second 
		        private void logStatistics(long logTime, long time) {
		        	
		        	 time=System.currentTimeMillis()-logTime;
			         if(time>Constants.ONE_SECOND)
			         {
			        	 Date date = new Date();
			        	 System.out.println(new Timestamp(date.getTime()));
			        	 log.info(messageList.size() + Constants.STATS_LOG);
			        	 log.info(new Timestamp(date.getTime()));
			        	 logTime=logTime+ Constants.ONE_SECOND;
			         }
				}

		        //check the limit of 100 msgs or 30 secs, whichever comes true first
				private void checkCountAndTimeLimit() {
					
					 interval=System.currentTimeMillis()-conOpenTime;
					 
		        	 if(count > Constants.COUNT_LIMIT){
		        		 log.info(Constants.COUNT_LIMIT_REACHED);
			        	 twitterStream.clearListeners();
			        	 twitterStream.shutdown();
			         }else if(interval>Constants.CONNECTION_TIME_IN_MILLIS){
			        	 log.info(Constants.TIME_LIMIT_REACHED);
			        	 twitterStream.clearListeners();
			        	 twitterStream.shutdown();
			         }
				}
		    };
		    
		
		    twitterStream.addListener(listener);
		    twitterStream.addConnectionLifeCycleListener(connLifeCycleListener);
		  
		    FilterQuery fQuery = new FilterQuery();
		    String keywords[] = {"bieber"};
		    fQuery.track(keywords);
		    twitterStream.filter(fQuery); 
		    
		    try{
		        Thread.sleep(Constants.CONNECTION_TIME_IN_MILLIS);
		    }
		    catch (InterruptedException e) {
		    	log.error(e.getLocalizedMessage());
				twitterStream.shutdown();
				e.printStackTrace();
			}
		    catch (Exception e){
		    	log.error(e.getMessage());
		    }
		    
		    log.info(Constants.TIME_LIMIT_REACHED);
		    twitterStream.shutdown();
		    return messageList;
	
	}
}
