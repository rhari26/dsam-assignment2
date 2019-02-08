package com.example.appengine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.java8.ElectionSummary;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


/**
 * Servlet implementation class CastVoteServlet
 */
//@WebServlet("/CastVoteServlet")
public class CastVoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CastVoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = request.getParameter("token");
		try {
		
		ElectionSummary es = new ElectionSummary();
			if(token != null) {
				
				Entity voterData = es.getVoterData(token);
				String voterKey = voterData.getProperty("token").toString();
				
				if(voterKey.equals(token) && voterData.getProperty("voted").toString() == "false" && es.votingTime() == true) {
					request.setAttribute("voterId", voterData.getKey().getId());
					request.getRequestDispatcher("castVote.jsp").forward(request, response);
				}
				else
				{
					request.getRequestDispatcher("404page.jsp").forward(request, response);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String candidateId = request.getParameter("candidate");
		String voterId = request.getParameter("id");
		Key voterKey = KeyFactory.createKey("Voters", Long.parseLong(voterId));
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity voter = datastore.get(voterKey);
			if(voter.getProperty("voted").toString() == "false") {
				voter.setProperty("candidate", Long.parseLong(candidateId));
				voter.setProperty("voted", "true");
				datastore.put(voter);
			}
			response.sendRedirect(request.getRequestURL().toString());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
