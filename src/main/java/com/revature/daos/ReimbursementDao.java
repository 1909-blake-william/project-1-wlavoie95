package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDao {
	ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	int save(Reimbursement r);
	
	List<Reimbursement> findAll();
	
	int updateStatus(int reimbursementId, String statusType, int resolverId);

	List<Reimbursement> findByUsername(String username);

	List<Reimbursement> findByStatus(String statusType);
}
