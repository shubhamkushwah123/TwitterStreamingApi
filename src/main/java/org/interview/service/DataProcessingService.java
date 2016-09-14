package org.interview.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.interview.app.BieberTweets;
import org.interview.dto.Message;
import twitter4j.Status;


/**
 * It provides data processing mechanism by sorting,printing and writing of data
 *@author SHUBHAM
 */

public class DataProcessingService {
	
	static Logger log = Logger.getLogger(BieberTweets.class);	
	
	LinkedHashMap<Long,ArrayList<Message>> msgMap = new LinkedHashMap<Long,ArrayList<Message>>();
	

	/**
	 * This method filter out and transfer the data from the Status object to Message object.
	 * @param It takes an ArrayList of type Status as input that has received by twitter streaming
	 * @return It returns an ArrayList of type Message
	 */

	public ArrayList<Message> getMessageList(ArrayList<Status> statusList) {
		ArrayList<Message> list = new ArrayList<Message>();
		for(Status status : statusList)
		{
			Message message = new Message();
			message.setMessageId(status.getId());
			message.setCreationDate(status.getCreatedAt().getTime());
			message.setMessageText(status.getText());
			message.setAuthor(status.getUser().getName());
			message.setUserId(status.getUser().getId());
			message.setUserName(status.getUser().getName());
			message.setScreenName(status.getUser().getScreenName());
			message.setUserCreatedAt(status.getUser().getCreatedAt().getTime());
			list.add(message);
		}
		return list;
	}


	/**
	 * This method calls the further methods to perform sorting & grouping of messages and users chronologically
	 * @param It takes an ArrayList of type  ArrayList of type Message
	 * @return it returns an linkedHashMap with sorted users and sorted msgs grouped on users
	 */
	public LinkedHashMap<Long,ArrayList<Message>> sortAndGroupMessage(ArrayList<Message> messageList) {
			
		//Sort list of messages on user creation date chronologically
			Collections.sort(messageList);
			
		//Group messages created by the same user.
			groupMsgByUsers(messageList);
			
		//Sort message chronologically based on users.
			chronologicallySortMsgPerUser(msgMap);
			return msgMap;
	}

	
	/**
	 * It groups the messages created by same user and puts them into LinkedHashMap
	 */
	public void groupMsgByUsers(ArrayList<Message> msgList) {
		
			Iterator<Message> iterator = msgList.iterator();
			while(iterator.hasNext())
			{
				Message msg =(Message)iterator.next();
				if(msgMap.containsKey(msg.getUserId())){
					ArrayList<Message> temp = msgMap.get(msg.getUserId());
					temp.add(msg);
					msgMap.replace(msg.getUserId(), temp);
				} 
				else{
					ArrayList<Message> temp = new ArrayList<Message>();
					temp.add(msg);
					msgMap.put(msg.getUserId(), temp);
				}
			}
	}

	
	/**
	 * It sorts the messages grouped by per user chronologically
	 */
	public void chronologicallySortMsgPerUser(LinkedHashMap<Long, ArrayList<Message>> msgMap2) {

			for(Long key:msgMap.keySet())
			{
				System.out.println(key);
				ArrayList<Message> msgList = msgMap2.get(key);
				Collections.sort(msgList, new Message());
				msgMap2.replace(key, msgList);
			}
		
	}

	/**
	 * It provides the user with option to print the file 1-Console and 2-TXT file. Input of the user is read output is created
	 * @param LinkedHashMap of sorted msgs
	 */

	public void writeAsPerUserWish(LinkedHashMap<Long, ArrayList<Message>> msgMap) {
			String delimiter="||";
			Scanner scan = new Scanner(System.in); 
			System.out.println("Select your choice to print the data and press Enter\n");
			System.out.println("Press 1 to print on Console and 2 to print on file");
			String choice=scan.next();
			if(choice.equalsIgnoreCase("1")){
				printDataOnConsole(msgMap,delimiter);
				scan.close();
			}
			else if(choice.equalsIgnoreCase("2")){
				System.out.println("Enter the Delimiter for file and press Enter\n ");
				delimiter=scan.next();
				printDataInFile(msgMap,delimiter);
				scan.close();
			}
			else{	
			System.out.println("Invalid Choice");
			writeAsPerUserWish(msgMap);
			}
				
	}
			

