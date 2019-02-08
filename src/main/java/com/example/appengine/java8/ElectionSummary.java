package com.example.appengine.java8;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class ElectionSummary {
	
	public ElectionSummary() {
		
	}
	
	//This function sends an email with a link to cast a vote after creating a voter
	public void sendEmailVoters(String toEmail, String token) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress("rhari26@gmail.com"));
		  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, ""));
		  msg.setSubject("University Election - Voting Link");
		  msg.setText("Hi, please cast your vote in the following link https://dsam-demo.appspot.com/cast-vote?token="+token);
		  Transport.send(msg);
		} catch (Exception e) {
		  // ...
		}

	}
	
	//This function displays all voters
	public List<Entity> votersList(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Voters");
		List<Entity> votersList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		return votersList;
	}
	
	//This function displays all candidates
	public List<Entity> candidateList(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Candidates");
		List<Entity> candidateList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		return candidateList;
	}
	
	//This function returns the election date and time.
	public Entity getElectionTime() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query electionQuery = new Query("Elections");
		Entity election = datastore.prepare(electionQuery).asSingleEntity();
		return election;
	}
	
	//This function returns an entity of a voter 
	public Entity getVoterData(String token) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("token",FilterOperator.EQUAL, token));
		Entity voter = ds.prepare(q).asList(FetchOptions.Builder.withDefaults()).get(0);
		
		return voter;
	}
	
	//This function sends a reminder email for all voters who didn't cast their vote.
	public void sendCronEmailVoters() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Voters");
		List<Entity> votersList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		for(Entity voter : votersList){
			if(voter.getProperty("voted").toString() == "true") {
			  Message msg = new MimeMessage(session);
			  msg.setFrom(new InternetAddress("rhari26@gmail.com"));
			  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(voter.getProperty("email").toString().trim(), ""));
			  msg.setSubject("Reminder: University Election - Voting Link");
			  msg.setText("Hi, please cast your vote in the following link https://dsam-demo.appspot.com/cast-vote?token="+voter.getProperty("token").toString());
			  Transport.send(msg);
			}
		}
		} catch (Exception e) {
		  // ...
		}

	}
	
	//This function returns vote count of a single candidate
	public int showCandidateVoteCount(long key) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("candidate",FilterOperator.EQUAL, key));
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	//This function returns the election dates and time count.
	public int showElectionTimeCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Elections");
		List<Entity> election = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return election.size();
	}
	
	//This function returns the count of all voters.
	public int showVotersCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters");
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	//This function returns the count of voters who casted the vote.
	public int showCastedVoteCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("voted",FilterOperator.EQUAL, "true"));
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	//This function returns percentage of how many votes are casted
	public float votingPercentage() {
		float casted = showCastedVoteCount();
		float total = showVotersCount();
		float result = (casted/total)*100;
		return result;

	}
	
	//This function returns a boolean to find the election date is started. 
	@SuppressWarnings("deprecation")
	public boolean votingTime() {
		boolean result = false;
		Entity electionTime = getElectionTime();
		String electionDateTime = electionTime.getProperty("electionDate")+" "+electionTime.getProperty("electionStartTime");
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");  
		String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
		
		Date electionDate = null;
		Date currentTime = null;
		
		try {
			electionDate = format.parse(electionDateTime);
		    currentTime = format.parse(timeStamp);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		if(electionDate.getDate() == currentTime.getDate() && electionDate.getTime() == currentTime.getTime()) {
			result = true;
		}
		return result;
	}

}
