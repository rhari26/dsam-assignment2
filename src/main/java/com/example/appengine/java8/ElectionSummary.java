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
	
	public void sendEmailVoters(String toEmail) {
		
		String host = "localhost";
		String from = "rhari26@gmail.com";
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		  Message msg = new MimeMessage(session);
		  msg.setFrom(new InternetAddress("rhari26@gmail.com"));
		  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, "Mr. User"));
		  msg.setSubject("Your Example.com account has been activated");
		  msg.setText("This is a test");
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
		Entity election = datastore.prepare(electionQuery).asList(FetchOptions.Builder.withDefaults()).get(0);
		
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
			  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(voter.getProperty("email").toString().trim(), "Mr. User"));
			  msg.setSubject("Your Example.com account has been activated");
			  msg.setText("This is a test");
			  Transport.send(msg);
			}
		}
		} catch (Exception e) {
		  // ...
		}

	}

}
