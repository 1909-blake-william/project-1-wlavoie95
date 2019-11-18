package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementDao;
import com.revature.daos.UserDao;
import com.revature.models.Reimbursement;


public class ReimbServlet extends HttpServlet {
	private ReimbursementDao reimbursementDao = ReimbursementDao.currentImplementation;
	private UserDao userDao = UserDao.currentImplementation;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		System.out.println("To context param: " + req.getServletContext().getInitParameter("To"));

		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Reimbursement> reimbursements;

		String username = req.getParameter("username");

		if (username != null) { // find by trainer name
			reimbursements = reimbursementDao.findByUsername(username);
		} else { // find all
			reimbursements = reimbursementDao.findAll();
		}

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(reimbursements);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read the reimbursement from the request body
		ObjectMapper om = new ObjectMapper();
		Reimbursement r = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class);

		System.out.println(r);

		int id = reimbursementDao.save(r);
		r.setId(id);
		
		resp.setStatus(201); // created status code

		resp.getWriter().write(om.writeValueAsString(r));
		

	}
	
	/*
	 * Need to research doPut a bit more.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reimbIdString = req.getParameter("reimbId");
		int reimbId = Integer.parseInt(reimbIdString);
	}
}
