package com.example.appengine.servlets;
import com.example.appengine.java8.ElectionSummary;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


/**
 * Servlet implementation class Voters
 */
//@WebServlet("/Voters")
public class VotersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(VotersServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VotersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/voters.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        //log.info("ssssssssssssssssss");
		try {
			String[] emails = request.getParameterValues("email");
			ElectionSummary es = new ElectionSummary();
			for(String email : emails) {
				DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
				Entity voter = new Entity("Voters");
				String uniqueID = UUID.randomUUID().toString();
				
				voter.setProperty("email", email);
				voter.setProperty("token", uniqueID);
				voter.setProperty("candidate", 0);
				voter.setProperty("voted", false);
				ds.put(voter);
				es.sendEmailVoters(email);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
//		reqURL = new URL(request.getRequestURL().toString());
		response.sendRedirect(request.getRequestURL().toString());
	}

}
