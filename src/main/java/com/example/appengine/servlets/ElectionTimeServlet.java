package com.example.appengine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Servlet implementation class ElectionTimeServlet
 */
//@WebServlet("/ElectionTimeServlet")
public class ElectionTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElectionTimeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/electionTime.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String electionDate = request.getParameter("date");
		String electionStartTime = request.getParameter("startTime");
		String electionEndTime = request.getParameter("endTime");
		
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity election = new Entity("Elections");
			
			election.setProperty("electionDate", electionDate);
			election.setProperty("electionStartTime", electionStartTime);
			election.setProperty("electionEndTime", electionEndTime);
			
			ds.put(election);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		response.sendRedirect(request.getRequestURL().toString());
	}

}
