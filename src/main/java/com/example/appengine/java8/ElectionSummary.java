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
	
	public void sendEmailVoters() {
		
		String host = "localhost";
		String from = "rhari26@gmail.com";
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query qry = new Query("Voters");
		List<Entity> votersList = datastore.prepare(qry).asList(FetchOptions.Builder.withDefaults());
		
		try {
			for(Entity voter: votersList) {
				String to = voter.getProperty("email").toString();
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));			
				message.setSubject("This is the Subject Line!");
				message.setText("This is actual message");
				Transport.send(message);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
