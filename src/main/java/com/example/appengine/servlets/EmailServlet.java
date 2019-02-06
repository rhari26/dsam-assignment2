package com.example.appengine.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import com.example.appengine.java8.ElectionSummary;

/**
 * Servlet implementation class EmailServlet
 */
//@WebServlet("/EmailServlet")
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String kind = request.getParameter("type");
		if(kind != null && kind.equals("cronjob")) {
			ElectionSummary es = new ElectionSummary();
			Entity electionTime = es.getElectionTime();
			
			String electionDateTime = electionTime.getProperty("electionDate")+" "+electionTime.getProperty("electionStartTime");
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");  
			String timeStamp = new SimpleDateFormat("yy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
			
			Date electionDate = null;
			Date currentTime = null;
			
			try {
				electionDate = format.parse(electionDateTime);
			    currentTime = format.parse(timeStamp);
			}
			catch(Exception e) {
				
			}
			
			long diff = electionDate.getTime() - currentTime.getTime();  
			long diffHours = diff / (60 * 60 * 1000);
			
			if(diffHours == 24) {
				es.sendCronEmailVoters();
			}
			
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
