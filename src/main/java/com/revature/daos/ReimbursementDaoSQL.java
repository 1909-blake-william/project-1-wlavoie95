package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	private Logger log = Logger.getRootLogger();

	private Reimbursement extractReimbursement(ResultSet rs) throws SQLException {
		int ticketId = rs.getInt("reimb_id");
		int authorId = rs.getInt("ers_users_id");
		int typeId = rs.getInt("reimb_type_id");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		double amount = rs.getDouble("reimb_amount");
		String description = rs.getString("reimb_description");
		String ticketType = rs.getString("reimb_type");
		String authorFirstName = rs.getString("user_first_name");
		String authorLastName = rs.getString("user_last_name");
		String authorEmail = rs.getString("user_email");
		Timestamp resolved = null; // nullable value in DB
		int resolverId = 0;
		String resolverFirstName = ""; // nullable value in DB
		String resolverLastName = "";
		String resolverEmail = "";
		
		if (rs.getTimestamp("reimb_resolved") != null) {
			resolved = rs.getTimestamp("reimb_resolved");
			resolverId = rs.getInt("reimb_resolver");
			resolverFirstName = rs.getString(13);
			resolverLastName = rs.getString(14);
			resolverEmail = rs.getString(15);
		}
		String statusType = rs.getString("reimb_status");

		

//		if (!"Pending".equals(statusType)) { // if the ticket is not pending, then there will be a resolver and a
//												// resolver timestamp
//			
//		}

		return new Reimbursement(ticketId, authorId, typeId, submitted, amount, description, ticketType,
				authorFirstName, authorLastName, authorEmail, resolved, resolverId, resolverFirstName, resolverLastName,
				resolverEmail, statusType);
	}

	@Override
	public int save(Reimbursement r) {
		log.debug("attempting to save a new reimbursement to the DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted,"
					+ "reimb_description, reimb_author, reimb_type_id) "
					+ " VALUES (ers_reimbursement_id_seq.nextval , ?, ?, ?, ?, ?)";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDouble(1, r.getAmount());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, r.getDescription());
			ps.setInt(4, r.getAuthorId());
			ps.setInt(5, r.getTypeId());
			System.out.println(r.getAmount());
			System.out.println(new Timestamp(System.currentTimeMillis()));
			System.out.println(r.getDescription());
			System.out.println(r.getTypeId());

			return ps.executeUpdate();
		}

		catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Reimbursement> findByStatus(String statusType) {
		log.debug("attempting to show all reiumbursement tickets from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT  r.reimb_id, emp.ers_users_id, rt.reimb_type_id, r.reimb_submitted, r.reimb_amount, r.reimb_description, "
					+ "rt.reimb_type, emp.user_first_name, emp.user_last_name, emp.user_email, r.reimb_resolved, r.reimb_resolver, "
					+ "man.user_first_name AS resolver_first_name, man.user_last_name AS resolver_last_name, man.user_email AS resolver_email, "
					+ "rs.reimb_status FROM ers_reimbursement r "
					+ "LEFT JOIN ers_reimbursement_type rt ON (r.reimb_type_id = rt.reimb_type_id) "
					+ "LEFT JOIN ers_reimbursement_status rs USING (reimb_status_id) "
					+ "LEFT JOIN ers_users emp ON (r.reimb_author = emp.ers_users_id) "
					+ "LEFT JOIN ers_users man ON (r.reimb_resolver = man.ers_users_id) "
					+ "WHERE reimb_status = ? " + "ORDER BY r.reimb_submitted desc";

			PreparedStatement ps = c.prepareStatement(sql);
			System.out.println(statusType);
			ps.setString(1, statusType);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Reimbursement> findAll() {
		log.debug("attempting to show all reiumbursement tickets from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT  r.reimb_id, emp.ers_users_id, rt.reimb_type_id, r.reimb_submitted, r.reimb_amount, r.reimb_description, "
					+ "rt.reimb_type, emp.user_first_name, emp.user_last_name, emp.user_email, r.reimb_resolved, r.reimb_resolver, "
					+ "man.user_first_name AS resolver_first_name, man.user_last_name AS resolver_last_name, man.user_email AS resolver_email, "
					+ "rs.reimb_status FROM ers_reimbursement r "
					+ "LEFT JOIN ers_reimbursement_type rt ON (r.reimb_type_id = rt.reimb_type_id) "
					+ "LEFT JOIN ers_reimbursement_status rs USING (reimb_status_id) "
					+ "LEFT JOIN ers_users emp ON (r.reimb_author = emp.ers_users_id) "
					+ "LEFT JOIN ers_users man ON (r.reimb_resolver = man.ers_users_id) "
					+ "ORDER BY r.reimb_submitted desc";

			PreparedStatement ps = c.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			System.out.println(reimbursements);
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public int updateStatus(int reimbursementId, String statusType, int resolverId) {
		log.debug("attempting to update a reimbursement ticket in the DB");
		try (Connection c = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_reimbursement "
					+ "SET  reimb_resolved = ?, reimb_resolver = ?, reimb_status_id = ? " + "WHERE reimb_id = ?";

			int status= 0;
			if(statusType.equals("Approved")) {
				status = 2;
			} else {
				status = 3;
			}
				
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, resolverId);
			ps.setInt(3, status);
			ps.setInt(4, reimbursementId);

			System.out.println(resolverId);
			System.out.println(status);
			System.out.println(reimbursementId);
			return ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Reimbursement> findByUsername(String username) {
		log.debug("attempting to show all reiumbursement tickets from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT  r.reimb_id, emp.ers_users_id, rt.reimb_type_id, r.reimb_submitted, r.reimb_amount, r.reimb_description, "
					+ "rt.reimb_type, emp.user_first_name, emp.user_last_name, emp.user_email, r.reimb_resolved, r.reimb_resolver, "
					+ "man.user_first_name AS resolver_first_name, man.user_last_name AS resolver_last_name, man.user_email AS resolver_email, "
					+ "rs.reimb_status FROM ers_reimbursement r "
					+ "LEFT JOIN ers_reimbursement_type rt ON (r.reimb_type_id = rt.reimb_type_id) "
					+ "LEFT JOIN ers_reimbursement_status rs USING (reimb_status_id) "
					+ "LEFT JOIN ers_users emp ON (r.reimb_author = emp.ers_users_id) "
					+ "LEFT JOIN ers_users man ON (r.reimb_resolver = man.ers_users_id) "
					+ "WHERE emp.ers_username = ? " + "ORDER BY r.reimb_submitted desc";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			System.out.println(reimbursements);
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
