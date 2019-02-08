package com.example.appengine.java8;

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
	
	public List<Entity> votersList(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Voters");
		List<Entity> votersList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		return votersList;
	}
	
	public List<Entity> candidateList(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Candidates");
		List<Entity> candidateList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		return candidateList;
	}
	
	public Entity getElectionTime() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query electionQuery = new Query("Elections");
		Entity election = datastore.prepare(electionQuery).asSingleEntity();
		
		return election;
	}
	
	public Entity getVoterData(String token) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("token",FilterOperator.EQUAL, token));
		Entity voter = ds.prepare(q).asList(FetchOptions.Builder.withDefaults()).get(0);
		
		return voter;
	}
	
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
	
	public int showCandidateVoteCount(long key) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("candidate",FilterOperator.EQUAL, key));
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	public int showElectionTimeCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Elections");
		List<Entity> election = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return election.size();
	}
	
	public String showVoteStatus(String status) {
		String str = "";
		if(status.equals("true")) {
			str = "Voted";
		}
		else if(status.equals("false")) {
			str = "Not Voted";
		}
		return str;
	}
	
	public int showVotersCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters");
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	public int showCastedVoteCount() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Voters").setFilter(new FilterPredicate("voted",FilterOperator.EQUAL, "true"));
		List<Entity> votes = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return votes.size();
	}
	
	public float votingPercentage() {
		float casted = showCastedVoteCount();
		float total = showVotersCount();
		float result = (casted/total)*100;
		return result;

	}

}
