package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ReimbServlet extends HttpServlet {
	private ReimbursementDao reimbursementDao = ReimbursementDao.currentImplementation;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getRequestURL());
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
		// TODO Auto-generated method stub
		super.service(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Reimbursement> reimbursements = null;

		System.out.println("uri = " + req.getRequestURI());
		if ("/Project1/reimbursements/employee".equals(req.getRequestURI())) {

			String username = req.getParameter("username");
			System.out.println(username);

			if (username != null) { // find by trainer name
				reimbursements = reimbursementDao.findByUsername(username);
				resp.setStatus(201);
			} else {
				resp.setStatus(401);
			}
		} else if ("/Project1/reimbursements/manager".equals(req.getRequestURI())) {

			String statusType = req.getParameter("status");
			System.out.println(statusType);
			if (statusType != null) { // find by trainer name
				reimbursements = reimbursementDao.findByStatus(statusType);
				resp.setStatus(201);
			} else {
				reimbursements = reimbursementDao.findAll();
				resp.setStatus(201);
			}
		} else {
			resp.setStatus(401);
		}

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(reimbursements);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Reimbursement r = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class);
		if ("/Project1/reimbursements/employee/create-ticket".equals(req.getRequestURI())) {

			// read the reimbursement from the request body
			
			System.out.println(r);
			if (r != null) {
				int id = reimbursementDao.save(r);
				r.setId(id);
				resp.setStatus(201); // created status code
			} else {
				resp.setStatus(401);
			}
		
			
		}
		resp.getWriter().write(om.writeValueAsString(r));

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		Reimbursement r = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class);
		System.out.println("uri = " + req.getRequestURI());
		if ("/Project1/reimbursements/manager/update-ticket".equals(req.getRequestURI())) {

			reimbursementDao.updateStatus(r.getId(), r.getStatusType(), r.getResolverId());
			resp.setStatus(201); // created status code
		} else {
			resp.setStatus(401);
		}

		resp.getWriter().write(om.writeValueAsString(r));
	}
}