	/**
	 * If users selects to print the output in file, This method will write the data in a file named "bieber_tweets.txt"
	 */
	public void printDataInFile(LinkedHashMap<Long, ArrayList<Message>> msgMap2, String delimiter) {
			try{
			File file = new File("bieber_tweets.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			System.out.println("file created at location" + file.getAbsolutePath());
			ArrayList<Message> printList = new ArrayList<Message>();
			int recordCount=0;
			//creating header date in the file
			writer.write("Creation Date" + new Date() + "\n");
			
			//creating header record containing attribute names
			writer.write("USER ID"+ delimiter + 
					     "USER NAME" + delimiter+ 
					     "SCREEN NAME" + delimiter + 
					     "MESSAGE" + delimiter + 
					     "MESSAGE AUTHOR" + delimiter + 
					     "MESSAGE CREATED AT" + delimiter + 
					     "USER CREATED AT" + delimiter + 
					     "MESSAGE ID" + "\r\n");
			
			for(Long key:msgMap2.keySet())
			{
				printList = msgMap2.get(key);
				for(Message message:printList){
				writeMessageInFile(writer,message,delimiter);
				recordCount++;
				}
			}
			writer.write("Total Number of records:" + recordCount);
			writer.close();
			log.info("Data has been written in file successfully at " + file.getAbsolutePath());
			}catch(IOException e)
			{
				log.info("Exception Occured" + e.getMessage());
				e.printStackTrace();
			}	
	}

	
	/**
	 * If users selects to print the output in console, This method will print the output in Console 
	 */
	public void printDataOnConsole(LinkedHashMap<Long, ArrayList<Message>> msgMap2,String delimiter) {
			ArrayList<Message> printList = new ArrayList<Message>();
			printHeaders(delimiter);
			for(Long key:msgMap2.keySet())
			{
				printList = msgMap2.get(key);
				for(Message message:printList){
				printMessage(message,delimiter);
				}
			}
	}


	/**
	 * It writes the particular message in file
	 */
	public void writeMessageInFile(FileWriter writer, Message message, String delimiter) {
			try {
				writer.write(message.getUserId()       + delimiter);
				writer.write(message.getUserName()     + delimiter);
				writer.write(message.getScreenName()   + delimiter);
				writer.write(message.getMessageText()   + delimiter);
				writer.write(message.getAuthor()       + delimiter);
				writer.write(message.getCreationDate() + delimiter);
				writer.write(message.getUserCreatedAt()+ delimiter);
				writer.write(message.getMessageId()+ "\n");
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		
	}

	
	/**
	 * It prints a particular message in Console
	 */
	public void printMessage(Message message , String delimiter) {
			System.out.print(message.getUserId()       + delimiter);
			System.out.print(message.getUserName()     + delimiter);
			System.out.print(message.getScreenName()   + delimiter);
			System.out.print(message.getMessageText()  + delimiter);
			System.out.print(message.getAuthor()       + delimiter);
			System.out.print(message.getCreationDate() + delimiter);
			System.out.print(message.getUserCreatedAt()+ delimiter);
			System.out.print(message.getMessageId()  + delimiter+ "\n");
	}

	
	
	
	public void printHeaders(String delimiter) {
			System.out.println("USER ID"            +delimiter+ 
								"USER NAME"          +delimiter+ 
								"SCREEN NAME"        +delimiter+ 
								"MESSAGE"            +delimiter+ 
								"MESSAGE AUTHOR"     +delimiter+ 
								"MESSAGE CREATED AT" +delimiter+ 
								"USER CREATED AT"    +delimiter+ 
								"MESSAGE ID");
	}

}
