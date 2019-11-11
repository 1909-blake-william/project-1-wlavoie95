package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDao {
	ReimbursementDao reimbursementDao = new ReimbursementDaoSQL();
	
	int save(Reimbursement r);
	
	List<Reimbursement> showAllReimbursements();
	
	List<Reimbursement> getByStatusId(int statusId);
	
	List<Reimbursement> viewUserTickets(int userId);
	
	int updateStatus(int reimbursementId, int statusId, int resolverId);
}
