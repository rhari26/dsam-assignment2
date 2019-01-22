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
 * Servlet implementation class CandidateServlet
 */
@WebServlet("/CandidateServlet")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/candidate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String faculty = request.getParameter("faculty");
		
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity candidate = new Entity("Candidates");
			
			candidate.setProperty("name", name);
			candidate.setProperty("surname", surname);
			candidate.setProperty("email", email);
			candidate.setProperty("faculty", faculty);
			
			ds.put(candidate);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		response.sendRedirect("http://localhost:8080/candidate");
	}

}
