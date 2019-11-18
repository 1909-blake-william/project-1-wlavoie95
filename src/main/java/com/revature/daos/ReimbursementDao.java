package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDao {
	ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	int save(Reimbursement r);
	
	List<Reimbursement> findAll();
	
	List<Reimbursement> getByStatusId(int statusId);
	
	List<Reimbursement> viewUserTickets(int userId);
	
	int updateStatus(int reimbursementId, int statusId, int resolverId);

	List<Reimbursement> findByUsername(String username);
}
